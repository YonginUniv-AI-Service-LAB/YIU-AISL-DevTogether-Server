package yiu.aisl.devTogether.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import yiu.aisl.devTogether.domain.state.AskCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.domain.state.StatusCategory;
import yiu.aisl.devTogether.service.FilesService;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long askId;

//, referencedColumnName = "email"

    @JoinColumn(name = "user_id",nullable = false)
    @ManyToOne
    private User user;

    @Column(columnDefinition = "TEXT")
    private String title;


    @Column(columnDefinition = "TEXT")
    private String contents;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private StatusCategory status;

    @Column
    private String answer;

    @Column
    private Boolean files;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private AskCategory askCategory;


    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;


}
