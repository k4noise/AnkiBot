package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.*;
import ru.rtf.Card;
import ru.rtf.telegramBot.learning.AnswerStatus;

import java.util.List;

/**
 * Тестирование режима обучения "карточки"
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
     * Создание нового экземпляра режима обучения и инициализация карт перед тестами
     */
    @BeforeAll
    void setUp() {
        cards = List.of(new Card("term", "def"));
        memoryLearning = new MemoryLearning(cards);
    }

    /**
     * Проверка формулировки вопроса к пользователю
     * Проверка ответов
     */
    @Test
    @DisplayName("Один вопрос")
    void testFormQuestion() {
        String question = memoryLearning.formQuestion();
        Assertions.assertEquals("Термин - \"term\"", question);
        Assertions.assertEquals(AnswerStatus.RIGHT, memoryLearning.checkAnswer("2"));
        Assertions.assertEquals(AnswerStatus.PARTIALLY_RIGHT, memoryLearning.checkAnswer("1"));
        Assertions.assertEquals(AnswerStatus.WRONG, memoryLearning.checkAnswer("0"));
    }
}