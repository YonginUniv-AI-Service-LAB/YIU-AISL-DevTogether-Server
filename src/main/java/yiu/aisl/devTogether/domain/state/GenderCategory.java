package yiu.aisl.devTogether.domain.state;

import lombok.Getter;

@Getter
public enum GenderCategory {

    // ENUM이란?  일련의 상수를 정의하는 데 사용되는 데이터 형식

    // 왜 사용함?
    //- 코드의 단순화
    //- 인스턴스 생성과 상속을 방지
    //- 키워드 enum을 사용하기 때문에 구현 의도가 열거임을 분명하게 나타낼 수 있다.
    //- 기존 코드보다 짧으면서 더 많은 기능을 구현할 수있다.(필드, 메소드 생성)
    //- class로 선언을 하면 각각의 멤버들을 배열처럼 하나씩 꺼내서 사용 할 수 없다. > eunm이 보완해줌


    //명사 이용
    MAN(0), //남자
    FEMALE(1); //여자


    private final int value;

    GenderCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GenderCategory fromValue(int value) {
        for (GenderCategory category : GenderCategory.values()) {
            if (category.getValue() == value) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }

}
