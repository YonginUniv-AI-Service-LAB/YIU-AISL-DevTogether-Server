package yiu.aisl.devTogether.domain.state;

import lombok.Getter;

@Getter
public enum NoticeCategory  {
    NOTICE(0),
    EVENT(1),
    UPDATE(2);

    private final int value;

    NoticeCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static NoticeCategory fromValue(int value) {
        for (NoticeCategory category : NoticeCategory.values()) {
            if (category.getValue() == value) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }
}