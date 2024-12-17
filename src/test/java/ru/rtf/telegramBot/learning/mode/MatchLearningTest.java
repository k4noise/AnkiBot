package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.*;
import ru.rtf.Card;
import ru.rtf.telegramBot.learning.AnswerStatus;

import java.util.List;


/**
 * Тестирование режима обучения "соответствие"
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
     * Создание нового экземпляра режима обучения и инициализация карт перед тестами
     */
    @BeforeAll
    void setUp() {
        cards = List.of(new Card("term", "def"));
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