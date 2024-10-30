package ru.rtf.telegramBot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Commands.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Тесты для класса управления командами
 */
class CommandManagerTest {

    private final Map<String, Command> commands = new LinkedHashMap<>();
    private CommandManager commandManager;
    private SenderMessages senderMessages;
    private UserDecksData userDecksData;

    @BeforeEach
    void setUp() {
        senderMessages = Mockito.mock(SenderMessages.class);
        userDecksData = new UserDecksData();
        uploadCommands(senderMessages);
        commandManager = new CommandManager(commands, senderMessages, userDecksData);
    }

    /**
     * Возвращение экземпляра одной из команд
     */
    @Test
    void testReturnsCorrectCommand() {
        Command startCommand = commandManager.getCommand("/start");
        Assertions.assertNotNull(startCommand);
        Assertions.assertTrue(startCommand instanceof StartCommand);
    }

    /**
     * Попытка вызова неизвестной команды
     */
    @Test
    void testUnknownCommand() {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> commandManager.getCommand("/unknown")
        );
        Assertions.assertEquals("Команда /unknown не распознана", exception.getMessage());
    }

    /**
     * Тест на корректных данных
     */
    @Test
    void testCorrectCountParams() {
        Long chatId = 123456789L;
        userDecksData.addUser(chatId);
        DeckManager decks = userDecksData.getUserDecks(chatId);
        decks.addDeck("old name");
        commandManager.execution(chatId, "/rename-deck old name:=new name");
        Mockito.verify(senderMessages).sendMessage(chatId,
                "Колода успешно переименована: old name -> new name");
    }

    /**
     * Тест на несоответствие параметров
     */
    @Test
    void testNoCorrectCountParams() {
        Long chatId = 123456789L;
        commandManager.execution(chatId, "/rename-deck old name:=");
        Mockito.verify(senderMessages).sendMessage(chatId,
                "Ошибка параметров команды.\n Проверьте на соответствие шаблону (/help)");
    }

    /**
     * Добавление команд, их имени и экземпляра соответствующего класса в список команд
     */
    private void uploadCommands(SenderMessages senderMessages) {
        commands.put("/start", new StartCommand(senderMessages));
        commands.put("/help", new HelpCommand(senderMessages));
        //команды для работы с колодами
        commands.put("/list-decks", new ListDecksCommand(senderMessages, userDecksData));
        commands.put("/create-deck", new CreateDeckCommand(senderMessages, userDecksData));
        commands.put("/rename-deck", new RenameDeckCommand(senderMessages, userDecksData));
        commands.put("/delete-deck", new DeleteDeckCommand(senderMessages, userDecksData));
        //команды для работы с картами
        commands.put("/list-cards", new ListCardsCommands(senderMessages, userDecksData));
        commands.put("/create-card", new CreateCardCommand(senderMessages, userDecksData));
        commands.put("/edit-card-term", new EditCardTermCommand(senderMessages, userDecksData));
        commands.put("/edit-card-def", new EditCardDefCommand(senderMessages, userDecksData));
        commands.put("/delete-card", new DeleteCardCommand(senderMessages, userDecksData));
        commands.put("/list-card", new ListCardCommand(senderMessages, userDecksData));
    }
}
