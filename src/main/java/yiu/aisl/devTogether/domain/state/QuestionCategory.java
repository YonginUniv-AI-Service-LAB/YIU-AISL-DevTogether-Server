package yiu.aisl.devTogether.domain.state;

import lombok.Getter;

@Getter
public enum QuestionCategory {

    인생에서제일행복했던순간은언제인가요(0),
    태어난곳은어디인가요(1),
    제일좋아하는음식은무엇인가요(2),
    출신초등학교는어디인가요(3),
    좋아하는캐릭터는무엇인가요(4);



    private final int value;

    QuestionCategory(int value) {this.value = value;}

    public int getValue() {return value;}

    public static QuestionCategory fromInt(int value) {
        for (QuestionCategory question : QuestionCategory.values()) {
            if (question.getValue() == value) {
                return question;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }
}
