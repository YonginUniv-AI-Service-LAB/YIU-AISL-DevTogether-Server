package yiu.aisl.devTogether.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {
    //리폿 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long reportId;
    //신고하는 사람
    @JoinColumn
    @ManyToOne
    private User userId;
    //신고 당하는 사람
    @JoinColumn
    @ManyToOne
    private User toUserId;
    //어디서 신고 했어?
    @Column
    private Integer type;
    //어디서 몇 번째 신고야?
    @Column
    private Long typeId;
    //신고 받은거 처리 할께
    @Column
    private Integer status;
    //어떤 걸로 신고 당했어?
    @Column
    private Integer category;
    //신고 이름 category에 따라 설정
    @Column(columnDefinition = "TEXT")
    private String contents;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;
}
