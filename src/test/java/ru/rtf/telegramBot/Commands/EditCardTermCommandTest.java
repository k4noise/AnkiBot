package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тесты команды редактирования термина карты {@link EditCardTermCommand}
 */
public class EditCardTermCommandTest {
    /**
     * id пользователя, для которого было инициализировано хранилище колод
     */
    private final Long existUser = 987654321L;
    /**
     * Хранилище колод пользователей
     */
    private UserDecksData userDecksData;
    /**
     * Команда для редактирования термина карты
     */
    private EditCardTermCommand editCardTermCommand;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        editCardTermCommand = new EditCardTermCommand();
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
        String ans = editCardTermCommand.execute(decks, new String[]{deckName, term, newTerm});
        Assertions.assertEquals("Термин карты был успешно изменен: \"new term\" = def", ans);
    }

    /**
     * Изменение несуществующей карты
     */
    @Test
    void testIncorrectTerm() {

        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");
        String ans = editCardTermCommand.execute(decks, new String[]{"Deck", "term", "new term"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("Карта с термином term не существует в колоде", ans);
    }

    /**
     * Тест несуществующая колода
     */
    @Test
    void testIncorrectDeck() {

        String ans = editCardTermCommand.execute(userDecksData.getUserDecks(existUser),
                new String[]{"Deck", "term", "new term"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("Колода с именем Deck не существует в менеджере", ans);
    }
}
