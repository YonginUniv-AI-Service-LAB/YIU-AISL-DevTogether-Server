package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yiu.aisl.devTogether.domain.Report;
import yiu.aisl.devTogether.domain.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponseDto {
    private Long reportId;
    private User fromUserId;
    private User toUserId;
    private Integer type;
    private Long typeId;
    private Integer status;
    private Integer category;
    private String contents;

    public static ReportResponseDto getReportDto(Report report) {
        return new ReportResponseDto(
                report.getReportId(),   //리폿 아이디
                report.getUserId(),     //보낸 사람
                report.getToUserId(),   //받은 사람
                report.getType(),       //신고 개시물 유형
                report.getTypeId(),     //신고 개시물 번호
                report.getStatus(),     //신고 처리 상태
                report.getCategory(),   //신고 유형
                report.getContents()    //신고 내용
        );
    }

    public ReportResponseDto(Report report) {
        this.reportId = report.getReportId();
        this.fromUserId = report.getUserId();
        this.toUserId = report.getToUserId();
        this.contents = report.getContents();
        this.type = report.getType();
        this.typeId = report.getTypeId();
        this.category = report.getCategory();
        this.status = report.getStatus();
    }

}
