package ru.rtf.telegramBot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;

/**
 * Тест обработчика команды отображения колод пользователя
 */
public class ListDecksCommandHandlerTest {
    /**
     * Обработчик команды для отображения колод
     */
    private ListDecksCommandHandler listDecksCommandHandler;
    /**
     * Менеджер колод пользователя
     */
    private DeckManager deckManager;

    @BeforeEach
    void setUp() {
        listDecksCommandHandler = new ListDecksCommandHandler();
        deckManager = new DeckManager();
    }

    /**
     * Тест на пустом списке колод
     */
    @Test
    void testHandleWithNoDecksEmpty() {
        // попытка выполнить команду
        String message = listDecksCommandHandler.handle(deckManager, null);

        // Проверка, что отправляется корректное сообщение
        Assertions.assertEquals("У Вас пока нет ни одной колоды, создайте первую /create_deck <название>", message);
    }

    /**
     * Тест на непустом списке колод
     */
    @Test
    void testHandleWithDecks() {
        deckManager.addDeck("first");
        deckManager.addDeck("second");

        // попытка выполнить команду
        String message = listDecksCommandHandler.handle(deckManager, null);

        Assertions.assertEquals("""
                        Ваши колоды:
                        first: 0 карт
                        second: 0 карт"""
                , message);
    }
}
