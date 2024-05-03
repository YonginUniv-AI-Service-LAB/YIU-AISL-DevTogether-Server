package yiu.aisl.devTogether.domain.state;

import lombok.Getter;

@Getter
public enum GenderCategory {
    //명사 이용
    MAN(0), //남자
    FEMALE(1); //여자


    private final int value;

    GenderCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GenderCategory fromValue(int value) {
        for (GenderCategory category : GenderCategory.values()) {
            if (category.getValue() == value) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }

}
