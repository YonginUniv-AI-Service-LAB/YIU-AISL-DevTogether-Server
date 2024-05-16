package yiu.aisl.devTogether.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {
    // 데이터 미입력
    INSUFFICIENT_DATA(400),

    //회원정보 불일치
    USER_DATA_INCONSISTENCY(401),

    //로그인 필요
    LOGIN_REQUIRED(401),

    //권한 없음
    NO_AUTH(403),

    //AccessToken 만료
    ACCESS_TOKEN_EXPIRED(403),

    //RefreshToken 만료
    REFRESH_TOKEN_EXPIRED(403),

    //회원 없음
    NOT_EXIST_MEMBER(404),

    //id 없음
    NOT_EXIST_ID(404),

    //수정 불가
    NOT_MODIFICATION(404),

    // to_user_id 없음
    To_USER_ID_NOT_EXIST(404),

    //데이터 중복
    DUPLICATE(409),

    //서버 오류
    INTERNAL_SERVER_ERROR(500);




    private final int status;

    ErrorCode(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        switch (this) {
            case INSUFFICIENT_DATA:
                return "데이터 미입력";

            case NOT_EXIST_MEMBER:
                return "회원 없음";

            case NOT_EXIST_ID:
                return "id 없음";

            case NO_AUTH:
                return "권한 없음";

            case ACCESS_TOKEN_EXPIRED:
                return "AccessToken 만료";

            case REFRESH_TOKEN_EXPIRED:
                return "RefreshToken 만료";

            case To_USER_ID_NOT_EXIST:
                return "to_user_id 없음";
            case NOT_MODIFICATION:
                return "수정 불가";

            case DUPLICATE:
                return "데이터 중복";

            case USER_DATA_INCONSISTENCY:
                return "회원 정보 불일치";

            case INTERNAL_SERVER_ERROR:
                return "내부 서버 오류";

            case LOGIN_REQUIRED:
                return "로그인 필요";
            default:
                return "알 수 없는 오류";
        }
    }
}
