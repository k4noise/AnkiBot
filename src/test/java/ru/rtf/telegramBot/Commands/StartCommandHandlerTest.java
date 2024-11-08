package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;

/**
 * Тестирования команды старт. Приветственное сообщение
 */
public class StartCommandHandlerTest {
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
        String ans = startCommandHandler.execution(new DeckManager(),null);
        Assertions.assertEquals("Добро пожаловать в AnkiBot. Введите команду /help, " +
                "чтобы посмотреть доступные команды", ans);
    }
}
