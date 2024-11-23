package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;
import ru.rtf.Deck;
import ru.rtf.telegramBot.learning.AnswerStatus;

/**
 * Тестирование режима обучения "карточки"
 */
class MemoryLearningTest {
    /**
     * Колода для обучения
     */
    private Deck deck;
    /**
     * Экземпляр режима обучения
     */
    private MemoryLearning memoryLearning;

    /**
     * Создание нового экземпляра режима обучения для каждого теста
     */
    @BeforeEach
    void setUp() {
        deck = new Deck("Deck");
        deck.addCard(new Card("term", "def"));
        memoryLearning = new MemoryLearning(deck);
    }

    /**
     * Проверка правильного ответа и изменения баллов карты
     */
    @Test
    @DisplayName("Правильный ответ с изменением баллов")
    void testCheckRightAnswerWithNewStatus() {
        Card cardToLearn = deck.getCards().iterator().next();
        Assertions.assertEquals(AnswerStatus.RIGHT, memoryLearning.checkAnswer("2"));
        Assertions.assertEquals(1, cardToLearn.getScore());
        Assertions.assertEquals(1, memoryLearning.getStats().get(AnswerStatus.RIGHT));
    }

    /**
     * Проверка частично правильного ответа и сохранения баллов
     */
    @Test
    @DisplayName("Частично правильный ответ с сохранением баллов")
    void testCheckPartiallyRightAnswerWithSaveStatus() {
        Card cardToLearn = deck.getCards().iterator().next();
        cardToLearn.addScore(2);

        Assertions.assertEquals(AnswerStatus.PARTIALLY_RIGHT, memoryLearning.checkAnswer("1"));
        Assertions.assertEquals(2, cardToLearn.getScore());
        Assertions.assertEquals(1, memoryLearning.getStats().get(AnswerStatus.PARTIALLY_RIGHT));
    }

    /**
     * Проверка неправильного ответа и изменения баллов
     */
    @Test
    @DisplayName("Неправильный ответ с изменением баллов")
    void testCheckWrongAnswerWithNewStatus() {
        Card cardToLearn = deck.getCards().iterator().next();
        cardToLearn.addScore(2);

        Assertions.assertEquals(AnswerStatus.WRONG, memoryLearning.checkAnswer("0"));
        Assertions.assertEquals(1, cardToLearn.getScore());
        Assertions.assertEquals(1, memoryLearning.getStats().get(AnswerStatus.WRONG));
    }

    /**
     * Проверка формулировки вопроса к пользователю
     */
    @Test
    @DisplayName("Формулировка вопроса")
    void testFormQuestion() {
        String question = memoryLearning.formQuestion();
        Assertions.assertEquals("Термин - \"term\"", question);
    }

    /**
     * Проверка формирования текстового описания активной карты
     */
    @Test
    @DisplayName("Активная карта текстом")
    void pullActiveCardDescription() {
        String card = memoryLearning.pullActiveCardDescription();
        Assertions.assertEquals("\"term\" = def", card);
    }

}