package ru.rtf.telegramBot.learning.mode;

import ru.rtf.Card;
import ru.rtf.telegramBot.learning.AnswerStatus;
import ru.rtf.telegramBot.learning.LearningSession;

import java.util.Collection;

/**
 * Режим обучения "карточки"
 */
public class MemoryLearning extends LearningSession {
    /**
     * Количество баллов, которое добавляется карточке при правильном ответе
     */
    protected static final int RIGHT_ANSWER_SCORE_ADDITION = 1;

    /**
     * Инициализировать режим обучения
     *
     * @param cards Карты к обучению
     */
    public MemoryLearning(Collection<Card> cards) {
        super(cards);
    }

    @Override
    public String formQuestion() {
        String question = "Термин - \"%s\"";
        return question.formatted(getActiveCard().getTerm());
    }

    @Override
    public AnswerStatus checkAnswer(String answer) {
        return switch (answer) {
            case "1" -> AnswerStatus.PARTIALLY_RIGHT;
            case "2" -> AnswerStatus.RIGHT;
            default -> AnswerStatus.WRONG;
        };
    }

    @Override
    public String getDescription() {
        return """
                в режиме карточки
                Вам показывается термин, ваша задача - вспомнить определение
                После оценить, насколько хорошо вы помните определение
                0 - не помню  1 - частично помню  2 - помню""";
    }

    @Override
    public int getRightAnswerScoreAddition() {
        return RIGHT_ANSWER_SCORE_ADDITION;
    }
}
