package ru.rtf.telegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.telegramBot.Commands.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandManagerTest {

    private CommandManager commandManager;
    private SenderMessages senderMessages;
    private UserDecksData userDecksData;

    @BeforeEach
    void setUp() {
        senderMessages = mock(SenderMessages.class);
        userDecksData = mock(UserDecksData.class);
        commandManager = new CommandManager(senderMessages, userDecksData);
    }

    /**
     * Возвращение экземпляра одной из команд
     */
    @Test
    void testReturnsCorrectCommand() {
        Command startCommand = commandManager.getCommand("/start");
        assertNotNull(startCommand);
        assertTrue(startCommand instanceof StartCommand);
    }

    /**
     * попытка вызова неизвестной команды
     */
    @Test
    void testUnknownCommand() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> commandManager.getCommand("/unknown")
        );
        assertEquals("Команда /unknown не распознана", exception.getMessage());
    }
}

