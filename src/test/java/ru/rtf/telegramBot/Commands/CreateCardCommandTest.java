package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

public class CreateCardCommandTest {
    private SenderMessages senderMessages;
    private UserDecksData userDecksData;
    private CreateCardCommand createCardCommand;
    private final Long existUser = 987654321L;

    @BeforeEach
    void setUp() {
        senderMessages = Mockito.mock(SenderMessages.class);
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        createCardCommand = new CreateCardCommand(senderMessages, userDecksData);
    }

    /**
     * Корректное добавление карты в колоду
     */
    @Test
    void testCorrectAddCard() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");
        createCardCommand.execution(existUser, new String[]{"Deck", "name term", "Hello world"});

        Assertions.assertEquals(1, decks.getDecks().size());
        Mockito.verify(senderMessages).sendMessage(existUser, "Карта с термином name term была успешно добавлена в колоду Deck");
    }

    /**
     * Тест добавление карты с существующим термином
     */
    @Test
    void testIncorrectTerm() {

        DeckManager deckManager = userDecksData.getUserDecks(existUser);
        deckManager.addDeck("Deck");
        deckManager.getDeck("Deck").addCard("old term", "def");
        createCardCommand.execution(existUser, new String[]{"Deck", "old term", "Hello world"});

        // Проверяем отправку сообщения об ошибке
        Mockito.verify(senderMessages).sendMessage(existUser, "Карта с термином old term существует в колоде");
    }

    /**
     * Тест добавление карты в несуществующую колоду
     */
    @Test
    void testIncorrectDeck() {

        createCardCommand.execution(existUser, new String[]{"Deck", "term", "Hello world"});

        // Проверяем отправку сообщения об ошибке
        Mockito.verify(senderMessages).sendMessage(existUser, "Колода с именем Deck не существует в менеджере");
    }
}
