package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;
import ru.rtf.CardLearningStatus;

import java.util.ArrayList;
import java.util.List;


/**
 * Тестирование режима обучения "соответствие"
 */
class MatchLearningTest {
    /**
     * Карта для обучения
     */
    private List<Card> card;
    /**
     * Экземпляр режима обучения
     */
    private MatchLearning matchLearning;

    /**
     * Создание нового экземпляра режима обучения для каждого теста
     */
    @BeforeEach
    void setUp() {
        card = List.of(new Card("term", "def"));
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
     * Проверка правильного ответа и изменения статуса карты
     */
    @Test
    @DisplayName("Правильный ответ с новым статусом")
    void testCheckRightAnswerWithNewStatus() {
        Card cardToLearn = card.getFirst();
        cardToLearn.addScore(4);
        Assertions.assertTrue(matchLearning.checkAnswer("1"));
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
        Assertions.assertFalse(matchLearning.checkAnswer("0"));
        Assertions.assertEquals(CardLearningStatus.NOT_STUDIED, cardToLearn.getStatus());

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