package yiu.aisl.devTogether.domain.state;

import lombok.Getter;


@Getter
public enum MatchingCategory {

    멘토to멘티(0),
    멘토to멘토멘티(1),
    멘티to멘토(2),
    멘티to멘토멘티(3);



    private final int value;

    MatchingCategory(int value) {this.value = value;}

    public int getValue() {return value;}

    public static MatchingCategory fromInt(int value) {
        for (MatchingCategory matchingCategory : MatchingCategory.values()) {
            if (matchingCategory.getValue() == value) {
                return matchingCategory;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }
}
