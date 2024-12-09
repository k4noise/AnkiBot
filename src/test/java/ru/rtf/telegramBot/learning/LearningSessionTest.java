package ru.rtf.telegramBot.learning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Тестирование методов абстрактной сессии обучения на примере фейковой сессии
 */
class LearningSessionTest {
    /**
     * Общий тест на отсутствие активной карты для обучения
     */
    @Test
    @DisplayName("Нет активной карты")
    void testNoActiveCard() {
        final List<Card> cards = List.of(new Card("term", "def"));
        LearningSession fakeSession = new FakeLearningSession(cards);
        Card activeCard = fakeSession.getActiveCard();
        Assertions.assertEquals("term", activeCard.getTerm());

        fakeSession.removeActiveCardFromStudy();

        NoSuchElementException exceptionNoActiveCard = Assertions.assertThrows(
                NoSuchElementException.class,
                fakeSession::getActiveCard,
                "Не должно остаться активной карты"
        );
        Assertions.assertEquals("Нет активной карты", exceptionNoActiveCard.getMessage());
    }
}