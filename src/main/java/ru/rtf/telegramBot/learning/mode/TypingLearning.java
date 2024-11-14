package ru.rtf.telegramBot.learning.mode;

import ru.rtf.Card;
import ru.rtf.telegramBot.learning.LearningSession;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Режим обучения "Ввод термина"
 */
public class TypingLearning implements LearningSession {

    /**
     * Карты к изучению
     */
    private final Queue<Card> allCards;

    /**
     * Инициализировать режим обучения
     *
     * @param cards Карты к обучению
     */
    public TypingLearning(Collection<Card> cards) {
        allCards = new LinkedList<>(cards);
    }

    @Override
    public String formQuestion() {
        String questionText = """
                Определение — "%s"
                Введите соответствующий термин:""";
        Card currentCard = allCards.peek();
        return questionText.formatted(currentCard.getDefinition());
    }

    @Override
    public String messageCheckAnswer(String answer) {
        Card currentCard = allCards.peek();
        boolean checkAnswer = answer.equalsIgnoreCase(currentCard.getTerm());
        return checkAnswer
                ? LearningSession.CORRECT_ANSWER_INFO.formatted(currentCard)
                : LearningSession.INCORRECT_ANSWER_INFO.formatted(currentCard);
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
                в режиме ввода термина
                Дано определение, ваша задача написать соответсвующий термин \\(регистр не учитывается\\)""";
    }
}
