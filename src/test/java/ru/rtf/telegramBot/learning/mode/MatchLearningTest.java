package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;

import java.util.List;


/**
 * Тестирование режима обучения "соответствие"
 */
class MatchLearningTest {
    /**
     * Карта для обучения
     */
    private final List<Card> card = List.of(new Card("term", "def"));
    /**
     * Экземпляр режима обучения
     */
    private MatchLearning matchLearning;

    /**
     * Создание нового экземпляра режима обучения для каждого теста
     */
    @BeforeEach
    void setUp() {
        matchLearning = new MatchLearning(card);
    }

    /**
     * Проверка формулировки вопроса к пользователю
     */
    @Test
    @DisplayName("Формулировка вопроса")
    void testFormQuestion() {
        String question = matchLearning.formQuestion();
        Assertions.assertEquals("""
                Утверждение:
                term - def
                1 - верно, 0 - неверно""", question);
    }

    /**
     * Проверка правильного ответа
     */
    @Test
    @DisplayName("Правильный ответ")
    void testCheckRightAnswer() {
        Assertions.assertTrue(matchLearning.checkAnswer("1"));
    }

    /**
     * Проверка неправильного ответа
     */
    @Test
    @DisplayName("Неправильный ответ")
    void testCheckWrongAnswer() {
        Assertions.assertFalse(matchLearning.checkAnswer("0"));
    }

    /**
     * Проверка формирования текстового описания активной карты
     */
    @Test
    @DisplayName("Активная карта текстом")
    void getActiveCardDescription() {
        String card = matchLearning.pullActiveCardDescription();
        Assertions.assertEquals("\"term\" = def", card);
    }
}