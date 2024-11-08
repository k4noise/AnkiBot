package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;
import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тесты на редактирование карты в колоде
 */
public class EditCardDefCommandHandlerTest {
    private final Long existUser = 987654321L;
    private UserDecksData userDecksData;
    private EditCardDefCommandHandler editCardDefCommandHandler;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        editCardDefCommandHandler = new EditCardDefCommandHandler();
    }

    /**
     * Корректное изменение
     */
    @Test
    void testCorrectEditDef() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        //добавить колоду
        String deckName = "Deck";
        String term = "term", definition = "def";
        decks.addDeck("Deck");
        //добавить карту
        Deck deck = decks.getDeck(deckName);
        deck.addCard(term, definition);
        String newDefinition = "new def";

        String ans = editCardDefCommandHandler.execution(decks, new String[]{deckName, term, newDefinition});
        Card modifiedCard = deck.getCard(term);
        Assertions.assertEquals(newDefinition, modifiedCard.getDefinition(), "Определение должно измениться");
        Assertions.assertEquals("Определение карты было успешно изменено: \"term\" = new def", ans);
    }

    /**
     * Изменение несуществующей карты
     */
    @Test
    void testIncorrectTerm() {

        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");
        String ans = editCardDefCommandHandler.execution(decks, new String[]{"Deck", "term", "new def"});

        Assertions.assertEquals("Карта с термином term не существует в колоде Deck", ans);
    }

    /**
     * Тест несуществующая колода
     */
    @Test
    void testIncorrectDeck() {

        String ans = editCardDefCommandHandler.execution(userDecksData.getUserDecks(existUser),
                new String[]{"Deck", "term", "new def"});

        Assertions.assertEquals("Колода с именем Deck не существует в менеджере", ans);
    }
}
