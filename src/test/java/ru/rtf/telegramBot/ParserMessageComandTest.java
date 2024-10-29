package ru.rtf.telegramBot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserMessageComandTest {

    @Test
    void testNameCommandCorrect() {
        ParserMessageComand parser1 = new ParserMessageComand("/edit-card-def param1 param2");
        Assertions.assertEquals("/edit-card-def", parser1.nameCommand());

        ParserMessageComand parser2 = new ParserMessageComand("help");
        Assertions.assertEquals("help", parser2.nameCommand());

        ParserMessageComand parser3 = new ParserMessageComand("/add /del");
        Assertions.assertEquals("/add", parser3.nameCommand());
    }

    @Test
    void testNameCommandWithWithEmptyStart() {
        ParserMessageComand parser = new ParserMessageComand("     \t\n/add /del");
        Assertions.assertEquals("/add", parser.nameCommand());
    }

    @Test
    void testNameCommandWithEmptyMessage() {
        ParserMessageComand parser = new ParserMessageComand("");
        Assertions.assertNull(parser.nameCommand());

        ParserMessageComand parserSpan = new ParserMessageComand("  ");
        Assertions.assertNull(parserSpan.nameCommand());
    }

    @Test
    void testNameCommandWithNullMessage() {
        ParserMessageComand parser = new ParserMessageComand(null);
        Assertions.assertNull(parser.nameCommand());
    }

    @Test
    void testParamsCommandSpace() {
        ParserMessageComand parser = new ParserMessageComand("/edit-card-def длинный параметр с пробелами");
        String[] expectedParams = {"длинный параметр с пробелами"};
        Assertions.assertArrayEquals(expectedParams, parser.paramsCommand());
    }

    @Test
    void testParamsCommandSpaceSeveral() {
        ParserMessageComand parser = new ParserMessageComand(
                "/edit-card-def название колоды: большой термин := новое определение");
        String[] expectedParams = {"название колоды", "большой термин", "новое определение"};
        Assertions.assertArrayEquals(expectedParams, parser.paramsCommand());
    }

    @Test
    void testParamsCommandWithNoParams() {
        ParserMessageComand parser = new ParserMessageComand("/start");
        Assertions.assertNull(parser.paramsCommand());
    }

    @Test
    void testParse() {
        ParserMessageComand parser = new ParserMessageComand("");
        String[] expectedParts = {"/help:нет:пробела", "param"};
        Assertions.assertArrayEquals(expectedParts, parser.parseUsersMessage("/help:нет:пробела param"));
    }

    @Test
    void testParseMessageWithEmptyMessage() {
        ParserMessageComand parser = new ParserMessageComand("");
        Assertions.assertNull(parser.parseUsersMessage(""));
    }

    @Test
    void testParseUsersMessageWithSpacesAndEmptyValues() {
        ParserMessageComand parser = new ParserMessageComand(" /start  param1=  :param2 ");
        String[] expectedParts = {"/start", "param1", "param2"};
        Assertions.assertArrayEquals(expectedParts, parser.parseUsersMessage(" /start  param1=  :param2 "));
    }
}