package ru.rtf.telegramBot.Commands;
//TODO проверка что у разных пользователей разные колоды

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

import static org.mockito.Mockito.*;

class CreateDeckCommandTest {

    private SenderMessages senderMessages;
    private UserDecksData userDecksData;
    private CreateDeckCommand createDeckCommand;

    @BeforeEach
    void setUp() {
        senderMessages = mock(SenderMessages.class);
        userDecksData = mock(UserDecksData.class);
        createDeckCommand = new CreateDeckCommand(senderMessages, userDecksData);
    }

    /**
     * первая колода пользователя
     */
    @Test
    void testAddUserAndDeck() {
        Long chatId = 987654321L;
        String commandText = "/create-deck FirstDeck";

        when(userDecksData.containsUser(chatId)).thenReturn(false);

        DeckManager deckManager = mock(DeckManager.class);
        when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        createDeckCommand.execution(chatId, commandText);

        verify(userDecksData).addUser(chatId);
        verify(deckManager).addDeck("FirstDeck");
    }

    /**
     * Корректное добавление колоды
     */
    @Test
    void testAddDeck() {
        Long chatId = 987654321L;
        String commandText = "/create-deck NewDeck";

        when(userDecksData.containsUser(chatId)).thenReturn(true);

        DeckManager deckManager = mock(DeckManager.class);
        when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        createDeckCommand.execution(chatId, commandText);

        // Проверяем добавление новой колоды без добавления пользователя
        verify(userDecksData, never()).addUser(chatId);
        verify(deckManager).addDeck("NewDeck");
    }

    /**
     * тест для некорректной формулировки команды
     */
    @Test
    void testIncorrectWordingCommand() {
        Long chatId = 987654321L;
        String commandText = "/create-deck";

        when(userDecksData.containsUser(chatId)).thenReturn(true);
        createDeckCommand.execution(chatId, commandText);

        // Проверяем отправку сообщения об ошибке
        verify(senderMessages).sendMessage(chatId, "Команда отменена. Нужно ввести имя колоды.");
    }
}