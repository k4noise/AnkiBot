package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тест команды вывод карт колоды
 */
public class ListCardsCommandsTest {
    private SenderMessages senderMessages;
    private UserDecksData userDecksData;
    private ListCardsCommands listCardsCommands;
    private final Long existUser = 987654321L;

    @BeforeEach
    void setUp() {
        senderMessages = Mockito.mock(SenderMessages.class);
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        listCardsCommands = new ListCardsCommands(senderMessages, userDecksData);
    }

    /**
     * корректный вывод
     */
    @Test
    void testCorrectPrintCard() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");
        //TODO добавление карты "term1" в колоду (term1 = def 1)
        //TODO добавление карты "term2" в колоду (term2 = def 2)
        //TODO добавление карты "term3" в колоду (term3 = def 3)
        listCardsCommands.execution(existUser, new String[]{"Deck"});
        Mockito.verify(senderMessages).sendMessage(existUser, "Deck:\n" +
                "\"term1\" = def 1\n" +
                "\"term2\" = def 2\n" +
                "\"term3\" = def 3\n");
    }

    /**
     * нет карт
     */
    @Test
    void testNoCard() {

        DeckManager deckManager = userDecksData.getUserDecks(existUser);
        deckManager.addDeck("Deck");
        listCardsCommands.execution(existUser, new String[]{"Deck"});

        Mockito.verify(senderMessages).sendMessage(existUser, "Deck:\n" +
                "В этой колоде пока нет карточек");
    }

    /**
     * несуществующая колода
     */
    @Test
    void testIncorrectDeck() {

        listCardsCommands.execution(existUser, new String[]{"Deck"});

        // Проверяем отправку сообщения об ошибке
        Mockito.verify(senderMessages).sendMessage(existUser,
                "Колода с именем Deck не существует");
    }
}
