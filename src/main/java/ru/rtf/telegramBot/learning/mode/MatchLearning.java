package ru.rtf.telegramBot.learning.mode;

import ru.rtf.Card;
import ru.rtf.Deck;
import ru.rtf.telegramBot.learning.AnswerStatus;
import ru.rtf.telegramBot.learning.LearningSession;

import java.util.*;

/**
 * Режим обучения "соответствие"
 */
public class MatchLearning implements LearningSession {
    /**
     * Колода
     */
    private final Deck deck;
    /**
     * Карты к изучению
     */
    private final Queue<Card> allCards;
    /**
     * Все определения карт
     */
    private final List<String> allDefinitions;
    /**
     * Индекс рандомного определения
     */
    private int randomDefinitionIndex;
    /**
     * Статистика сеанса обучения
     */
    private final EnumMap<AnswerStatus, Integer> learningStats;

    /**
     * Инициализировать режим обучения
     *
     * @param deck Колода пользователя
     */
    public MatchLearning(Deck deck) {
        this.deck = deck;
        allCards = new LinkedList<>(deck.getCards());
        allDefinitions = deck.getCards().stream()
                .map(Card::getDefinition)
                .toList();
        learningStats = new EnumMap<>(AnswerStatus.class);
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
    public AnswerStatus checkAnswer(String answer) {
        Card currentCard = allCards.peek();
        boolean isCorrectDefinition = Objects.equals(currentCard.getDefinition(), allDefinitions.get(randomDefinitionIndex));

        boolean isRightUserAnswer;
        switch (answer) {
            case "0":
                isRightUserAnswer = !isCorrectDefinition;
                break;
            case "1":
                isRightUserAnswer = isCorrectDefinition;
                break;
            default:
                learningStats.merge(AnswerStatus.WRONG, 1, Integer::sum);
                return AnswerStatus.WRONG;
        }

        AnswerStatus result = isRightUserAnswer ? AnswerStatus.RIGHT : AnswerStatus.WRONG;
        if (isRightUserAnswer) {
            currentCard.addScore();
        } else {
            currentCard.subtractScore();
        }
        learningStats.merge(result, 1, Integer::sum);
        return result;
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
                в режиме соответствия
                Показывается термин и определение, ваша задача - определить, соответствует ли термин определению""";
    }

    @Override
    public EnumMap<AnswerStatus, Integer> getStats() {
        return learningStats;
    }

    @Override
    public void saveStatsToDeck() {
        deck.addNewStats(learningStats);
    }

    /**
     * Генерирует случайный индекс для определения из списка всех определений
     */
    private int generateNextRandomDefinitionIndex() {
        return new Random().nextInt(allDefinitions.size());
    }
}