package yiu.aisl.devTogether.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long askId;

    @Column(columnDefinition = "TEXT")
    private String title;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    @JoinColumn
    @ManyToOne
    private User userId;

    @Column
    private Integer status;

    @Column
    private String  answer;

    @Column
    private String file;

    @Column
    private Integer category;

}
