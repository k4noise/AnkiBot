package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

public class EditCardDefCommandTest {
    private SenderMessages senderMessages;
    private UserDecksData userDecksData;
    private EditCardDefCommand editCardDefCommand;
    private Long existUser = 987654321L;

    @BeforeEach
    void setUp() {
        senderMessages = Mockito.mock(SenderMessages.class);
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        editCardDefCommand = new EditCardDefCommand(senderMessages, userDecksData);
    }

    /**
     * корректное изменение
     */
    @Test
    void testCorrectEditDef() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");
        //TODO добавление карты "term" в колоду
        editCardDefCommand.execution(existUser, new String[]{"Deck", "term", "new def"});
        //TODO проверка карты на изменения в колоде
        //Assertions.assertEquals();
        Mockito.verify(senderMessages).sendMessage(existUser, "Deck: \"term\" = new def");
    }

    /**
     * изменение несуществующей карты
     */
    @Test
    void testIncorrectTerm() {

        DeckManager deckManager = userDecksData.getUserDecks(existUser);
        deckManager.addDeck("Deck");
        editCardDefCommand.execution(existUser, new String[]{"Deck", "term", "new def"});

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

        editCardDefCommand.execution(existUser, new String[]{"Deck", "term", "new def"});

        // Проверяем отправку сообщения об ошибке
        Mockito.verify(senderMessages).sendMessage(existUser,
                "Колода с именем Deck не существует");
    }
}
