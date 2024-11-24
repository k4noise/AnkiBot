package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;
import ru.rtf.telegramBot.learning.AnswerStatus;

import java.util.List;


/**
 * Тестирование режима обучения "соответствие"
 */
class MatchLearningTest {
    /**
     * Карты для обучения
     */
    private List<Card> cards;
    /**
     * Экземпляр режима обучения
     */
    private MatchLearning matchLearning;

    /**
     * Создание нового экземпляра режима обучения для каждого теста
     */
    @BeforeEach
    void setUp() {
        cards = List.of(new Card("term", "def"));
        matchLearning = new MatchLearning(cards);
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
     * Проверка правильного ответа и изменения баллов карты
     */
    @Test
    @DisplayName("Правильный ответ и изменение балла")
    void testCheckRightAnswerWithNewStatus() {
        Card cardToLearn = cards.getFirst();
        Assertions.assertEquals(AnswerStatus.RIGHT, matchLearning.checkAnswer("1"));
        Assertions.assertEquals(1, cardToLearn.getScore());
    }

    /**
     * Проверка неправильного ответа и изменения баллов карты
     */
    @Test
    @DisplayName("Неправильный ответ и изменения балла")
    void testCheckWrongAnswerWithNewStatus() {
        Card cardToLearn = cards.getFirst();
        cardToLearn.addScore(2);

        Assertions.assertEquals(AnswerStatus.WRONG, matchLearning.checkAnswer("0"));
        Assertions.assertEquals(1, cardToLearn.getScore());
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