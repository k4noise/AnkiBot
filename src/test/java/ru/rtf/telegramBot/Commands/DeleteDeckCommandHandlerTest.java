package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.telegramBot.UserDecksData;
import ru.rtf.DeckManager;

/**
 * Тесты для команды удаление колоды
 */
class DeleteDeckCommandHandlerTest {
    private final Long existUser = 987654321L;
    private UserDecksData userDecksData;
    private DeleteDeckCommandHandler deleteDeckCommandHandler;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        deleteDeckCommandHandler = new DeleteDeckCommandHandler();
    }

    /**
     * тест на корректных данных
     */
    @Test
    void testExistingDeck() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("DelDeck");

        String ans = deleteDeckCommandHandler.execution(decks, new String[]{"DelDeck"});
        Assertions.assertEquals("Колода DelDeck была успешно удалена", ans);
    }

    /**
     * Пустой список колод
     */
    @Test
    void testEmptyDeckList() {
        DeckManager decks = userDecksData.getUserDecks(existUser);

        String ans = deleteDeckCommandHandler.execution(decks, new String[]{"MyDeck"});
        Assertions.assertEquals("Колода с именем MyDeck не существует в менеджере", ans);
    }
}