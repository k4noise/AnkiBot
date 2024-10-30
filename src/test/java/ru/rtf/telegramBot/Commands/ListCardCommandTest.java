package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

public class ListCardCommandTest {
    private final Long existUser = 987654321L;
    private SenderMessages senderMessages;
    private UserDecksData userDecksData;
    private ListCardCommand listCardCommand;

    @BeforeEach
    void setUp() {
        senderMessages = Mockito.mock(SenderMessages.class);
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        listCardCommand = new ListCardCommand(senderMessages, userDecksData);
    }

    /**
     * Корректный вывод
     */
    @Test
    void testCorrectPrintCard() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");
        decks.getDeck("Deck").addCard("term", "какое-то описание");
        listCardCommand.execution(existUser, new String[]{"Deck", "term"});
        Mockito.verify(senderMessages).sendMessage(existUser, "\"term\" = какое-то описание");
    }

    /**
     * Несуществующий термин
     */
    @Test
    void testIncorrectTerm() {

        DeckManager deckManager = userDecksData.getUserDecks(existUser);
        deckManager.addDeck("Deck");
        listCardCommand.execution(existUser, new String[]{"Deck", "term"});

        // Проверяем отправку сообщения об ошибке
        //TODO
        Mockito.verify(senderMessages).sendMessage(existUser,
                "Карта с термином term не существует в колоде");
    }

    /**
     * Тест несуществующая колода
     */
    @Test
    void testIncorrectDeck() {

        listCardCommand.execution(existUser, new String[]{"Deck", "term"});

        // Проверяем отправку сообщения об ошибке
        Mockito.verify(senderMessages).sendMessage(existUser,
                "Колода с именем Deck не существует в менеджере");
    }
}
