package yiu.aisl.devTogether.domain.state;

import lombok.Getter;
import yiu.aisl.devTogether.domain.Faq;

@Getter
public enum RoleCategory {
    MANAGER(0), //관리자
    MENTOR(1), //멘토
    MENTEE(2), //멘티
    MEMTORMENTEE(3); //멘토멘티


    private final int value;

    RoleCategory(Integer value) { this.value = value; }

    public int getValue() { return value; }

    public static RoleCategory fromInt(int value) {
        for (RoleCategory role : RoleCategory.values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }


}

