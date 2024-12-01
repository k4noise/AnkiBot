package ru.rtf.telegramBot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.telegramBot.learning.SessionManager;

/**
 * Тесты для класса управления командами
 */
class CommandManagerTest {
    /**
     * Экземпляр менеджера обработчика команд
     */
    private CommandManager commandManager;

    /**
     * id нового пользователя
     */
    private final Long newChatId = 123456789L;

    /**
     * Создание нового экземпляра менеджера обработчика команд для каждого теста
     */
    @BeforeEach
    void setUp() {
        commandManager = new CommandManager(new SessionManager());
    }

    /**
     * Попытка вызова неизвестной команды
     */
    @Test
    void testUnknownCommand() {
        String message = commandManager.handle(newChatId, "/unknown");
        Assertions.assertEquals("Команда /unknown не распознана", message);
    }

    /**
     * Тест на корректных данных
     */
    @Test
    void testCorrectCountParams() {
        commandManager.handle(newChatId, "/create_deck old name");
        String message = commandManager.handle(newChatId, "/rename_deck old name:=new name");
        Assertions.assertEquals("Колода успешно переименована: old name —> new name", message);
    }

    /**
     * Тест на несоответствие параметров
     */
    @Test
    void testNoCorrectCountParams() {
        String message = commandManager.handle(newChatId, "/rename_deck old name:=");
        Assertions.assertEquals("Ошибка параметров команды.\n Проверьте на соответствие шаблону (/help)", message);
    }
}
