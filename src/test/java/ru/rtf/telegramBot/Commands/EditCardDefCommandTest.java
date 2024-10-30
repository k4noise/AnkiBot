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

/**
 * Тесты на редактирование карты в колоде
 */
public class EditCardDefCommandTest {
    private final Long existUser = 987654321L;
    private SenderMessages senderMessages;
    private UserDecksData userDecksData;
    private EditCardDefCommand editCardDefCommand;

    @BeforeEach
    void setUp() {
        senderMessages = Mockito.mock(SenderMessages.class);
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        editCardDefCommand = new EditCardDefCommand(senderMessages, userDecksData);
    }

    /**
     * Корректное изменение
     */
    @Test
    void testCorrectEditDef() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        String deckName = "Deck";
        String term = "term", definition = "def";
        decks.addDeck("Deck");
        Deck deck = decks.getDeck(deckName);
        deck.addCard(term, definition);

        String newDefinition = "new def";
        editCardDefCommand.execution(existUser, new String[]{deckName, term, newDefinition});
        Card modifiedCard = deck.getCard(term);
        Assertions.assertEquals(newDefinition, modifiedCard.getDefinition(), "Определение должно измениться");
        Mockito.verify(senderMessages).sendMessage(existUser, "Определение карты было успешно изменено: \"term\" = new def");
    }

    /**
     * Изменение несуществующей карты
     */
    @Test
    void testIncorrectTerm() {

        DeckManager deckManager = userDecksData.getUserDecks(existUser);
        deckManager.addDeck("Deck");
        editCardDefCommand.execution(existUser, new String[]{"Deck", "term", "new def"});

        Mockito.verify(senderMessages).sendMessage(existUser,
                "Карта с термином term не существует в колоде");
    }

    /**
     * Тест несуществующая колода
     */
    @Test
    void testIncorrectDeck() {

        editCardDefCommand.execution(existUser, new String[]{"Deck", "term", "new def"});

        Mockito.verify(senderMessages).sendMessage(existUser,
                "Колода с именем Deck не существует в менеджере");
    }
}
