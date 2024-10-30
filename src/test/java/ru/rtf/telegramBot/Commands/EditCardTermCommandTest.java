package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.Card;
import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

public class EditCardTermCommandTest {
    private final Long existUser = 987654321L;
    private SenderMessages senderMessages;
    private UserDecksData userDecksData;
    private EditCardTermCommand editCardTermCommand;

    @BeforeEach
    void setUp() {
        senderMessages = Mockito.mock(SenderMessages.class);
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        editCardTermCommand = new EditCardTermCommand(senderMessages, userDecksData);
    }

    /**
     * Корректное изменение
     */
    @Test
    void testCorrectEditTerm() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        String deckName = "Deck";
        String term = "term", definition = "def";

        decks.addDeck(deckName);
        decks.getDeck(deckName).addCard(term, definition);

        String newTerm = "new term";
        editCardTermCommand.execution(existUser, new String[]{deckName, term, newTerm});
        Mockito.verify(senderMessages).sendMessage(existUser, "Deck: \"term\" = def");
    }

    /**
     * Изменение несуществующей карты
     */
    @Test
    void testIncorrectTerm() {

        DeckManager deckManager = userDecksData.getUserDecks(existUser);
        deckManager.addDeck("Deck");
        editCardTermCommand.execution(existUser, new String[]{"Deck", "term", "new term"});

        // Проверяем отправку сообщения об ошибке
        Mockito.verify(senderMessages).sendMessage(existUser,
                "Карта с термином term не существует в колоде");
    }

    /**
     * Тест несуществующая колода
     */
    @Test
    void testIncorrectDeck() {

        editCardTermCommand.execution(existUser, new String[]{"Deck", "term", "new term"});

        // Проверяем отправку сообщения об ошибке
        Mockito.verify(senderMessages).sendMessage(existUser,
                "Колода с именем Deck не существует в менеджере");
    }
}
