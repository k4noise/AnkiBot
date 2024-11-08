package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import org.junit.jupiter.api.Assertions;
import ru.rtf.telegramBot.UserDecksData;

public class ListCardCommandHandlerTest {
    private final Long existUser = 987654321L;
    private UserDecksData userDecksData;
    private ListCardCommandHandler listCardCommandHandler;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        listCardCommandHandler = new ListCardCommandHandler();
    }

    /**
     * Корректный вывод
     */
    @Test
    void testCorrectPrintCard() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");
        decks.getDeck("Deck").addCard("term", "какое-то описание");
        String ans = listCardCommandHandler.execution(decks, new String[]{"Deck", "term"});
        Assertions.assertEquals("\"term\" = какое-то описание", ans);
    }

    /**
     * Несуществующий термин
     */
    @Test
    void testIncorrectTerm() {

        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");
        String ans = listCardCommandHandler.execution(decks, new String[]{"Deck", "term"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("Карта с термином term не существует в колоде", ans);
    }

    /**
     * Тест несуществующая колода
     */
    @Test
    void testIncorrectDeck() {

        String ans = listCardCommandHandler.execution(userDecksData.getUserDecks(existUser), new String[]{"Deck", "term"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("Колода с именем Deck не существует в менеджере", ans);
    }
}
