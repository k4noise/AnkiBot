package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.ParserMessageComand;
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
     * корректное добавление колоды
     */
    @Test
    void testSimpleAddDeck() {
        Long chatId = 987654321L;
        String commandText = "/create-deck Deck";
        ParserMessageComand parser = new ParserMessageComand(commandText);

        Mockito.when(userDecksData.containsUser(chatId)).thenReturn(true);

        DeckManager deckManager = Mockito.mock(DeckManager.class);

        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        createDeckCommand.execution(chatId, parser.paramsCommand());

        Mockito.verify(deckManager).addDeck("Deck");
        Mockito.verify(senderMessages).sendMessage(chatId, "колода Deck добавлена");
    }

    /**
     * тест для некорректной формулировки команды
     */
    @Test
    void testIncorrectWordingCommand() {
        Long chatId = 987654321L;
        String commandText = "/create-deck name";
        ParserMessageComand parser = new ParserMessageComand(commandText);

        Mockito.when(userDecksData.containsUser(chatId)).thenReturn(true);
        DeckManager deckManager = new DeckManager();
        deckManager.addDeck("name");
        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        createDeckCommand.execution(chatId, parser.paramsCommand());

        // Проверяем отправку сообщения об ошибке
        Mockito.verify(senderMessages).sendMessage(chatId,
                "Колода с именем name уже существует, выберите другое имя");
    }
}