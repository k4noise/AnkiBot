package ru.rtf.telegramBot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;
import ru.rtf.Deck;
import ru.rtf.DeckManager;

/**
 * Тест обработчика команды получения статистики колоды
 */
class DeckStatsCommandHandlerTest {

    /**
     * Обработчик команды для получения статистики колоды
     */
    private final DeckStatsCommandHandler deckStatsCommandHandler= new DeckStatsCommandHandler();
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
    }

    /**
     * Пустая колода
     */
    @Test
    void emptyDeckTest() {
        deckManager.addDeck("EmptyDeck");
        String message = deckStatsCommandHandler.handle(deckManager, chatId, new String[]{"EmptyDeck"});
        Assertions.assertEquals("""
                emptydeck: 0 карт
                Полностью изучено:  0
                Частично изучено:   0
                Не изучено: 0
                """, message);
    }

    /**
     * Карты с разными статусами
     */
    @Test
    void exampleDeckWithCardsTest() {
        deckManager.addDeck("Deck");

        Card bestCard = new Card("best", "лучший");
        bestCard.addScore(12);
        Card goodCard = new Card("good", "хорошо");
        goodCard.addScore(10);
        Card normalCard = new Card("normal", "нормально");
        normalCard.addScore(7);
        Card badCard = new Card("bad", "плохо");
        badCard.addScore(4);

        Deck deck = deckManager.getDeck("Deck");
        for (Card card : new Card[]{bestCard, badCard, goodCard, normalCard})
            deck.addCard(card);

        String message = deckStatsCommandHandler.handle(deckManager, chatId, new String[]{"Deck"});
        Assertions.assertEquals("""
                deck: 4 карт
                Полностью изучено:  2
                Частично изучено:   1
                Не изучено: 1
                """, message);
    }

    /**
     * Несуществующая колода
     */
    @Test
    void incorrectDeckNameTest() {
        String message = deckStatsCommandHandler.handle(deckManager, chatId, new String[]{"NoDeck"});
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Колода с именем nodeck не существует в менеджере""", message);
    }
}