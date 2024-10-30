package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

public class DeleteCardCommandTest {
    private SenderMessages senderMessages;
    private UserDecksData userDecksData;
    private DeleteCardCommand deleteCardCommand;
    private Long existUser = 987654321L;

    @BeforeEach
    void setUp() {
        senderMessages = Mockito.mock(SenderMessages.class);
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        deleteCardCommand = new DeleteCardCommand(senderMessages, userDecksData);
    }

    /**
     * корректное удаление
     */
    @Test
    void testCorrectDelCard() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");
        //TODO добавление карты "del term" в колоду
        deleteCardCommand.execution(existUser, new String[]{"Deck", "del term"});

        Assertions.assertEquals(0, decks.getDecks().size());
        Mockito.verify(senderMessages).sendMessage(existUser, "Deck: - \"name term\"");
    }

    /**
     * удаление несуществующей карты
     */
    @Test
    void testIncorrectTerm() {

        DeckManager deckManager = userDecksData.getUserDecks(existUser);
        deckManager.addDeck("Deck");
        deleteCardCommand.execution(existUser, new String[]{"Deck", "term"});

        // Проверяем отправку сообщения об ошибке
        //TODO
        Mockito.verify(senderMessages).sendMessage(existUser,
                "//TODO выводить ошибку из класса ответственного за действия над картами");
    }

    /**
     * тест несуществующая колода
     */
    @Test
    void testIncorrectDeck() {

        deleteCardCommand.execution(existUser, new String[]{"Deck", "term"});

        // Проверяем отправку сообщения об ошибке
        Mockito.verify(senderMessages).sendMessage(existUser,
                "Колода с именем Deck не существует");
    }
}
