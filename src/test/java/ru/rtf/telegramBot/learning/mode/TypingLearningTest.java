package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;
import ru.rtf.CardLearningStatus;

import java.util.List;

/**
 * Тестирование режима обучения "ввод термина"
 */
class TypingLearningTest {
    /**
     * Карта для обучения
     */
    private List<Card> card;
    /**
     * Экземпляр режима обучения
     */
    private TypingLearning typingLearning;

    /**
     * Создание нового экземпляра режима обучения для каждого теста
     */
    @BeforeEach
    void setUp() {
        card = List.of(new Card("term", "def"));
        typingLearning = new TypingLearning(card);
    }

    /**
     * Проверка формулировки вопроса к пользователю
     */
    @Test
    @DisplayName("Формулировка вопроса")
    void testFormQuestion() {
        String question = typingLearning.formQuestion();
        Assertions.assertEquals("""
                Определение - "def"
                Введите соответствующий термин:""", question);
    }

    /**
     * Проверка правильного ответа и изменения статуса карты
     */
    @Test
    @DisplayName("Правильный ответ с новым статусом")
    void testCheckRightAnswerWithNewStatus() {
        Card cardToLearn = card.getFirst();
        cardToLearn.addScore(3);
        Assertions.assertTrue(typingLearning.checkAnswer("term"));
        Assertions.assertEquals(CardLearningStatus.PARTIALLY_STUDIED, cardToLearn.getStatus());
    }

    /**
     * Проверка неправильного ответа и изменения статуса карты
     */
    @Test
    @DisplayName("Неправильный ответ с новым статусом")
    void testCheckWrongAnswerWithNewStatus() {
        Card cardToLearn = card.getFirst();
        cardToLearn.addScore(5);
        Assertions.assertFalse(typingLearning.checkAnswer("notTerm"));
        Assertions.assertEquals(CardLearningStatus.NOT_STUDIED, cardToLearn.getStatus());
    }

    /**
     * Проверка формирования текстового описания активной карты
     */
    @Test
    @DisplayName("Активная карта текстом")
    void getActiveCardDescription() {
        String card = typingLearning.pullActiveCardDescription();
        Assertions.assertEquals("\"term\" = def", card);
    }
}