package yiu.aisl.devTogether.domain.state;

import lombok.Getter;

@Getter
public enum RoleCategory {
    MANAGER(0), //관리자
    MENTOR(1), //멘토
    MEMTEE(2), //멘티
    MEMTORMEMTEE(3); //멘토멘티


    private final int value;

    RoleCategory(Integer value) { this.value = value; }

    public int getValue() { return value; }

    public static RoleCategory fromInt(int value) {
        for (RoleCategory roleCategory : RoleCategory.values()) {
            if (roleCategory.getValue() == value) {
                return roleCategory;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }
}
