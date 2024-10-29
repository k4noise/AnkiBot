package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.telegramBot.SenderMessages;

public class StartCommandTest {
    private SenderMessages senderMessages;
    private StartCommand startCommand;
    private final Long existUser = 987654321L;

    @BeforeEach
    void setUp() {
        senderMessages = Mockito.mock(SenderMessages.class);
        startCommand = new StartCommand(senderMessages);
    }

    /**
     * корректный вывод
     */
    @Test
    void testCorrectHelloText() {
        startCommand.execution(existUser,null);
        Mockito.verify(senderMessages).sendMessage(existUser,
                "Добро пожаловать в AnkiBot. Введите команду /help, чтобы посмотреть доступные команды");
    }
}
