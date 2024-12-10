package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;
import ru.rtf.telegramBot.learning.AnswerStatus;

import java.util.List;
import java.util.NoSuchElementException;


/**
 * Тестирование режима обучения "соответствие"
 */
class MatchLearningTest {
    /**
     * Карты для обучения
     */
    private final List<Card> cards = List.of(new Card("term", "def"));
    /**
     * Экземпляр режима обучения
     */
    private MatchLearning matchLearning;

    /**
     * Создание нового экземпляра режима обучения для каждого теста
     */
    @BeforeEach
    void setUp() {
        matchLearning = new MatchLearning(cards);
    }

    /**
     * Проверка формулировки вопроса к пользователю и
     * Проверка ответов
     */
    @Test
    @DisplayName("Один вопрос")
    void testFormQuestion() {
        String question = matchLearning.formQuestion();
        Assertions.assertEquals("""
                Утверждение:
                term - def
                1 - верно, 0 - неверно""", question);
        Assertions.assertEquals(AnswerStatus.RIGHT, matchLearning.checkAnswer("1"));
        Assertions.assertEquals(AnswerStatus.WRONG, matchLearning.checkAnswer("0"));
    }
}