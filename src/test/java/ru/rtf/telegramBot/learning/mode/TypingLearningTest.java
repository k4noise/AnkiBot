package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;
import ru.rtf.Deck;
import ru.rtf.telegramBot.learning.AnswerStatus;

/**
 * Тестирование режима обучения "ввод термина"
 */
class TypingLearningTest {
    /**
     * Колода для обучения
     */
    private Deck deck;
    /**
     * Экземпляр режима обучения
     */
    private TypingLearning typingLearning;

    /**
     * Создание нового экземпляра режима обучения для каждого теста
     */
    @BeforeEach
    void setUp() {
        deck = new Deck("Deck");
        deck.addCard(new Card("term", "def"));
        typingLearning = new TypingLearning(deck);
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
     * Проверка правильного ответа и изменения баллов карты
     */
    @Test
    @DisplayName("Правильный ответ с изменением балла")
    void testCheckRightAnswerWithNewStatus() {
        Card cardToLearn = deck.getCards().iterator().next();
        Assertions.assertEquals(AnswerStatus.RIGHT, typingLearning.checkAnswer("term"));
        Assertions.assertEquals(2, cardToLearn.getScore());
        Assertions.assertEquals(1, typingLearning.getStats().get(AnswerStatus.RIGHT));
    }

    /**
     * Проверка неправильного ответа и изменения баллов карты
     */
    @Test
    @DisplayName("Неправильный ответ с изменением балла")
    void testCheckWrongAnswerWithNewStatus() {
        Card cardToLearn = deck.getCards().iterator().next();
        cardToLearn.addScore(2);

        Assertions.assertEquals(AnswerStatus.WRONG, typingLearning.checkAnswer("notTerm"));
        Assertions.assertEquals(1, cardToLearn.getScore());
        Assertions.assertEquals(1, typingLearning.getStats().get(AnswerStatus.WRONG));
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