package yiu.aisl.devTogether.domain.state;

import lombok.Getter;

@Getter
public enum GenderCategory {


    MAN(0), //남자
    FEMALE(1); //여자


    private final int value;

    GenderCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GenderCategory fromInt(int value) {
        for (GenderCategory genderCategory : GenderCategory.values()) {
            if (genderCategory.getValue() == value) {
                return genderCategory;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }

}
