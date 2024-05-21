package yiu.aisl.devTogether.domain.state;

import lombok.Getter;

@Getter
public enum StatusCategory {

    신청(0), //신청
    진행(1), //진행
    완료(2), //완료
    거절(3);//거절


    private final int value;

    StatusCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StatusCategory fromInt(int value) {
        for (StatusCategory status : StatusCategory.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }
}
