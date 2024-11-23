package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;
import ru.rtf.Deck;
import ru.rtf.telegramBot.learning.AnswerStatus;


/**
 * Тестирование режима обучения "соответствие"
 */
class MatchLearningTest {
    /**
     * Колода для обучения
     */
    private Deck deck;
    /**
     * Экземпляр режима обучения
     */
    private MatchLearning matchLearning;

    /**
     * Создание нового экземпляра режима обучения для каждого теста
     */
    @BeforeEach
    void setUp() {
        deck = new Deck("Deck");
        deck.addCard(new Card("term", "def"));
        matchLearning = new MatchLearning(deck);
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
        Card cardToLearn = deck.getCards().iterator().next();
        Assertions.assertEquals(AnswerStatus.RIGHT, matchLearning.checkAnswer("1"));
        Assertions.assertEquals(1, cardToLearn.getScore());
        Assertions.assertEquals(1, matchLearning.getStats().get(AnswerStatus.RIGHT));
    }

    /**
     * Проверка неправильного ответа и изменения баллов карты
     */
    @Test
    @DisplayName("Неправильный ответ и изменения балла")
    void testCheckWrongAnswerWithNewStatus() {
        Card cardToLearn = deck.getCards().iterator().next();
        cardToLearn.addScore(2);

        Assertions.assertEquals(AnswerStatus.WRONG, matchLearning.checkAnswer("0"));
        Assertions.assertEquals(1, cardToLearn.getScore());
        Assertions.assertEquals(1, matchLearning.getStats().get(AnswerStatus.WRONG));
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