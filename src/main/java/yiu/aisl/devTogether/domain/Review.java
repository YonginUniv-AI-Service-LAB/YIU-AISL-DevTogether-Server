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
public class Review {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @JoinColumn
    @ManyToOne
    private Matching matchingId;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Column
    private Boolean hide;

    @Column
    private Integer category;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

}
