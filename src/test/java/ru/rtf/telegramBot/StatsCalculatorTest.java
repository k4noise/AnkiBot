package ru.rtf.telegramBot;

import org.junit.jupiter.api.*;
import ru.rtf.Card;
import ru.rtf.Deck;
import ru.rtf.telegramBot.learning.AnswerStatus;

import java.util.EnumMap;

/**
 * Тестирование калькулятора статистики обучения {@link StatsCalculator}
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatsCalculatorTest {
    /**
     * Калькулятор статистики обучения
     */
    private static StatsCalculator calculator;

    /**
     * Создать единый экземпляр калькулятора статистики
     */
    @BeforeAll
    void createStatsCalculator() {
        calculator = new StatsCalculator();
    }

    /**
     * Получение процента успешности без ответов
     */
    @Test
    @DisplayName("Процент успешности отсутствия ответов")
    void testGetSuccessLearningPercentageNoAnswers() {
        EnumMap<AnswerStatus, Integer> stats = new EnumMap<>(AnswerStatus.class);
        Assertions.assertEquals(0, calculator.getSuccessLearningPercentage(stats));

        stats.put(AnswerStatus.RIGHT, 0);
        stats.put(AnswerStatus.PARTIALLY_RIGHT, 0);
        stats.put(AnswerStatus.WRONG, 0);
        Assertions.assertEquals(0, calculator.getSuccessLearningPercentage(stats));
    }

    /**
     * Получение процента успешности со всеми правильными ответами
     */
    @Test
    @DisplayName("Процент успешности правильных ответов")
    void testGetSuccessLearningPercentageAllRight() {
        EnumMap<AnswerStatus, Integer> stats = new EnumMap<>(AnswerStatus.class);
        stats.put(AnswerStatus.RIGHT, 3);
        Assertions.assertEquals(100, calculator.getSuccessLearningPercentage(stats));
    }

    /**
     * Получение процента успешности со всеми неправильными ответами
     */
    @Test
    @DisplayName("Процент успешности неправильных ответов")
    void testGetSuccessLearningPercentageAllWrong() {
        EnumMap<AnswerStatus, Integer> stats = new EnumMap<>(AnswerStatus.class);
        stats.put(AnswerStatus.WRONG, 3);
        Assertions.assertEquals(0, calculator.getSuccessLearningPercentage(stats));
    }

    /**
     * Получение процента успешности со всеми частично правильными ответами
     */
    @Test
    @DisplayName("Процент успешности частично правильных ответов")
    void testGetSuccessLearningPercentageAllPartiallyRight() {
        EnumMap<AnswerStatus, Integer> stats = new EnumMap<>(AnswerStatus.class);
        stats.put(AnswerStatus.PARTIALLY_RIGHT, 3);
        Assertions.assertEquals(50, calculator.getSuccessLearningPercentage(stats));
    }

    /**
     * Получение процента успешности, все виды ответов
     */
    @Test
    @DisplayName("Процент успешности всех видов ответов")
    void testGetSuccessLearningPercentageAllOptions() {
        EnumMap<AnswerStatus, Integer> stats = new EnumMap<>(AnswerStatus.class);
        stats.put(AnswerStatus.RIGHT, 2);
        stats.put(AnswerStatus.PARTIALLY_RIGHT, 3);
        stats.put(AnswerStatus.WRONG, 1);
        Assertions.assertEquals(58, calculator.getSuccessLearningPercentage(stats));
    }

    /**
     * Получение процента изученности пустой колоды
     */
    @Test
    @DisplayName("Процент изученности пустой колоды")
    void testGetDeckLearningPercentageEmptyDeck() {
        Deck deck = new Deck("Deck");
        Assertions.assertEquals(0, calculator.getDeckLearningPercentage(deck));
    }

    /**
     * Получение процента изученности колоды с неизученными картами
     */
    @Test
    @DisplayName("Процент изученности неизученных карт колоды")
    void testGetDeckLearningPercentageUnlearnedCards() {
        Deck deck = new Deck("Deck");
        deck.addCard(new Card("term", "def"));
        deck.addCard(new Card("term2", "def"));
        Assertions.assertEquals(0, calculator.getDeckLearningPercentage(deck));
    }

    /**
     * Получение процента изученности колоды с изученными картами
     */
    @Test
    @DisplayName("Процент изученности неизученных карт колоды")
    void testGetDeckLearningPercentageLearnedCards() {
        Deck deck = new Deck("Deck");
        Card card1 = new Card("term", "def");
        Card card2 = new Card("term2", "def");
        card1.addScore(3);
        card2.addScore(10);
        deck.addCard(card1);
        deck.addCard(card2);

        Assertions.assertEquals(54, calculator.getDeckLearningPercentage(deck));
    }
}