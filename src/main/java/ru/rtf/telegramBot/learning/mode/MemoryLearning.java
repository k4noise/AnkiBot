package ru.rtf.telegramBot.learning.mode;

import ru.rtf.Card;
import ru.rtf.telegramBot.learning.AnswerStatus;
import ru.rtf.telegramBot.learning.LearningSession;

import java.util.Collection;
import java.util.EnumMap;
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
     * Статистика сеанса обучения
     */
    private final EnumMap<AnswerStatus, Integer> learningStats;

    /**
     * Инициализировать режим обучения
     *
     * @param cards Карты к обучению
     */
    public MemoryLearning(Collection<Card> cards) {
        allCards = new LinkedList<>(cards);
        learningStats = new EnumMap<>(AnswerStatus.class);
    }

    @Override
    public String formQuestion() {
        String question = "Термин - \"%s\"";
        Card currentCard = allCards.peek();
        return question.formatted(currentCard.getTerm());
    }

    @Override
    public AnswerStatus checkAnswer(String answer) {
        Card currentCard = allCards.peek();
        AnswerStatus status = switch (answer) {
            case "0" -> {
                currentCard.subtractScore();
                yield AnswerStatus.WRONG;
            }
            case "1" -> AnswerStatus.PARTIALLY_RIGHT;
            case "2" -> {
                currentCard.addScore();
                yield AnswerStatus.RIGHT;
            }
            default -> AnswerStatus.WRONG;
        };
        learningStats.merge(status, 1, Integer::sum);
        return status;
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
                Вам показывается термин, ваша задача - вспомнить определение
                После оценить, насколько хорошо вы помните определение
                0 - не помню  1 - частично помню  2 - помню""";
    }

    @Override
    public EnumMap<AnswerStatus, Integer> getStats() {
        return learningStats;
    }
}
