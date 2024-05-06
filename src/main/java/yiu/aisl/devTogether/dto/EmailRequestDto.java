package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

// Request 와 Response  나누는 이유
//Request: 데이터를 저장하기 위함
//Response: 사용자에게 원하는 정보를 전달하기 위해 2차 검증을 거쳐서 보냄
// > 협업과 유지보수 굿굿



public class EmailRequestDto {
    private String email;
}