package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.telegramBot.ParserMessageCommand;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;
import ru.rtf.DeckManager;

import java.util.NoSuchElementException;

/**
 * Тесты для команды удаление колоды
 */
class DeleteDeckCommandTest {

    private SenderMessages senderMessages;
    private UserDecksData userDecksData;
    private DeleteDeckCommand deleteDeckCommand;

    @BeforeEach
    void setUp() {
        senderMessages = Mockito.mock(SenderMessages.class);
        userDecksData = Mockito.mock(UserDecksData.class);
        deleteDeckCommand = new DeleteDeckCommand(senderMessages, userDecksData);
    }

    /**
     * тест на корректных данных
     */
    @Test
    void testExistingDeck() {
        Long chatId = 987654321L;
        String commandText = "/delete-deck DelDeck";
        ParserMessageCommand parser = new ParserMessageCommand(commandText);

        Mockito.when(userDecksData.containsUser(chatId)).thenReturn(true);

        DeckManager deckManager = new DeckManager();
        deckManager.addDeck("DelDeck");
        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        deleteDeckCommand.execution(chatId, parser.paramsCommand());
        Assertions.assertThrows(NoSuchElementException.class, () -> deckManager.getDeck("DelDeck")
        );
        Mockito.verify(senderMessages).sendMessage(chatId, "колода DelDeck удалена");
    }

    /**
     * Пустой список колод
     */
    @Test
    void testEmptyDeckList() {
        String commandText = "/delete-deck MyDeck";
        ParserMessageCommand parser = new ParserMessageCommand(commandText);

        Long chatIdEmpty = 123456789L;

        Mockito.when(userDecksData.getUserDecks(chatIdEmpty)).thenReturn(new DeckManager());
        deleteDeckCommand.execution(chatIdEmpty, parser.paramsCommand());

        Mockito.verify(senderMessages).sendMessage(chatIdEmpty, "Колода с именем MyDeck не существует");
    }
}