package ru.rtf.telegramBot.learning.mode;

import ru.rtf.Card;
import ru.rtf.telegramBot.learning.AnswerStatus;
import ru.rtf.telegramBot.learning.LearningSession;

import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Режим обучения "Ввод термина"
 */
public class TypingLearning implements LearningSession {
    /**
     * Количество баллов, которое добавляется карточке при правильном ответе <br>
     * 2 балла за сложность режима
     */
    private static final int RIGHT_ANSWER_SCORE_ADDITION = 2;
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
    public TypingLearning(Collection<Card> cards) {
        allCards = new LinkedList<>(cards);
        learningStats = new EnumMap<>(AnswerStatus.class);
    }

    @Override
    public String formQuestion() {
        String questionText = """
                Определение - "%s"
                Введите соответствующий термин:""";
        Card currentCard = allCards.peek();
        return questionText.formatted(currentCard.getDefinition());
    }

    @Override
    public AnswerStatus checkAnswer(String answer) {
        Card currentCard = allCards.peek();
        AnswerStatus status = AnswerStatus.WRONG;
        if (answer.equalsIgnoreCase(currentCard.getTerm())) {
            currentCard.addScore(RIGHT_ANSWER_SCORE_ADDITION);
            status = AnswerStatus.RIGHT;
        } else {
            currentCard.subtractScore();
        }
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
    public EnumMap<AnswerStatus, Integer> getStats() {
        return learningStats;
    }

    @Override
    public String getDescription() {
        return """
                в режиме ввода термина
                Дано определение, ваша задача написать соответсвующий термин (регистр не учитывается)""";
    }
}
