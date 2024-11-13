package ru.rtf.telegramBot.learning.mode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;

import java.util.List;

/**
 * Тестирование режима обучения "карточки"
 */
class MemoryLearningTest {

    /**
     * Карта для обучения
     */
    private final List<Card> card = List.of(new Card("term", "def"));
    /**
     * Экземпляр режима обучения
     */
    private MemoryLearning memoryLearning;

    /**
     * Создание нового экземпляра режима обучения для каждого теста
     */
    @BeforeEach
    void setUp() {
        memoryLearning = new MemoryLearning(card);
    }

    /**
     * Проверка формулировки вопроса к пользователю
     */
    @Test
    @DisplayName("Формулировка вопроса")
    void testFormQuestion() {
        String question = memoryLearning.formQuestion();
        Assertions.assertEquals("""
                Термин — "term"
                
                ||Правильный ответ:
                "term" — def
                Оцените свой ответ: 0 — не помню  1 — частично помню  2 — помню||""", question);
    }

    /**
     * Проверка формирования текстового описания активной карты
     */
    @Test
    @DisplayName("Активная карта текстом")
    void pullActiveCardDescription() {
        String card = memoryLearning.pullActiveCardDescription();
        Assertions.assertEquals("\"term\" \\= def", card);
    }

}