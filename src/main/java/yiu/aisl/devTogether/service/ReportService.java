package yiu.aisl.devTogether.service;

import com.google.firebase.database.core.Repo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.Report;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.dto.ReportRequestDto;
import yiu.aisl.devTogether.dto.ReportResponseDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.ReportRepository;
import yiu.aisl.devTogether.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {
    public final UserRepository userRepository;
    public final ReportRepository reportRepository;

    //신고하기
    public Boolean creatReport(String email, ReportRequestDto.creatDto request) throws Exception {

        // 400: 데이터 미입력
        if (request.getToUserId() == null || request.getType() == null || request.getTypeId() == null ||
                request.getContents().isEmpty() || request.getCategory() == null) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
        //  403: 권한없음
        User fromUser = findByUserEmail(email);

        // 404: to_user_id 없음
        User toUser = findByUserId(request.getToUserId());
        try {
            Report report = Report.builder()
                    .userId(fromUser)
                    .toUserId(toUser)
                    .type(request.getType())
                    .typeId(request.getTypeId())
                    .status(0)//0신고 미처리? 신고 번호 정해야 됨
                    .category(request.getCategory())
                    .contents(request.getContents())
                    .build();
            reportRepository.save(report);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //신고목록 가져오기 관리자만 누적 3회 이상인 사람만
    public List<ReportResponseDto> getList(String email) throws Exception {

        // 403: 권한 없음 --관리자 권한 확인 필요
        if (findByUserEmail(email).getRole() != RoleCategory.관리자) {
//            System.out.println(findByUserEmail(email).getRole());
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        try {
            List<Report> reports = reportRepository.findAll();
            // 유저 이름 별로 카운트
            Map<User, Long> userNameCounts = reports.stream()
                    .collect(Collectors.groupingBy(Report::getToUserId, Collectors.counting()));

            // 카운트가 3 이상인 유저 이름을 필터링
            Set<User> userNamesWithAtLeastThreeReports = userNameCounts.entrySet().stream()
                    .filter(entry -> entry.getValue() >= 3)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());

            // 해당 유저 이름에 해당하는 보고서를 필터링
            List<Report> filteredReports = reports.stream()
                    .filter(report -> userNamesWithAtLeastThreeReports.contains(report.getToUserId()))
                    .toList();
            // 필터링된 보고서를 ReportResponseDto로 변환
            List<ReportResponseDto> responseDtos = filteredReports.stream()
                    .map(ReportResponseDto::new)
                    .collect(Collectors.toList());
            return responseDtos;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //신고 처리하기 관리자만 --- 신고시 가져올것 물어보기 임시로 만듬
    public Boolean treat(String email, ReportRequestDto.treatDto reqest) throws Exception {
        // 403: 권한 없음 --관리자 권한 확인 필요
        if (findByUserEmail(email).getRole() != RoleCategory.관리자) {
            throw new CustomException(ErrorCode.NO_AUTH);
        }
        Optional<Report> report = reportRepository.findByReportId(reqest.getReportId());
        Report getReport = report.get();
        try {
            getReport.setStatus(reqest.getStatus());
            reportRepository.save(getReport);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public User findByUserEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }

    public User findByUserId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }
}
