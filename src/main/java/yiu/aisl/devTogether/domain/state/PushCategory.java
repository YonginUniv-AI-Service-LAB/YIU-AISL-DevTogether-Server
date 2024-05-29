package yiu.aisl.devTogether.domain.state;

import lombok.Getter;

@Getter
public enum PushCategory {
    게시판(0),
    댓글(1),
    쪽지(2),
    매칭(3),
    문의(4);

    private final int value;

    PushCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PushCategory fromInt(int value) {
        for(PushCategory push : PushCategory.values()) {
            if(push.getValue() == value) {
                return push;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " +value);
    }
}
