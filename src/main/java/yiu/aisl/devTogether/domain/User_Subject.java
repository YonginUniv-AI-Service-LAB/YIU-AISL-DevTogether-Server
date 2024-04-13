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
public class User_Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long userSubjectId;
    /*
    String -> User?
     */
    @Column
    private String userId;

    @Column
    private Integer subjectCode;

    @Column
    private String subject_name;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

}
