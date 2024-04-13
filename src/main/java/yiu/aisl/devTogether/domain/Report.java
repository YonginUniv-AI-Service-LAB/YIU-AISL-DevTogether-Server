package yiu.aisl.devTogether.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long reportId;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @JoinColumn
    @ManyToOne
    private User touserId;

    @Column
    private Integer type;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @JoinColumn
    @ManyToOne
    private User userId;

    @Column
    private Integer status;


}
