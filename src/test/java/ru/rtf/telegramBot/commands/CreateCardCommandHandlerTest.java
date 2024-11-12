package ru.rtf.telegramBot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;

/**
 * Тест обработчика команды создания новой карты в колоде
 */
public class CreateCardCommandHandlerTest {
    /**
     * Команда для создания новой карты в колоде
     */
    private CreateCardCommandHandler createCardCommandHandler;
    /**
     * Идентификатор чата
     */
    private Long chatId = 1L;

    @BeforeEach
    void setUp() {
        createCardCommandHandler = new CreateCardCommandHandler();
    }

    /**
     * Корректное добавление карты в колоду
     */
    @Test
    void testCorrectAddCard() {
        DeckManager deckManager = new DeckManager();
        deckManager.addDeck("Deck");
        String ans = createCardCommandHandler.handle(deckManager, chatId, new String[]{"Deck", "name term", "Hello world"});

        Assertions.assertEquals(1, deckManager.getDecks().size());
        Assertions.assertEquals("Карта с термином name term была успешно добавлена в колоду Deck", ans);
    }

    /**
     * Тест добавление карты с существующим термином
     */
    @Test
    void testIncorrectTerm() {
        DeckManager deckManager = new DeckManager();
        deckManager.addDeck("Deck");
        deckManager.getDeck("Deck").addCard("old term", "def");
        String ans = createCardCommandHandler.handle(deckManager, chatId, new String[]{"Deck", "old term", "Hello world"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Карта с термином old term существует в колоде""", ans);
    }

    /**
     * Тест добавление карты в несуществующую колоду
     */
    @Test
    void testIncorrectDeck() {
        DeckManager deckManager = new DeckManager();
        String ans = createCardCommandHandler.handle(deckManager, chatId, new String[]{"Deck", "term", "Hello world"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Колода с именем Deck не существует в менеджере""", ans);
    }
}
