package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тест команды редактирования карты в колоде
 */
public class ListCardsCommandsHandlerTest {

    /**
     * id пользователя, для которого было инициализировано хранилище колод
     */
    private final Long existUser = 987654321L;
    /**
     * Хранилище колод пользователей
     */
    private UserDecksData userDecksData;
    /**
     * Команда для отображения всех карт в колоде
     */
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

        String ans = listCardsCommandsHandler.execute(decks, new String[]{"Deck"});
        Assertions.assertEquals("""
                Deck:
                "term1" = def 1
                "term2" = def 2
                "term3" = def 3
                """, ans);
    }

    /**
     * Нет карт
     */
    @Test
    void testNoCard() {

        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");

        String ans = listCardsCommandsHandler.execute(decks, new String[]{"Deck"});

        Assertions.assertEquals("Deck:\n" +
                "В этой колоде пока нет карточек", ans);
    }

    /**
     * Несуществующая колода
     */
    @Test
    void testIncorrectDeck() {

        String ans = listCardsCommandsHandler.execute(userDecksData.getUserDecks(existUser), new String[]{"Deck"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Колода с именем Deck не существует в менеджере""", ans);
    }
}
