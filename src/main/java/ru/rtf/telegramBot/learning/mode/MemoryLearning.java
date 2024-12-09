package ru.rtf.telegramBot.learning.mode;

import ru.rtf.Card;
import ru.rtf.telegramBot.learning.LearningSession;

import java.util.Collection;

/**
 * Режим обучения "карточки"
 */
public class MemoryLearning extends LearningSession {

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
    public boolean checkAnswer(String answer) {
        return answer.equals("1") || answer.equals("2");
    }

    @Override
    public String getDescription() {
        return """
                в режиме карточки
                Вам показывается термин, ваша задача - вспомнить определение
                После оценить, насколько хорошо вы помните определение
                0 - не помню  1 - частично помню  2 - помню""";
    }
}
