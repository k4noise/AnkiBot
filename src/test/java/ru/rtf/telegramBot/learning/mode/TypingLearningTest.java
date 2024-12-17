package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.*;
import ru.rtf.Card;
import ru.rtf.telegramBot.learning.AnswerStatus;

import java.util.List;

/**
 * Тестирование режима обучения "ввод термина"
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
     * Создание нового экземпляра режима обучения и инициализация карт перед тестами
     */
    @BeforeAll
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
}