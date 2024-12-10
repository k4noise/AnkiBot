package ru.rtf.telegramBot;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.rtf.Card;
import ru.rtf.telegramBot.learning.CardLearningStatus;
import ru.rtf.Deck;
import ru.rtf.telegramBot.learning.AnswerStatus;

import java.util.EnumMap;
import java.util.stream.Stream;

/**
 * Тестирование калькулятора статистики обучения {@link StatsCalculator}
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatsCalculatorTest {
    /**
     * Калькулятор статистики обучения
     */
    private StatsCalculator calculator;

    /**
     * Создать единый экземпляр калькулятора статистики
     */
    @BeforeAll
    void createStatsCalculator() {
        calculator = new StatsCalculator();
    }

    /**
     * Получение процента успешности ответов одного типа
     */
    @Test
    @DisplayName("Процент успешности ответов одного типа")
    void testGetSuccessLearningPercentage() {
        EnumMap<AnswerStatus, Integer> stats = new EnumMap<>(AnswerStatus.class);
        stats.put(AnswerStatus.RIGHT, 3);
        Assertions.assertEquals(100, calculator.getSuccessLearningPercentage(stats));

        stats = new EnumMap<>(AnswerStatus.class);
        stats.put(AnswerStatus.WRONG, 3);
        Assertions.assertEquals(0, calculator.getSuccessLearningPercentage(stats));

        stats = new EnumMap<>(AnswerStatus.class);
        stats.put(AnswerStatus.PARTIALLY_RIGHT, 3);
        Assertions.assertEquals(50, calculator.getSuccessLearningPercentage(stats));
    }

    /**
     * Получение процента успешности, все типы ответов
     */
    @Test
    @DisplayName("Процент успешности всех типов ответов")
    void testGetSuccessLearningPercentageAllOptions() {
        EnumMap<AnswerStatus, Integer> stats = new EnumMap<>(AnswerStatus.class);
        stats.put(AnswerStatus.RIGHT, 2);
        stats.put(AnswerStatus.PARTIALLY_RIGHT, 3);
        stats.put(AnswerStatus.WRONG, 1);
        Assertions.assertEquals(58, calculator.getSuccessLearningPercentage(stats));
    }

    /**
     * Получение процента изученности колоды без карт и с неизученными картами
     */
    @Test
    @DisplayName("Процент изученности пустой и неизученной колоды")
    void testGetDeckLearningPercentageEmptyDeck() {
        Deck deck = new Deck("Deck");
        Assertions.assertEquals(0, calculator.getDeckLearningPercentage(deck));

        deck.addCard(new Card("term", "def"));
        deck.addCard(new Card("term2", "def"));
        Assertions.assertEquals(0, calculator.getDeckLearningPercentage(deck));
    }

    /**
     * Получение процента изученности колоды с изученными картами
     */
    @Test
    @DisplayName("Процент изученности карт колоды")
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

    /**
     * Получение статистики статусов карт в колоде для пустой и неизученной колоды
     */
    @Test
    @DisplayName("Статусы карт пустой и неизученной колоды")
    void testGetCardsCountByStatusEmptyDeck() {
        Deck deck = new Deck("Deck");
        EnumMap<CardLearningStatus, Integer> noCardsStats = calculator.getCardsCountByStatus(deck);

        Assertions.assertNull(noCardsStats.get(CardLearningStatus.NOT_STUDIED));
        Assertions.assertNull(noCardsStats.get(CardLearningStatus.PARTIALLY_STUDIED));
        Assertions.assertNull(noCardsStats.get(CardLearningStatus.STUDIED));

        deck.addCard(new Card("term", "def"));
        deck.addCard(new Card("term2", "def"));
        EnumMap<CardLearningStatus, Integer> noLearnedCardsStats = calculator.getCardsCountByStatus(deck);

        Assertions.assertEquals(2, noLearnedCardsStats.get(CardLearningStatus.NOT_STUDIED));
        Assertions.assertNull(noLearnedCardsStats.get(CardLearningStatus.PARTIALLY_STUDIED));
        Assertions.assertNull(noLearnedCardsStats.get(CardLearningStatus.STUDIED));
    }

    /**
     * Получение статистики статусов карт в колоде при изменении баллов карт
     */
    @Test
    @DisplayName("Статусы карт колоды при изменении баллов карт")
    void testGetCardsCountByStatus() {
        Deck deck = new Deck("Deck");
        deck.addCard(new Card("term", "def"));
        deck.addCard(new Card("term2", "def"));

        deck.getCard("term").addScore(4);
        deck.getCard("term2").addScore(9);

        EnumMap<CardLearningStatus, Integer> cardsStats = calculator.getCardsCountByStatus(deck);
        Assertions.assertEquals(1, cardsStats.get(CardLearningStatus.NOT_STUDIED));
        Assertions.assertEquals(1, cardsStats.get(CardLearningStatus.PARTIALLY_STUDIED));
        Assertions.assertNull(cardsStats.get(CardLearningStatus.STUDIED));

        deck.getCard("term").addScore(1);
        deck.getCard("term2").addScore(1);

        cardsStats = calculator.getCardsCountByStatus(deck);
        Assertions.assertNull(cardsStats.get(CardLearningStatus.NOT_STUDIED));
        Assertions.assertEquals(1, cardsStats.get(CardLearningStatus.PARTIALLY_STUDIED));
        Assertions.assertEquals(1, cardsStats.get(CardLearningStatus.STUDIED));
    }

    /**
     * Получение процента успешности
     */
    @ParameterizedTest
    @MethodSource("provideDataForSuccessLearningPercentage")
    @DisplayName("Проверка процента успешности обучения")
    void testGetSuccessLearningPercentage(EnumMap<AnswerStatus, Integer> stats, int expectedPercentage) {
        Assertions.assertEquals(expectedPercentage, calculator.getSuccessLearningPercentage(stats));
    }

    private Stream<Arguments> provideDataForSuccessLearningPercentage() {
        return Stream.of(
                Arguments.of(new EnumMap<>(AnswerStatus.class), 0),
                Arguments.of(createStats(0, 0, 0), 0),
                Arguments.of(createStats(3, 0, 0), 100),
                Arguments.of(createStats(0, 0, 3), 0),
                Arguments.of(createStats(0, 3, 0), 50),
                Arguments.of(createStats(2, 3, 1), 58)
        );
    }

    private EnumMap<AnswerStatus, Integer> createStats(int right, int partiallyRight, int wrong) {
        EnumMap<AnswerStatus, Integer> stats = new EnumMap<>(AnswerStatus.class);
        stats.put(AnswerStatus.RIGHT, right);
        stats.put(AnswerStatus.PARTIALLY_RIGHT, partiallyRight);
        stats.put(AnswerStatus.WRONG, wrong);
        return stats;
    }
}