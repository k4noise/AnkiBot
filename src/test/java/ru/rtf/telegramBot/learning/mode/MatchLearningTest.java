package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;

import java.util.List;
import java.util.NoSuchElementException;


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
     * Проверка формулировки вопроса к пользователю и
     * Проверка ответов
     */
    @Test
    @DisplayName("Один вопрос")
    void testFormQuestion() {
        final List<Card> cards = List.of(new Card("term", "def"));
        String question = matchLearning.formQuestion();
        Assertions.assertEquals("""
                Утверждение:
                term - def
                1 - верно, 0 - неверно""", question);
        Assertions.assertEquals(true, matchLearning.checkAnswer("1"));
        Assertions.assertEquals(false, matchLearning.checkAnswer("0"));
    }
}