package ru.rtf.telegramBot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserMessageCommandHandlerTest {

    @Test
    void testNameCommandCorrect() {
        ParserMessageCommand parser1 = new ParserMessageCommand("/edit-card-def param1 param2");
        Assertions.assertEquals("/edit-card-def", parser1.nameCommand());

        ParserMessageCommand parser2 = new ParserMessageCommand("help");
        Assertions.assertEquals("help", parser2.nameCommand());

        ParserMessageCommand parser3 = new ParserMessageCommand("/add /del");
        Assertions.assertEquals("/add", parser3.nameCommand());
    }

    @Test
    void testNameCommandWithWithEmptyStart() {
        ParserMessageCommand parser = new ParserMessageCommand("     \t\n/add /del");
        Assertions.assertEquals("/add", parser.nameCommand());
    }

    @Test
    void testNameCommandWithEmptyMessage() {
        ParserMessageCommand parser = new ParserMessageCommand("");
        Assertions.assertNull(parser.nameCommand());

        ParserMessageCommand parserSpan = new ParserMessageCommand("  ");
        Assertions.assertNull(parserSpan.nameCommand());
    }

    @Test
    void testNameCommandWithNullMessage() {
        ParserMessageCommand parser = new ParserMessageCommand(null);
        Assertions.assertNull(parser.nameCommand());
    }

    @Test
    void testParamsCommandSpace() {
        ParserMessageCommand parser = new ParserMessageCommand("/edit-card-def длинный параметр с пробелами");
        String[] expectedParams = {"длинный параметр с пробелами"};
        Assertions.assertArrayEquals(expectedParams, parser.paramsCommand());
    }

    @Test
    void testParamsCommandSpaceSeveral() {
        ParserMessageCommand parser = new ParserMessageCommand(
                "/edit-card-def название колоды: большой термин := новое определение");
        String[] expectedParams = {"название колоды", "большой термин", "новое определение"};
        Assertions.assertArrayEquals(expectedParams, parser.paramsCommand());
    }

    @Test
    void testParamsCommandWithNoParams() {
        ParserMessageCommand parser = new ParserMessageCommand("/start");
        Assertions.assertNull(parser.paramsCommand());
    }

    @Test
    void testParse() {
        ParserMessageCommand parser = new ParserMessageCommand("");
        String[] expectedParts = {"/help:нет:пробела", "param"};
        Assertions.assertArrayEquals(expectedParts, parser.parseUsersMessage("/help:нет:пробела param"));
    }

    @Test
    void testParseMessageWithEmptyMessage() {
        ParserMessageCommand parser = new ParserMessageCommand("");
        Assertions.assertNull(parser.parseUsersMessage(""));
    }

    @Test
    void testParseUsersMessageWithSpacesAndEmptyValues() {
        ParserMessageCommand parser = new ParserMessageCommand(" /start  param1=  :param2 ");
        String[] expectedParts = {"/start", "param1", "param2"};
        Assertions.assertArrayEquals(expectedParts, parser.parseUsersMessage(" /start  param1=  :param2 "));
    }
}