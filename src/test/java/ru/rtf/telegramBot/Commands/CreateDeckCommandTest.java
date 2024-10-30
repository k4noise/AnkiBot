package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.ParserMessageCommand;
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
     * Корректное добавление колоды
     */
    @Test
    void testSimpleAddDeck() {
        Long chatId = 987654321L;
        String commandText = "/create-deck Deck";
        ParserMessageCommand parser = new ParserMessageCommand(commandText);

        Mockito.when(userDecksData.containsUser(chatId)).thenReturn(true);

        DeckManager deckManager = Mockito.mock(DeckManager.class);

        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        createDeckCommand.execution(chatId, parser.paramsCommand());

        Mockito.verify(deckManager).addDeck("Deck");
        Mockito.verify(senderMessages).sendMessage(chatId, "Колода Deck успешно добавлена");
    }

    /**
     * Тест для некорректной формулировки команды
     */
    @Test
    void testIncorrectWordingCommand() {
        Long chatId = 987654321L;
        String commandText = "/create-deck name";
        ParserMessageCommand parser = new ParserMessageCommand(commandText);

        Mockito.when(userDecksData.containsUser(chatId)).thenReturn(true);
        DeckManager deckManager = new DeckManager();
        deckManager.addDeck("name");
        Mockito.when(userDecksData.getUserDecks(chatId)).thenReturn(deckManager);

        createDeckCommand.execution(chatId, parser.paramsCommand());

        // Проверяем отправку сообщения об ошибке
        Mockito.verify(senderMessages).sendMessage(chatId,
                "Колода с именем name существует в менеджере");
    }
}