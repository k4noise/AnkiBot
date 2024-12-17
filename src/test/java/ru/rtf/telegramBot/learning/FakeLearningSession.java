package ru.rtf.telegramBot.learning;

import ru.rtf.Card;

import java.util.Collection;

/**
 * Фейковая сессия обучения
 */
public class FakeLearningSession extends LearningSession {
    public FakeLearningSession(Collection<Card> cards) {
        super(cards);
    }

    @Override
    public String formQuestion() {
        return "question";
    }

    @Override
    public AnswerStatus checkAnswer(String answer) {
        return AnswerStatus.WRONG;
    }

    @Override
    public String getDescription() {
        return "description";
    }

    @Override
    public int getRightAnswerScoreAddition() {
        return 1;
    }
}
