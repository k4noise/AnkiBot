package ru.rtf.telegramBot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;
import ru.rtf.Deck;
import ru.rtf.DeckManager;

/**
 * Тест обработчика команды получения общей статистики
 */
class StatsCommandHandlerTest {

    /**
     * Обработчик команды для получения статистики колоды
     */
    private StatsCommandHandler statsCommandHandler;
    /**
     * Менеджер колод пользователя
     */
    private DeckManager deckManager;
    /**
     * Идентификатор чата
     */
    private final Long chatId = 1L;

    /**
     * Создание нового экземпляра обработчика команд и менеджера колод для каждого теста
     */
    @BeforeEach
    void setUp() {
        deckManager = new DeckManager();
        statsCommandHandler = new StatsCommandHandler();
    }

    /**
     * Несколько колод. Корректный тест
     */
    @Test
    void correctTest() {
        deckManager.addDeck("First");
        deckManager.addDeck("Second");

        Card bestCard = new Card("best", "лучший");
        bestCard.addScore(12);
        Card goodCard = new Card("good", "хорошо");
        goodCard.addScore(10);
        Card normalCard = new Card("normal", "нормально");
        normalCard.addScore(7);
        Card badCard = new Card("bad", "плохо");
        badCard.addScore(4);

        Deck firstDeck = deckManager.getDeck("First");
        firstDeck.addCard(bestCard);
        firstDeck.addCard(goodCard);

        Deck secondDeck = deckManager.getDeck("Second");
        secondDeck.addCard(badCard);
        secondDeck.addCard(normalCard);

        String message = statsCommandHandler.handle(deckManager, chatId, null);

        Assertions.assertEquals("""
                Прогресс изучения колод в процентах:
                'First':\t91%
                'Second':\t45%
                """, message);
    }

    /**
     * У пользователя нет колод
     */
    @Test
    void NoDeckTest() {
        String message = statsCommandHandler.handle(deckManager, chatId, null);

        Assertions.assertEquals(
                "У Вас пока нет ни одной колоды, создайте первую /create_deck <название>",
                message);
    }

    /**
     * В колоде нет карт
     */
    @Test
    void NoCardInDeck() {
        deckManager.addDeck("Deck");
        String message = statsCommandHandler.handle(deckManager, chatId, null);

        Assertions.assertEquals("""
                Прогресс изучения колод в процентах:
                'Deck':\t0%
                """, message);
    }
}