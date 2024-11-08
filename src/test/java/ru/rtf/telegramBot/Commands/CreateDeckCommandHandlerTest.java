package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.UserDecksData;
import org.junit.jupiter.api.Assertions;


/**
 * Тесты для команды создание новой колоды
 */
class CreateDeckCommandHandlerTest {

    private UserDecksData userDecksData;
    private CreateDeckCommandHandler createDeckCommandHandler;
    private final Long existUser = 987654321L;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        createDeckCommandHandler = new CreateDeckCommandHandler();
    }

    /**
     * Корректное добавление колоды
     */
    @Test
    void testSimpleAddDeck() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        String ans = createDeckCommandHandler.execution(decks, new String[]{"Deck"});

        Assertions.assertEquals(1, decks.getDecks().size());
        Assertions.assertEquals("Колода Deck успешно добавлена", ans);
    }

    /**
     * Тест для некорректной формулировки команды
     */
    @Test
    void testIncorrectWordingCommand() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("name");

        String ans = createDeckCommandHandler.execution(decks, new String[]{"name"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("Колода с именем name уже существует в менеджере", ans);
    }
}