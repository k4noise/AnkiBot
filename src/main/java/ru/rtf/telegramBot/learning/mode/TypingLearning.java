package ru.rtf.telegramBot.learning.mode;

import ru.rtf.Card;
import ru.rtf.telegramBot.learning.AnswerStatus;
import ru.rtf.telegramBot.learning.LearningSession;

import java.util.Collection;


/**
 * Режим обучения "Ввод термина"
 */
public class TypingLearning extends LearningSession {
    /**
     * Количество баллов, которое добавляется карточке при правильном ответе <br>
     * 2 балла за сложность режима
     */
    private static final int RIGHT_ANSWER_SCORE_ADDITION = 2;

    /**
     * Инициализировать режим обучения
     *
     * @param cards Карты к обучению
     */
    public TypingLearning(Collection<Card> cards) {
        super(cards);
    }

    @Override
    public String formQuestion() {
        String questionText = """
                Определение - "%s"
                Введите соответствующий термин:""";
        return questionText.formatted(getActiveCard().getDefinition());
    }

    @Override
    public AnswerStatus checkAnswer(String answer) {
        Card currentCard = getActiveCard();
        AnswerStatus status = AnswerStatus.WRONG;
        if (answer.equalsIgnoreCase(currentCard.getTerm())) {
            currentCard.addScore(RIGHT_ANSWER_SCORE_ADDITION);
            status = AnswerStatus.RIGHT;
        } else {
            currentCard.subtractScore();
        }
        updateStats(status);
        return status;
    }

    @Override
    public String getDescription() {
        return """
                в режиме ввода термина
                Дано определение, ваша задача написать соответсвующий термин (регистр не учитывается)""";
    }
}
