package yiu.aisl.devTogether.domain.state;

import lombok.Getter;

@Getter
public enum BoardCategory {
    자유(0),
    뉴스(1),
    질문공부(2),
    취업기술(3),
    플리마켓(4);

    private final int value;

    BoardCategory(Integer value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static BoardCategory fromInt(int value) {
        for (BoardCategory boardCategory : BoardCategory.values()) {
            if (boardCategory.getValue() == value) {
                return boardCategory;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }
}
