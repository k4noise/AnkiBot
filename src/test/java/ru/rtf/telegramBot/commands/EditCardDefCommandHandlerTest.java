package ru.rtf.telegramBot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;
import ru.rtf.Deck;
import ru.rtf.DeckManager;

/**
 * Тесты обработчика команды редактирования определения карты
 */
public class EditCardDefCommandHandlerTest {
    /**
     * Обработчик команды для редактирования определения карты
     */
    private EditCardDefCommandHandler editCardDefCommandHandler;
    /**
     * Менеджер колод пользователя
     */
    private DeckManager deckManager;
    /**
     * Идентификатор чата
     */
    private Long chatId = 1L;

    @BeforeEach
    void setUp() {
        editCardDefCommandHandler = new EditCardDefCommandHandler();
        deckManager = new DeckManager();
    }

    /**
     * Корректное изменение
     */
    @Test
    void testCorrectEditDef() {
        //добавить колоду
        String deckName = "Deck";
        deckManager.addDeck("Deck");

        //добавить карту
        Deck deck = deckManager.getDeck(deckName);
        deck.addCard("term", "def");

        String message = editCardDefCommandHandler.handle(deckManager, chatId, new String[]{deckName, "term", "new def"});
        Card modifiedCard = deck.getCard("term");

        Assertions.assertEquals("new def", modifiedCard.getDefinition(), "Определение должно измениться");
        Assertions.assertEquals("Определение карты было успешно изменено: \"term\" = new def", message);
    }

    /**
     * Изменение несуществующей карты
     */
    @Test
    void testIncorrectTerm() {
        deckManager.addDeck("Deck");
        String ans = editCardDefCommandHandler.handle(deckManager, chatId, new String[]{"Deck", "term", "new def"});

        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Карта с термином term не существует в колоде""", ans);
    }

    /**
     * Тест несуществующей колоды
     */
    @Test
    void testIncorrectDeck() {
        String message = editCardDefCommandHandler.handle(deckManager, chatId, new String[]{"Deck", "term", "new def"});

        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Колода с именем deck не существует в менеджере""", message);
    }
}
