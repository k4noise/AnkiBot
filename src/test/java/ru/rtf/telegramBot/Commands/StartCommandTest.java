package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;

/**
 * Тест команды инициализации пользователя {@link StartCommand}
 */
public class StartCommandTest {
    /**
     * id пользователя, для которого было инициализировано хранилище колод
     */
    private final Long existUser = 987654321L;
    /**
     * Команда для инициализации пользователя
     */
    private StartCommand startCommand;

    @BeforeEach
    void setUp() {
        startCommand = new StartCommand();
    }

    /**
     * Корректный вывод
     */
    @Test
    void testCorrectHelloText() {
        String ans = startCommand.execute(new DeckManager(), null);
        Assertions.assertEquals("Добро пожаловать в AnkiBot. Введите команду /help, " +
                "чтобы посмотреть доступные команды", ans);
    }
}
