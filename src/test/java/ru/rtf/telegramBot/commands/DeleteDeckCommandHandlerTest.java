package ru.rtf.telegramBot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;

/**
 * Тест обработчика команды удаление колоды
 */
class DeleteDeckCommandHandlerTest {
    /**
     * Обработчик команды для удаления колоды
     */
    private DeleteDeckCommandHandler deleteDeckCommandHandler;
    /**
     * Хранилище колод пользователя
     */
    private DeckManager deckManager;
    /**
     * Идентификатор чата
     */
    private Long chatId = 1L;

    @BeforeEach
    void setUp() {
        deleteDeckCommandHandler = new DeleteDeckCommandHandler();
        deckManager = new DeckManager();
    }

    /**
     * Тест на корректных данных
     */
    @Test
    void testExistingDeck() {
        deckManager.addDeck("DelDeck");

        String message = deleteDeckCommandHandler.handle(deckManager, chatId, new String[]{"DelDeck"});
        Assertions.assertEquals("Колода DelDeck была успешно удалена", message);
    }

    /**
     * Пустой список колод
     */
    @Test
    void testEmptyDeckList() {
        String message = deleteDeckCommandHandler.handle(deckManager, chatId, new String[]{"MyDeck"});
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Колода с именем MyDeck не существует в менеджере""", message);
    }
}