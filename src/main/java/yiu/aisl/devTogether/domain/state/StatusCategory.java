package yiu.aisl.devTogether.domain.state;

import lombok.Getter;

@Getter
public enum StatusCategory {

    APPLICATION(0), //신청
    PROGRESSION(1), //진행
    COMPLETION(2), //완료
    REJECTION(3);//거절


    private final int value;

    StatusCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StatusCategory fromValue(int value) {
        for (StatusCategory category : StatusCategory.values()) {
            if (category.getValue() == value) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }
}