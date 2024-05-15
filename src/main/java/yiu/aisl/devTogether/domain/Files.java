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
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long FileId;

    @Column
    private Integer type;

    @Column
    private Long typeId;

    @Column(columnDefinition = "TEXT")
    private String originName;

    @Column(columnDefinition = "TEXT")
    private String storageName;

    @Column(columnDefinition = "TEXT")
    private String path;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;


}
