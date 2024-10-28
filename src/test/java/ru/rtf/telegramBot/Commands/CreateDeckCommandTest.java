package ru.rtf.telegramBot.Commands;
//TODO проверка что у разных пользователей разные колоды

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тесты для команды создание новой колоды
 */
class CreateDeckCommandTest {

    private SenderMessages senderMessages;
    private UserDecksData userDecksData;
    private CreateDeckCommand createDeckCommand;

    @BeforeEach
    void setUp() {
        senderMessages = Mockito.mock(SenderMessages.class);
        userDecksData = Mockito.mock(UserDecksData.class);
        createDeckCommand = new CreateDeckCommand(senderMessages, userDecksData);
    }

    /**
     * первая колода пользователя
     */
    @Test
    void testAddUserAndDeck() {
        Long chatId = 987654321L;
        String commandText = "/create-deck FirstDeck";

        Mockito.when(userDecksData.containsUser(chatId)).thenReturn(false);

        DeckManager deckManager = Mockito.mock(DeckManager.class);
        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        createDeckCommand.execution(chatId, commandText);

        Mockito.verify(userDecksData).addUser(chatId);
        Mockito.verify(deckManager).addDeck("FirstDeck");
    }

    /**
     * Корректное добавление колоды
     */
    @Test
    void testAddDeck() {
        Long chatId = 987654321L;
        String commandText = "/create-deck NewDeck";

        Mockito.when(userDecksData.containsUser(chatId)).thenReturn(true);

        DeckManager deckManager = Mockito.mock(DeckManager.class);
        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        createDeckCommand.execution(chatId, commandText);

        // Проверяем добавление новой колоды без добавления пользователя
        Mockito.verify(userDecksData, Mockito.never()).addUser(chatId);
        Mockito.verify(deckManager).addDeck("NewDeck");
    }

    /**
     * тест для некорректной формулировки команды
     */
    @Test
    void testIncorrectWordingCommand() {
        Long chatId = 987654321L;
        String commandText = "/create-deck";

        Mockito.when(userDecksData.containsUser(chatId)).thenReturn(true);
        createDeckCommand.execution(chatId, commandText);

        // Проверяем отправку сообщения об ошибке
        Mockito.verify(senderMessages).sendMessage(chatId, "Команда отменена. Нужно ввести имя колоды.");
    }
}