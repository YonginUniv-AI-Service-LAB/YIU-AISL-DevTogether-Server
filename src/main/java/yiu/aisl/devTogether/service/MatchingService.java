package yiu.aisl.devTogether.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yiu.aisl.devTogether.domain.Ask;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.dto.AskResponseDto;
import yiu.aisl.devTogether.dto.MatchingRequestDto;
import yiu.aisl.devTogether.exception.CustomException;
import yiu.aisl.devTogether.exception.ErrorCode;
import yiu.aisl.devTogether.repository.MatchingRepository;
import yiu.aisl.devTogether.repository.UserRepository;

import java.util.ArrayList;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class MatchingService {

    private final UserRepository userRepository;
    private final MatchingRepository matchingRepository;

    public Boolean apply(String email, MatchingRequestDto.ApplyDTO request) {
        return true;
    }

    public Boolean approve(String email, MatchingRequestDto.AproveDTO request) { return true;
    }

    public Boolean delete(String email, MatchingRequestDto.DeleteDTO request) { return true;
    }

    public Boolean refusal(String email, MatchingRequestDto.RefusalDTO request) { return true;
    }

    public Boolean confirm(String email, MatchingRequestDto.ConfirmDTO request) { return true;
    }

    public Boolean end(String email, MatchingRequestDto.EndDTO request) { return true;
    }

/*
    public List<User> getMentorList(String email) throws Exception {


        User user = findByUserEmail(email);
        if (user.getRole() == RoleCategory.MENTEE) {
            // 현재 사용자가 멘티인 경우, 멘토들의 정보를 가져옴
            return userRepository.findByRole(RoleCategory.MENTOR);
        }

    }

    public User findByUserEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
    }



    public List getMeneteeList(String email) {
    }

 */
}
