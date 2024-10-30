package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rtf.telegramBot.UserDecksData;
import ru.rtf.DeckManager;

/**
 * Тесты для команды удаление колоды
 */
class DeleteDeckCommandTest {
    private final Long existUser = 987654321L;
    private UserDecksData userDecksData;
    private DeleteDeckCommand deleteDeckCommand;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        deleteDeckCommand = new DeleteDeckCommand();
    }

    /**
     * тест на корректных данных
     */
    @Test
    void testExistingDeck() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("DelDeck");

        String ans = deleteDeckCommand.execution(decks, new String[]{"DelDeck"});
        Assertions.assertEquals("Колода DelDeck была успешно удалена", ans);
    }

    /**
     * Пустой список колод
     */
    @Test
    void testEmptyDeckList() {
        DeckManager decks = userDecksData.getUserDecks(existUser);

        String ans = deleteDeckCommand.execution(decks, new String[]{"MyDeck"});
        Assertions.assertEquals("Колода с именем MyDeck не существует в менеджере", ans);
    }
}