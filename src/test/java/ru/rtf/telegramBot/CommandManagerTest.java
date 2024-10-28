package ru.rtf.telegramBot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.telegramBot.Commands.*;

/**
 * Тесты для класса управления командами
 */
class CommandManagerTest {

    private CommandManager commandManager;
    private SenderMessages senderMessages;
    private UserDecksData userDecksData;

    @BeforeEach
    void setUp() {
        senderMessages = Mockito.mock(SenderMessages.class);
        userDecksData = Mockito.mock(UserDecksData.class);
        commandManager = new CommandManager(senderMessages, userDecksData);
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
     * попытка вызова неизвестной команды
     */
    @Test
    void testUnknownCommand() {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> commandManager.getCommand("/unknown")
        );
        Assertions.assertEquals("Команда /unknown не распознана", exception.getMessage());
    }
}

