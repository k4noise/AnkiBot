package ru.rtf.telegramBot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;

/**
 * Тест обработчика команды инициализации пользователя
 */
public class StartCommandHandlerTest {

    /**
     * Команда для инициализации пользователя
     */
    private StartCommandHandler startCommandHandler;

    @BeforeEach
    void setUp() {
        startCommandHandler = new StartCommandHandler();
    }

    /**
     * Корректный вывод
     */
    @Test
    void testCorrectHelloText() {
        String ans = startCommandHandler.handle(new DeckManager(), 1L, null);
        Assertions.assertEquals("Добро пожаловать в AnkiBot. Введите команду /help, " +
                "чтобы посмотреть доступные команды", ans);
    }
}
