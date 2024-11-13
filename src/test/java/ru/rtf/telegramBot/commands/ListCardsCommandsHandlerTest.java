package ru.rtf.telegramBot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.Deck;
import ru.rtf.DeckManager;

/**
 * Тест команды редактирования карты в колоде
 */
public class ListCardsCommandsHandlerTest {
    /**
     * Команда для отображения всех карт в колоде
     */
    private ListCardsCommandsHandler listCardsCommandsHandler;
    /**
     * Менеджер колод пользователя
     */
    private DeckManager deckManager;

    @BeforeEach
    void setUp() {
        listCardsCommandsHandler = new ListCardsCommandsHandler();
        deckManager = new DeckManager();
        deckManager.addDeck("Deck");
    }

    /**
     * Корректный вывод
     */
    @Test
    void testCorrectPrintCard() {
        Deck deck = deckManager.getDeck("Deck");
        deck.addCard("term1", "def 1");
        deck.addCard("term2", "def 2");
        deck.addCard("term3", "def 3");

        String message = listCardsCommandsHandler.handle(deckManager, new String[]{"Deck"});
        Assertions.assertEquals("""
                Deck:
                "term1" = def 1
                "term2" = def 2
                "term3" = def 3
                """, message);
    }

    /**
     * Нет карт
     */
    @Test
    void testNoCards() {
        String message = listCardsCommandsHandler.handle(deckManager, new String[]{"Deck"});

        Assertions.assertEquals("Deck:\n" +
                "В этой колоде пока нет карточек", message);
    }

    /**
     * Несуществующая колода
     */
    @Test
    void testIncorrectDeck() {
        String message = listCardsCommandsHandler.handle(deckManager, new String[]{"Deck2"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Колода с именем deck2 не существует в менеджере""", message);
    }
}
