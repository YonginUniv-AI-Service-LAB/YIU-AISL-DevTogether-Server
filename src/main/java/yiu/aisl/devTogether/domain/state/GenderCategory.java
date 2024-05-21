package yiu.aisl.devTogether.domain.state;

import lombok.Getter;

@Getter
public enum GenderCategory {


    남(0), //남자
    여(1); //여자


    private final int value;

    GenderCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GenderCategory fromInt(int value) {
        for (GenderCategory gender : GenderCategory.values()) {
            if (gender.getValue() == value) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }

}
