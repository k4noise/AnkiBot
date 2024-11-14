package ru.rtf.telegramBot.learning.mode;

import ru.rtf.Card;
import ru.rtf.telegramBot.learning.LearningSession;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Режим обучения "карточки"
 */
public class MemoryLearning implements LearningSession {

    /**
     * Карты к изучению
     */
    private final Queue<Card> allCards;

    /**
     * Инициализировать режим обучения
     *
     * @param cards Карты к обучению
     */
    public MemoryLearning(Collection<Card> cards) {
        allCards = new LinkedList<>(cards);
    }

    @Override
    public String formQuestion() {
        String question = "Термин - \"%s\"";
        Card currentCard = allCards.peek();
        return question.formatted(currentCard.getTerm());
    }

    @Override
    public boolean checkAnswer(String answer) {
        return answer.equals("1") || answer.equals("2");
    }

    @Override
    public boolean hasCardsToLearn() {
        return !allCards.isEmpty();
    }

    @Override
    public String pullActiveCardDescription() {
        return allCards.poll().toString();
    }

    @Override
    public String getDescription() {
        return """
                в режиме карточки
                Вам показывается термин, ваша задача — вспомнить определение
                После оценить, насколько хорошо вы помните определение
                0 — не помню  1 — частично помню  2 — помню""";
    }
}
