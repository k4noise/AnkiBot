package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.Deck;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;
import ru.rtf.DeckManager;

import java.util.Set;

import static org.mockito.Mockito.*;

class DeleteDeckCommandTest {

    private SenderMessages senderMessages;
    private UserDecksData userDecksData;
    private DeleteDeckCommand deleteDeckCommand;

    @BeforeEach
    void setUp() {
        senderMessages = mock(SenderMessages.class);
        userDecksData = mock(UserDecksData.class);
        deleteDeckCommand = new DeleteDeckCommand(senderMessages, userDecksData);
    }

    /**
     * тест на корректных данных
     */
    @Test
    void testExistingDeck() {
        Long chatId = 987654321L;
        String commandText = "/delete-deck DelDeck";

        when(userDecksData.containsUser(chatId)).thenReturn(true);

        DeckManager deckManager = mock(DeckManager.class);
        when(deckManager.getDecks()).thenReturn(Set.of(new Deck("DelDeck")));
        when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        deleteDeckCommand.execution(chatId, commandText);

        verify(deckManager).removeDeck("DelDeck");
        verify(senderMessages).sendMessage(chatId, "Колода DelDeck удалена");
    }

    /**
     * Пустой список колод
     */
    @Test
    void testEmptyDeckList() {
        Long chatId = 987654321L;
        String commandText = "/delete-deck MyDeck";

        when(userDecksData.getUserDecks(chatId)).thenReturn(null);
        deleteDeckCommand.execution(chatId, commandText);

        verify(senderMessages).sendMessage(chatId, "У Вас пока нет ни одной колоды, создайте первую (/create-deck <название>)");

        Long chatIdEmpty = 123456789L;

        when(userDecksData.getUserDecks(chatIdEmpty)).thenReturn(new DeckManager());
        deleteDeckCommand.execution(chatIdEmpty, commandText);

        verify(senderMessages).sendMessage(chatIdEmpty, "У Вас пока нет ни одной колоды, создайте первую (/create-deck <название>)");

    }

    /**
     * тест для некорректной формулировки команды
     */
    @Test
    void testIncorrectWordingCommand() {
        Long chatId = 987654321L;
        String commandText = "/delete-deck";

        // "заполняем" коллекцию колод пользователя
        when(userDecksData.containsUser(chatId)).thenReturn(true);
        DeckManager deckManager = mock(DeckManager.class);
        when(deckManager.getDecks()).thenReturn(Set.of(new Deck("DelDeck")));
        when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        deleteDeckCommand.execution(chatId, commandText);

        verify(senderMessages).sendMessage(chatId, "Команда отменена. Нужно ввести имя колоды.");
    }
}