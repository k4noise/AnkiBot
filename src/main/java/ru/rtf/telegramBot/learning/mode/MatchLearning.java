package ru.rtf.telegramBot.learning.mode;

import ru.rtf.Card;
import ru.rtf.telegramBot.learning.LearningSession;

import java.util.*;

/**
 * Режим обучения "соответствие"
 */
public class MatchLearning extends LearningSession {

    /**
     * Все определения карт
     */
    private final List<String> allDefinitions;
    /**
     * Индекс рандомного определения
     */
    private int randomDefinitionIndex;

    /**
     * Экземпляр генератора случайных чисел
     */
    private final Random random;

    /**
     * Инициализировать режим обучения
     *
     * @param cards Карты к обучению
     */
    public MatchLearning(Collection<Card> cards) {
        super(cards);
        allDefinitions = cards.stream()
                .map(Card::getDefinition)
                .toList();
        random = new Random();
    }

    @Override
    public String formQuestion() {
        randomDefinitionIndex = generateNextRandomDefinitionIndex();

        return """
                Утверждение:
                %s - %s
                1 - верно, 0 - неверно""".formatted(allCards.peek().getTerm(), allDefinitions.get(randomDefinitionIndex));
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
    public String getDescription() {
        return """
                в режиме соответствия
                Показывается термин и определение, ваша задача - определить, соответствует ли термин определению""";
    }

    /**
     * Генерирует случайный индекс для определения из списка всех определений
     */
    private int generateNextRandomDefinitionIndex() {
        return random.nextInt(allDefinitions.size());
    }
}