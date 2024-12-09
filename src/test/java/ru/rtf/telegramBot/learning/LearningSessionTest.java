package ru.rtf.telegramBot.learning;

import org.junit.jupiter.api.*;
import ru.rtf.Card;

import java.util.List;

/**
 * Тестирование методов абстрактной сессии обучения на примере фейковой сессии
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LearningSessionTest {
    /**
     * Карты для обучения
     */
    private List<Card> cards;
    /**
     * Фейковая сессия для обучения
     */
    private LearningSession fakeSession;

    /**
     * Создание фейковой сессии и списка карт
     */
    @BeforeAll
    void setUp() {
        cards = List.of(new Card("term", "def"));
        fakeSession = new FakeLearningSession(cards);
    }

    /**
     * Общий тест на отсутствие активной карты для обучения
     */
    @Test
    @DisplayName("Нет активной карты")
    void testNoActiveCard() {
        Assertions.assertTrue(fakeSession.hasCardsToLearn());
        fakeSession.removeActiveCardFromStudy();
        Assertions.assertFalse(fakeSession.hasCardsToLearn());
    }

    /**
     * Тест проверки обновления статистики ответов
     */
    @Test
    @DisplayName("Проверка обновления статистики")
    void testUpdateStats() {
        fakeSession.updateStats(AnswerStatus.RIGHT);
        Assertions.assertEquals(1, fakeSession.getStats().getOrDefault(AnswerStatus.RIGHT, 0));
        Assertions.assertEquals(0, fakeSession.getStats().getOrDefault(AnswerStatus.WRONG, 0));
        Assertions.assertEquals(0, fakeSession.getStats().getOrDefault(AnswerStatus.PARTIALLY_RIGHT, 0));

        fakeSession.updateStats(AnswerStatus.RIGHT);
        fakeSession.updateStats(AnswerStatus.WRONG);
        Assertions.assertEquals(2, fakeSession.getStats().getOrDefault(AnswerStatus.RIGHT, 0));
        Assertions.assertEquals(1, fakeSession.getStats().getOrDefault(AnswerStatus.WRONG, 0));
        Assertions.assertEquals(0, fakeSession.getStats().getOrDefault(AnswerStatus.PARTIALLY_RIGHT, 0));
    }

    /**
     * Тест проверки обновления баллов карты
     */
    @Test
    @DisplayName("Проверка обновления балла карты")
    void testUpdateCardScore() {
        final Card card = cards.getFirst();
        LearningSession fakeSession = new FakeLearningSession(cards);

        fakeSession.updateCardScore(card, AnswerStatus.RIGHT);
        Assertions.assertEquals(1, card.getScore());

        fakeSession.updateCardScore(card, AnswerStatus.WRONG);
        Assertions.assertEquals(0, card.getScore());

        fakeSession.updateCardScore(card, AnswerStatus.PARTIALLY_RIGHT);
        Assertions.assertEquals(0, card.getScore());
    }
}