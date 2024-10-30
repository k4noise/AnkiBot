package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.ParserMessageComand;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

import java.util.Set;

/**
 * Тесты для команды переименование колоды
 */
class RenameDeckCommandTest {

    private SenderMessages senderMessages;
    private UserDecksData userDecksData;
    private RenameDeckCommand renameDeckCommand;

    @BeforeEach
    void setUp() {
        senderMessages = Mockito.mock(SenderMessages.class);
        userDecksData = Mockito.mock(UserDecksData.class);
        renameDeckCommand = new RenameDeckCommand(senderMessages, userDecksData);
    }


    /**
     * тест на корректных данных
     */
    @Test
    void testCorrectNames() {
        Long chatId = 987654321L;
        String commandText = "/rename-deck OldName := NewName";
        ParserMessageComand parser = new ParserMessageComand(commandText);

        DeckManager deckManager = Mockito.mock(DeckManager.class);
        Mockito.when(deckManager.getDecks()).thenReturn(Set.of(new Deck("OldName")));
        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        renameDeckCommand.execution(chatId, parser.paramsCommand());

        // Проверяем, что метод updateDeckName был вызван с правильными аргументами

        Mockito.verify(deckManager).updateDeckName("OldName", "NewName");
        Mockito.verify(senderMessages).sendMessage(chatId, "Переименование OldName -> NewName");
    }

    /**
     * тест на корректных данных названия с пробелами
     */
    @Test
    void testCorrectBigNames() {
        Long chatId = 987654321L;
        String commandText = "/rename-deck Old big Name := New Name";
        ParserMessageComand parser = new ParserMessageComand(commandText);

        DeckManager deckManager = Mockito.mock(DeckManager.class);
        Mockito.when(deckManager.getDecks()).thenReturn(Set.of(new Deck("Old big Name")));
        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        renameDeckCommand.execution(chatId, parser.paramsCommand());

        // Проверяем, что метод updateDeckName был вызван с правильными аргументами

        Mockito.verify(deckManager).updateDeckName("Old big Name", "New Name");
        Mockito.verify(senderMessages).sendMessage(chatId, "Переименование Old big Name -> New Name");
    }

    /**
     * тест с пустой коллекцией колод
     */
    @Test
    void testExecutionWithNoDecks() {
        Long chatId = 987654321L;
        String commandText = "/rename-deck OldName:= NewName";
        ParserMessageComand parser = new ParserMessageComand(commandText);

        //пустая коллекция колод
        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(new DeckManager());
        renameDeckCommand.execution(chatId, parser.paramsCommand());
        Mockito.verify(senderMessages).sendMessage(chatId, "Колода с именем OldName не существует");
    }
}

