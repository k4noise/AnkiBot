package ru.rtf.telegramBot.learning.mode;

import ru.rtf.Card;
import ru.rtf.telegramBot.learning.LearningSession;

import java.util.*;

/**
 * Режим обучения "соответствие"
 */
public class MatchLearning implements LearningSession {
    /**
     * Карты к изучению
     */
    private final Queue<Card> allCards;
    private final List<String> allDefinitions;
    private int randomDefinitionIndex;

    /**
     * Инициализировать режим обучения
     *
     * @param cards Карты к обучению
     */
    public MatchLearning(Collection<Card> cards) {
        allCards = new LinkedList<>(cards);
        allDefinitions = cards.stream()
                .map(Card::getDefinition)
                .toList();
    }

    @Override
    public String formQuestion() {
        randomDefinitionIndex = generateNextRandomDefinitionIndex();

        return """
                Утверждение:
                %s - %s""".formatted(allCards.peek().getTerm(), allDefinitions.get(randomDefinitionIndex));
    }

    @Override
    public boolean checkAnswer(String answer) {
        boolean userAnswer;
        if (answer.equals("0")) {
            userAnswer = false;
        } else if (answer.equals("1")) {
            userAnswer = true;
        } else {
            return false;
        }
        return userAnswer == Objects.equals(allCards.peek().getDefinition(), allDefinitions.get(randomDefinitionIndex));
    }

    @Override
    public boolean hasCardsToLearn() {
        return !allCards.isEmpty();
    }

    @Override
    public String getActiveCardDescription() {
        return allCards.poll().toString();
    }

    @Override
    public String getDescription() {
        return """
                в режиме соответствия
                Показывается термин и определение, ваша задача - определить, соответствует ли термин определению""";
    }

    private int generateNextRandomDefinitionIndex() {
        return new Random().nextInt(allDefinitions.size());
    }
}
