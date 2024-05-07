package yiu.aisl.devTogether.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {
    // 데이터 미입력
    INSUFFICIENT_DATA(400, ResultMessage.INSUFFICIENT_DATA),
    // 데이터를 찾을 수 없음
    NOT_EXIST(404, ResultMessage.NOT_EXIST),
    // 인증 실패
    UNAUTHORIZED(401, ResultMessage.UNAUTHORIZED),
    //접근 권한 없음
    NO_AUTH(401, ResultMessage.NO_AUTH),
    // 회원정보 불일치
    USER_DATA_INCONSISTENCY(401, ResultMessage.USER_DATA_INCONSISTENCY),
    //잘못된 비밀번호
    VALID_NOT_PWD(401, ResultMessage.VALID_NOT_PWD),
    //존재하지 않는 사용자
    MEMBER_NOT_EXIST(401, ResultMessage.MEMBER_NOT_EXIST),
    // 로그인 필요
    LOGIN_REQUIRED(401, ResultMessage.LOGIN_REQUIRED),

    // 권한 없음
    ACCESS_NO_AUTH(403, ResultMessage.ACCESS_NO_AUTH),
    //AccessToken 만료
    ACCESS_TOKEN_EXPIRED(403, ResultMessage.ACCESS_TOKEN_EXPIRED),
    //RefreshToken 만료
    REFRESH_TOKEN_EXPIRED(403, ResultMessage.REFRESH_TOKEN_EXPIRED),

    //  to_user_id 없음
    To_USER_ID_NOT_EXIST(404, ResultMessage.To_USER_ID_NOT_EXIST),
    //회원 없음
    USER_NOT_EXIST(404, ResultMessage.USER_NOT_EXIST),
    //id 없음
    ID_NOT_EXIST(404, ResultMessage.ID_NOT_EXIST),

    // 데이터 충돌
    CONFLICT(409, ResultMessage.CONFLICT),
    // 데이터 중복
    DUPLICATE(409, ResultMessage.DUPLICATE),

    // 서버 오류
    INTERNAL_SERVER_ERROR(500, ResultMessage.INTERNAL_SERVER_ERROR),
    // 레디스 서버 오류
    REDIS_SERVER_ERROR(500, ResultMessage.REDIS_SERVER_ERROR)
    ;


    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public interface ResultMessage {
        String UNAUTHORIZED = "인증 실패";
        String NO_AUTH = "접근 권한 없음";
        String ACCESS_NO_AUTH = "접근 권한 없음";
        String ACCESS_TOKEN_EXPIRED = "AccessToken 만료";
        String REFRESH_TOKEN_EXPIRED = "RefreshToken 만료";
        String USER_DATA_INCONSISTENCY = "회원 정보 불일치";
        String VALID_NOT_PWD = "잘못된 비밀번호";
        String MEMBER_NOT_EXIST = "존재하지 않는 사용자";
        String LOGIN_REQUIRED = "로그인 필요";
        String INSUFFICIENT_DATA = "데이터 미입력";
        String NOT_EXIST = "존재하지 않음";
        String CONFLICT = "데이터 충돌";
        String DUPLICATE = "데이터 중복";
        String INTERNAL_SERVER_ERROR = "내부 서버 오류";
        String REDIS_SERVER_ERROR = "Redis 서버 오류";

        String USER_NOT_EXIST = "회원 없음";

        String ID_NOT_EXIST = "id 없음";


        String To_USER_ID_NOT_EXIST = " to_user_id 없음";
    }


}