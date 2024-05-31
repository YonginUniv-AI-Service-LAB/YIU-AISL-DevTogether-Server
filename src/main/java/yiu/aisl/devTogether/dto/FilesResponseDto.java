package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilesResponseDto {
    private Long fileId;
    private String originName;
    private Long fileSize;
    private byte[] fileData;

}
