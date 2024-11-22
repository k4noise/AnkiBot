package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;
import ru.rtf.CardLearningStatus;
import ru.rtf.telegramBot.learning.AnswerStatus;

import java.util.List;

/**
 * Тестирование режима обучения "карточки"
 */
class MemoryLearningTest {

    /**
     * Карта для обучения
     */
    private final List<Card> card = List.of(new Card("term", "def"));
    /**
     * Экземпляр режима обучения
     */
    private MemoryLearning memoryLearning;

    /**
     * Создание нового экземпляра режима обучения для каждого теста
     */
    @BeforeEach
    void setUp() {
        memoryLearning = new MemoryLearning(card);
    }

    /**
     * Проверка правильного ответа и изменения статуса карты
     */
    @Test
    @DisplayName("Правильный ответ с новым статусом")
    void testCheckRightAnswerWithNewStatus() {
        Card cardToLearn = card.getFirst();
        cardToLearn.addScore(4);

        Assertions.assertEquals(AnswerStatus.RIGHT, memoryLearning.checkAnswer("2"));
        Assertions.assertEquals(CardLearningStatus.PARTIALLY_STUDIED, cardToLearn.getStatus());
        Assertions.assertEquals(1, memoryLearning.getStats().get(AnswerStatus.RIGHT));
    }

    /**
     * Проверка частично правильного ответа и сохранения статуса карты
     */
    @Test
    @DisplayName("Частично правильный ответ с сохранением статуса")
    void testCheckPartiallyRightAnswerWithSaveStatus() {
        Card cardToLearn = card.getFirst();
        cardToLearn.addScore(4);

        Assertions.assertEquals(AnswerStatus.PARTIALLY_RIGHT, memoryLearning.checkAnswer("1"));
        Assertions.assertEquals(
                CardLearningStatus.NOT_STUDIED,
                cardToLearn.getStatus(),
                "Статус не должен был измениться"
        );
        Assertions.assertEquals(1, memoryLearning.getStats().get(AnswerStatus.PARTIALLY_RIGHT));
    }

    /**
     * Проверка неправильного ответа и изменения статуса карты
     */
    @Test
    @DisplayName("Неправильный ответ с новым статусом")
    void testCheckWrongAnswerWithNewStatus() {
        Card cardToLearn = card.getFirst();
        cardToLearn.addScore(5);

        Assertions.assertEquals(AnswerStatus.WRONG, memoryLearning.checkAnswer("0"));
        Assertions.assertEquals(CardLearningStatus.NOT_STUDIED, cardToLearn.getStatus());
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