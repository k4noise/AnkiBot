package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;
import ru.rtf.telegramBot.learning.AnswerStatus;

import java.util.List;

/**
 * Тестирование режима обучения "карточки"
 */
class MemoryLearningTest {
    /**
     * Карты для обучения
     */
    private List<Card> cards;
    /**
     * Экземпляр режима обучения
     */
    private MemoryLearning memoryLearning;

    /**
     * Создание нового экземпляра режима обучения для каждого теста
     */
    @BeforeEach
    void setUp() {
        cards = List.of(new Card("term", "def"));
        memoryLearning = new MemoryLearning(cards);
    }

    /**
     * Проверка правильного ответа и изменения баллов карты
     */
    @Test
    @DisplayName("Правильный ответ с изменением баллов")
    void testCheckRightAnswerWithNewStatus() {
        Card cardToLearn = cards.getFirst();
        Assertions.assertEquals(AnswerStatus.RIGHT, memoryLearning.checkAnswer("2"));
        Assertions.assertEquals(1, cardToLearn.getScore());
    }

    /**
     * Проверка частично правильного ответа и сохранения баллов
     */
    @Test
    @DisplayName("Частично правильный ответ с сохранением баллов")
    void testCheckPartiallyRightAnswerWithSaveStatus() {
        Card cardToLearn = cards.getFirst();
        cardToLearn.addScore(2);

        Assertions.assertEquals(AnswerStatus.PARTIALLY_RIGHT, memoryLearning.checkAnswer("1"));
        Assertions.assertEquals(2, cardToLearn.getScore());
    }

    /**
     * Проверка неправильного ответа и изменения баллов
     */
    @Test
    @DisplayName("Неправильный ответ с изменением баллов")
    void testCheckWrongAnswerWithNewStatus() {
        Card cardToLearn = cards.getFirst();
        cardToLearn.addScore(2);

        Assertions.assertEquals(AnswerStatus.WRONG, memoryLearning.checkAnswer("0"));
        Assertions.assertEquals(1, cardToLearn.getScore());
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