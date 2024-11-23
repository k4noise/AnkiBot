package ru.rtf.telegramBot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Тесты на парсера команд из сообщений пользователя {@link CommandParser}
 */
class CommandParserTest {
    /**
     * Тест проверяет корректность получения имени команды из различных сообщений.
     */
    @Test
    void testGetCommandNameCorrect() {
        CommandParser parser1 = new CommandParser("/edit-card-def param1 param2");
        Assertions.assertEquals("/edit-card-def", parser1.getCommandName());

        CommandParser parser2 = new CommandParser("help");
        Assertions.assertEquals("help", parser2.getCommandName());

        CommandParser parser3 = new CommandParser("/add /del");
        Assertions.assertEquals("/add", parser3.getCommandName());
    }

    /**
     * Тест проверяет получение имени команды из сообщения, начинающегося с пробельных символов.
     */
    @Test
    void testGetCommandNameWithEmptyStart() {
        CommandParser parser = new CommandParser("     \t\n/add /del");
        Assertions.assertEquals("/add", parser.getCommandName());
    }

    /**
     * Тест проверяет получение имени команды из пустого сообщения.
     */
    @Test
    void testGetCommandNameWithEmptyMessage() {
        CommandParser parser = new CommandParser("");
        Assertions.assertNull(parser.getCommandName());

        CommandParser parserSpan = new CommandParser("  ");
        Assertions.assertNull(parserSpan.getCommandName());
    }

    /**
     * Тест проверяет получение имени команды из null сообщения.
     */
    @Test
    void testGetCommandNameWithNullMessage() {
        CommandParser parser = new CommandParser(null);
        Assertions.assertNull(parser.getCommandName());
    }

    /**
     * Тест проверяет корректность получения параметров команды, содержащих пробелы.
     */
    @Test
    void testGetCommandParamsSpace() {
        CommandParser parser = new CommandParser("/edit-card-def длинный параметр с пробелами");
        String[] expectedParams = {"длинный параметр с пробелами"};
        Assertions.assertArrayEquals(expectedParams, parser.getCommandParams());
    }

    /**
     * Тест проверяет корректность получения нескольких параметров команды.
     */
    @Test
    void testGetCommandParamsSpaceSeveral() {
        CommandParser parser = new CommandParser(
                "/edit-card-def название колоды: большой термин := новое определение");
        String[] expectedParams = {"название колоды", "большой термин", "новое определение"};
        Assertions.assertArrayEquals(expectedParams, parser.getCommandParams());
    }

    /**
     * Тест проверяет получение параметров команды, когда команда не содержит параметров.
     */
    @Test
    void testParamsCommandWithNoGetParams() {
        CommandParser parser = new CommandParser("/start");
        Assertions.assertNull(parser.getCommandParams());
    }

    /**
     * Тест проверяет корректность парсинга сообщения пользователя.
     */
    @Test
    void testParse() {
        CommandParser parser = new CommandParser("");
        String[] expectedParts = {"/help:нет:пробела", "param"};
        Assertions.assertArrayEquals(expectedParts, parser.parseUsersMessage("/help:нет:пробела param"));
    }

    /**
     * Тест проверяет парсинг пустого сообщения.
     */
    @Test
    void testParseMessageWithEmptyMessage() {
        CommandParser parser = new CommandParser("");
        Assertions.assertNull(parser.parseUsersMessage(""));
    }

    /**
     * Тест проверяет парсинг сообщения пользователя с пробелами и пустыми значениями.
     */
    @Test
    void testParseUsersMessageWithSpacesAndEmptyValues() {
        CommandParser parser = new CommandParser(" /start  param1=  :param2 ");
        String[] expectedParts = {"/start", "param1", "param2"};
        Assertions.assertArrayEquals(expectedParts, parser.parseUsersMessage(" /start  param1=  :param2 "));
    }
}