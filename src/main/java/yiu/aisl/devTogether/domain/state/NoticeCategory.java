package yiu.aisl.devTogether.domain.state;

import lombok.Getter;

@Getter
public enum NoticeCategory  {
    공지(0), //
    이벤트(1), //
    업데이트(2); //업데이트

    private final int value;

    NoticeCategory(Integer value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static NoticeCategory fromInt(int value) {
        for (NoticeCategory noticeCategory : NoticeCategory.values()) {
            if (noticeCategory.getValue() == value) {
                return noticeCategory;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }
}

