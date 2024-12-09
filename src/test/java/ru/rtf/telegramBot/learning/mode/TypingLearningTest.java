package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;
import ru.rtf.telegramBot.learning.AnswerStatus;

import java.util.List;

/**
 * Тестирование режима обучения "ввод термина"
 */
class TypingLearningTest {
    /**
     * Карты для обучения
     */
    private List<Card> cards;
    /**
     * Экземпляр режима обучения
     */
    private TypingLearning typingLearning;

    /**
     * Создание нового экземпляра режима обучения для каждого теста
     */
    @BeforeEach
    void setUp() {
        cards = List.of(new Card("term", "def"));
        typingLearning = new TypingLearning(cards);
    }

    /**
     * Проверка формулировки вопроса к пользователю
     * Проверка ответов
     */
    @Test
    @DisplayName("Один вопрос")
    void testFormQuestion() {
        String question = typingLearning.formQuestion();
        Assertions.assertEquals("""
                Определение - "def"
                Введите соответствующий термин:""", question);
        Assertions.assertEquals(AnswerStatus.RIGHT, typingLearning.checkAnswer("term"));
        Assertions.assertEquals(AnswerStatus.WRONG, typingLearning.checkAnswer("notTerm"));
    }

    /**
     * Проверка правильного ответа и изменения баллов карты
     */
    @Test
    @DisplayName("Правильный ответ с изменением балла")
    void testCheckRightAnswerWithNewStatus() {
        Card cardToLearn = cards.getFirst();
        Assertions.assertEquals(AnswerStatus.RIGHT, typingLearning.checkAnswer("term"));
        Assertions.assertEquals(2, cardToLearn.getScore());
    }

    /**
     * Проверка неправильного ответа и изменения баллов карты
     */
    @Test
    @DisplayName("Неправильный ответ с изменением балла")
    void testCheckWrongAnswerWithNewStatus() {
        Card cardToLearn = cards.getFirst();
        cardToLearn.addScore(2);

        Assertions.assertEquals(AnswerStatus.WRONG, typingLearning.checkAnswer("notTerm"));
        Assertions.assertEquals(1, cardToLearn.getScore());
    }
}