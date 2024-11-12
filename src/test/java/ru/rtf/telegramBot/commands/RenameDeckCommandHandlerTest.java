package ru.rtf.telegramBot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;

/**
 * Тест обработчика команды переименования колоды
 */
class RenameDeckCommandHandlerTest {
    /**
     * Обработчик команды для переименования колоды
     */
    private RenameDeckCommandHandler renameDeckCommandHandler;
    /**
     * Менеджер колод пользователя
     */
    private DeckManager deckManager;
    /**
     * Идентификатор чата
     */
    private Long chatId = 1L;

    @BeforeEach
    void setUp() {
        renameDeckCommandHandler = new RenameDeckCommandHandler();
        deckManager = new DeckManager();
    }

    /**
     * Тест на корректных данных
     */
    @Test
    void testCorrectNames() {
        deckManager.addDeck("OldName");

        String message = renameDeckCommandHandler.handle(deckManager, chatId, new String[]{"OldName", "NewName"});
        Assertions.assertEquals("Колода успешно переименована: OldName -> NewName", message);
    }

    /**
     * Тест на корректных данных названия с пробелами
     */
    @Test
    void testCorrectBigNames() {
        deckManager.addDeck("Old big Name");

        String message = renameDeckCommandHandler.handle(deckManager, chatId, new String[]{"Old big Name", "New Name"});
        Assertions.assertEquals("Колода успешно переименована: Old big Name -> New Name", message);
    }

    /**
     * Тест с пустой коллекцией колод
     */
    @Test
    void testHandleWithNoDecks() {
        String message = renameDeckCommandHandler.handle(deckManager, chatId, new String[]{"OldName", "NewName"});
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Колода с именем OldName не существует в менеджере""", message);
    }
}

