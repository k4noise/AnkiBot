package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.UserDecksData;

public class CreateCardCommandTest {
    private UserDecksData userDecksData;
    private CreateCardCommand createCardCommand;
    private final Long existUser = 987654321L;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        createCardCommand = new CreateCardCommand();
    }

    /**
     * Корректное добавление карты в колоду
     */
    @Test
    void testCorrectAddCard() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");
        String ans = createCardCommand.execution(decks, new String[]{"Deck", "name term", "Hello world"});

        Assertions.assertEquals(1, decks.getDecks().size());
        Assertions.assertEquals("Карта с термином name term была успешно добавлена в колоду Deck", ans);
    }

    /**
     * Тест добавление карты с существующим термином
     */
    @Test
    void testIncorrectTerm() {

        DeckManager deckManager = userDecksData.getUserDecks(existUser);
        deckManager.addDeck("Deck");
        deckManager.getDeck("Deck").addCard("old term", "def");
        String ans = createCardCommand.execution(deckManager, new String[]{"Deck", "old term", "Hello world"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("Карта с термином old term существует в колоде", ans);
    }

    /**
     * Тест добавление карты в несуществующую колоду
     */
    @Test
    void testIncorrectDeck() {

        String ans = createCardCommand.execution(userDecksData.getUserDecks(existUser), new String[]{"Deck", "term", "Hello world"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("Колода с именем Deck не существует в менеджере", ans);
    }
}
