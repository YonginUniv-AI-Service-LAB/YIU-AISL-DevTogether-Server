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
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long scrapId;

    @Column
    private Integer type;

    @Column
    private Long typeId;


    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;


}
