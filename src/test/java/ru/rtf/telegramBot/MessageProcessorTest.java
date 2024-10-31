package ru.rtf.telegramBot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Тесты на обработчика сообщений пользователя {@link MessageProcessor}
 */
class MessageProcessorTest {

    /**
     * Тест проверяет корректность получения имени команды из различных сообщений.
     */
    @Test
    void testGetCommandNameCorrect() {
        MessageProcessor parser1 = new MessageProcessor("/edit-card-def param1 param2");
        Assertions.assertEquals("/edit-card-def", parser1.getCommandName());

        MessageProcessor parser2 = new MessageProcessor("help");
        Assertions.assertEquals("help", parser2.getCommandName());

        MessageProcessor parser3 = new MessageProcessor("/add /del");
        Assertions.assertEquals("/add", parser3.getCommandName());
    }

    /**
     * Тест проверяет получение имени команды из сообщения, начинающегося с пробельных символов.
     */
    @Test
    void testGetCommandNameWithEmptyStart() {
        MessageProcessor parser = new MessageProcessor("     \t\n/add /del");
        Assertions.assertEquals("/add", parser.getCommandName());
    }

    /**
     * Тест проверяет получение имени команды из пустого сообщения.
     */
    @Test
    void testGetCommandNameWithEmptyMessage() {
        MessageProcessor parser = new MessageProcessor("");
        Assertions.assertNull(parser.getCommandName());

        MessageProcessor parserSpan = new MessageProcessor("  ");
        Assertions.assertNull(parserSpan.getCommandName());
    }

    /**
     * Тест проверяет получение имени команды из null сообщения.
     */
    @Test
    void testGetCommandNameWithNullMessage() {
        MessageProcessor parser = new MessageProcessor(null);
        Assertions.assertNull(parser.getCommandName());
    }

    /**
     * Тест проверяет корректность получения параметров команды, содержащих пробелы.
     */
    @Test
    void testGetCommandParamsSpace() {
        MessageProcessor parser = new MessageProcessor("/edit-card-def длинный параметр с пробелами");
        String[] expectedParams = {"длинный параметр с пробелами"};
        Assertions.assertArrayEquals(expectedParams, parser.getCommandParams());
    }

    /**
     * Тест проверяет корректность получения нескольких параметров команды.
     */
    @Test
    void testGetCommandParamsSpaceSeveral() {
        MessageProcessor parser = new MessageProcessor(
                "/edit-card-def название колоды: большой термин := новое определение");
        String[] expectedParams = {"название колоды", "большой термин", "новое определение"};
        Assertions.assertArrayEquals(expectedParams, parser.getCommandParams());
    }

    /**
     * Тест проверяет получение параметров команды, когда команда не содержит параметров.
     */
    @Test
    void testParamsCommandWithNoGetParams() {
        MessageProcessor parser = new MessageProcessor("/start");
        Assertions.assertNull(parser.getCommandParams());
    }

    /**
     * Тест проверяет корректность парсинга сообщения пользователя.
     */
    @Test
    void testParse() {
        MessageProcessor parser = new MessageProcessor("");
        String[] expectedParts = {"/help:нет:пробела", "param"};
        Assertions.assertArrayEquals(expectedParts, parser.parseUsersMessage("/help:нет:пробела param"));
    }

    /**
     * Тест проверяет парсинг пустого сообщения.
     */
    @Test
    void testParseMessageWithEmptyMessage() {
        MessageProcessor parser = new MessageProcessor("");
        Assertions.assertNull(parser.parseUsersMessage(""));
    }

    /**
     * Тест проверяет парсинг сообщения пользователя с пробелами и пустыми значениями.
     */
    @Test
    void testParseUsersMessageWithSpacesAndEmptyValues() {
        MessageProcessor parser = new MessageProcessor(" /start  param1=  :param2 ");
        String[] expectedParts = {"/start", "param1", "param2"};
        Assertions.assertArrayEquals(expectedParts, parser.parseUsersMessage(" /start  param1=  :param2 "));
    }
}