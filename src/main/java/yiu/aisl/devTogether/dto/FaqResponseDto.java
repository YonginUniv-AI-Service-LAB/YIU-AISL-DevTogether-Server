package yiu.aisl.devTogether.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Faq;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FaqResponseDto {
    private Long faqId;
    private String title;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

    public static FaqResponseDto GetFaqDTO(Faq faq) {
        return new FaqResponseDto(

                faq.getFaqId(),

                faq.getTitle(),
                faq.getContents(),
                faq.getCreatedAt(),
                faq.getUpdatedAt()


        );
    }
}
