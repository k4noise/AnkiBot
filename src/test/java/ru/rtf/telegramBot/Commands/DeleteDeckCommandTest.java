package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.Deck;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;
import ru.rtf.DeckManager;

import java.util.Set;

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

        Mockito.when(userDecksData.containsUser(chatId)).thenReturn(true);

        DeckManager deckManager = Mockito.mock(DeckManager.class);
        Mockito.when(deckManager.getDecks()).thenReturn(Set.of(new Deck("DelDeck")));
        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        deleteDeckCommand.execution(chatId, commandText);

        Mockito.verify(deckManager).removeDeck("DelDeck");
        Mockito.verify(senderMessages).sendMessage(chatId, "Колода DelDeck удалена");
    }

    /**
     * Пустой список колод
     */
    @Test
    void testEmptyDeckList() {
        Long chatId = 987654321L;
        String commandText = "/delete-deck MyDeck";

        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(null);
        deleteDeckCommand.execution(chatId, commandText);

        Mockito.verify(senderMessages).sendMessage(chatId, "У Вас пока нет ни одной колоды, создайте первую (/create-deck <название>)");

        Long chatIdEmpty = 123456789L;

        Mockito.when(userDecksData.getUserDecks(chatIdEmpty)).thenReturn(new DeckManager());
        deleteDeckCommand.execution(chatIdEmpty, commandText);

        Mockito.verify(senderMessages).sendMessage(chatIdEmpty, "У Вас пока нет ни одной колоды, создайте первую (/create-deck <название>)");

    }

    /**
     * тест для некорректной формулировки команды
     */
    @Test
    void testIncorrectWordingCommand() {
        Long chatId = 987654321L;
        String commandText = "/delete-deck";

        // "заполняем" коллекцию колод пользователя
        Mockito.when(userDecksData.containsUser(chatId)).thenReturn(true);
        DeckManager deckManager = Mockito.mock(DeckManager.class);
        Mockito.when(deckManager.getDecks()).thenReturn(Set.of(new Deck("DelDeck")));
        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        deleteDeckCommand.execution(chatId, commandText);

        Mockito.verify(senderMessages).sendMessage(chatId, "Команда отменена. Нужно ввести имя колоды.");
    }
}