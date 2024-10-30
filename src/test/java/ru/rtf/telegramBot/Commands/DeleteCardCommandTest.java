package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

public class DeleteCardCommandTest {
    private final Long existUser = 987654321L;
    private SenderMessages senderMessages;
    private UserDecksData userDecksData;
    private DeleteCardCommand deleteCardCommand;

    @BeforeEach
    void setUp() {
        senderMessages = Mockito.mock(SenderMessages.class);
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        deleteCardCommand = new DeleteCardCommand(senderMessages, userDecksData);
    }

    /**
     * Корректное удаление
     */
    @Test
    void testCorrectDelCard() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");
        decks.getDeck("Deck").addCard("del term", "def");
        deleteCardCommand.execution(existUser, new String[]{"Deck", "del term"});

        Assertions.assertEquals(0, decks.getDecks().size());
        Mockito.verify(senderMessages).sendMessage(existUser, "Карта с термином del term была успешно удалена из колоды Deck");
    }

    /**
     * Удаление несуществующей карты
     */
    @Test
    void testIncorrectTerm() {

        DeckManager deckManager = userDecksData.getUserDecks(existUser);
        deckManager.addDeck("Deck");
        deleteCardCommand.execution(existUser, new String[]{"Deck", "term"});

        // Проверяем отправку сообщения об ошибке
        Mockito.verify(senderMessages).sendMessage(existUser,
                "Карта с термином term не существует в колоде");
    }

    /**
     * Тест несуществующая колода
     */
    @Test
    void testIncorrectDeck() {

        deleteCardCommand.execution(existUser, new String[]{"Deck", "term"});

        // Проверяем отправку сообщения об ошибке
        Mockito.verify(senderMessages).sendMessage(existUser,
                "Колода с именем Deck не существует в менеджере");
    }
}
