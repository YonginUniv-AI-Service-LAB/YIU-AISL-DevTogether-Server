package yiu.aisl.devTogether.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Faq;
import yiu.aisl.devTogether.domain.state.RoleCategory;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FaqResponsetDto {
    private Long faqId;
    private RoleCategory roleCategory;
    private String title;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

    public static FaqResponsetDto GetFaqDTO(Faq faq) {
        return new FaqResponsetDto(

                faq.getFaqId(),
                faq.getRoleCategory(),
                faq.getTitle(),
                faq.getContents(),
                faq.getCreatedAt(),
                faq.getUpdatedAt()


        );
    }
}
