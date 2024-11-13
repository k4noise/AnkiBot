package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;

import java.util.List;

/**
 * Тестирование режима обучения "ввод термина"
 */
class TypingLearningTest {
    /**
     * Карта для обучения
     */
    private final List<Card> card = List.of(new Card("term", "def"));
    /**
     * Экземпляр режима обучения
     */
    private TypingLearning typingLearning;

    /**
     * Создание нового экземпляра режима обучения для каждого теста
     */
    @BeforeEach
    void setUp() {
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
                Определение — "def"
                Введите соответствующий термин:""", question);
    }

    /**
     * Проверка правильного ответа
     */
    @Test
    @DisplayName("Правильный ответ")
    void testCheckRightAnswer() {
        Assertions.assertEquals("""
                Верно\\! Правильный ответ:
                "term" \\= def""", typingLearning.messageCheckAnswer("term"));
    }

    /**
     * Проверка неправильного ответа
     */
    @Test
    @DisplayName("Неправильный ответ")
    void testCheckWrongAnswer() {
        Assertions.assertEquals("""
                Неверно\\. Правильный ответ:
                "term" \\= def""", typingLearning.messageCheckAnswer("notTerm"));
    }

    /**
     * Проверка формирования текстового описания активной карты
     */
    @Test
    @DisplayName("Активная карта текстом")
    void pullActiveCardDescription() {
        String card = typingLearning.pullActiveCardDescription();
        Assertions.assertEquals("\"term\" \\= def", card);
    }
}