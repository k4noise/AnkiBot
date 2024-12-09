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
    public boolean checkAnswer(String answer) {
        return false;
    }

    @Override
    public String getDescription() {
        return "description";
    }
}
