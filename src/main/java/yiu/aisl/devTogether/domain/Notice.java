package yiu.aisl.devTogether.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import yiu.aisl.devTogether.domain.state.NoticeCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long noticeId;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private RoleCategory roleCategory;

    @Column(columnDefinition = "TEXT")
    private String contents;


    @Column(nullable = false,length = 255)
    private String title;

    @Column(length = 255)
    private String file;



    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



    @Column
    @Enumerated(EnumType.ORDINAL)
    private NoticeCategory noticeCategory;

}
