package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;

/**
 * Тестирования команды старт. Приветственное сообщение
 */
public class StartCommandTest {
    private StartCommand startCommand;
    private final Long existUser = 987654321L;

    @BeforeEach
    void setUp() {
        startCommand = new StartCommand();
    }

    /**
     * Корректный вывод
     */
    @Test
    void testCorrectHelloText() {
        String ans = startCommand.execution(new DeckManager(),null);
        Assertions.assertEquals("Добро пожаловать в AnkiBot. Введите команду /help, " +
                "чтобы посмотреть доступные команды", ans);
    }
}
