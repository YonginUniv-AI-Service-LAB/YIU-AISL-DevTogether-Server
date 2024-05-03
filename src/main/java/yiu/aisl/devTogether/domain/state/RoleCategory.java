package yiu.aisl.devTogether.domain.state;

import lombok.Getter;

@Getter
public enum RoleCategory {
    MANAGER(0), //관리자
    MEMTOR(1), //멘토
    MEMTEE(2), //멘티
    MEMTORMEMTEE(3); //멘토멘티


    private final int value;

    RoleCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RoleCategory fromValue(int value) {
        for (RoleCategory category : RoleCategory.values()) {
            if (category.getValue() == value) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }
}
