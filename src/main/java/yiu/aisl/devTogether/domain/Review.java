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

    @JoinColumn(name = "matching_id")
    @ManyToOne
    private Matching matchingId;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Column
    private Boolean hide;

    @Column
    private Integer category;

    @Column
    private Integer star1;

    @Column
    private Integer star2;

    @Column
    private Integer star3;


    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

}
