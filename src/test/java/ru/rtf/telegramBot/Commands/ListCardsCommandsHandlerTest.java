package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тест команды вывод карт колоды
 */
public class ListCardsCommandsHandlerTest {
    private final Long existUser = 987654321L;
    private UserDecksData userDecksData;
    private ListCardsCommandsHandler listCardsCommandsHandler;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        listCardsCommandsHandler = new ListCardsCommandsHandler();
    }

    /**
     * Корректный вывод
     */
    @Test
    void testCorrectPrintCard() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");
        decks.getDeck("Deck").addCard("term1", "def 1");
        decks.getDeck("Deck").addCard("term2", "def 2");
        decks.getDeck("Deck").addCard("term3", "def 3");
        String ans = listCardsCommandsHandler.execution(decks, new String[]{"Deck"});
        Assertions.assertEquals("Deck:\n" +
                "\"term1\" = def 1\n" +
                "\"term2\" = def 2\n" +
                "\"term3\" = def 3\n", ans);
    }

    /**
     * Нет карт
     */
    @Test
    void testNoCard() {

        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");
        String ans = listCardsCommandsHandler.execution(decks, new String[]{"Deck"});

        Assertions.assertEquals("Deck:\n" +
                "В этой колоде пока нет карточек", ans);
    }

    /**
     * Несуществующая колода
     */
    @Test
    void testIncorrectDeck() {

        String ans = listCardsCommandsHandler.execution(userDecksData.getUserDecks(existUser), new String[]{"Deck"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("Колода с именем Deck не существует в менеджере", ans);
    }
}
