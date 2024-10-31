package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тест команды удаления колоды {@link DeleteDeckCommand}
 */
class DeleteDeckCommandTest {
    /**
     * id пользователя, для которого было инициализировано хранилище колод
     */
    private final Long existUser = 987654321L;
    /**
     * Хранилище колод пользователей
     */
    private UserDecksData userDecksData;
    /**
     * Команда для удаления колоды
     */
    private DeleteDeckCommand deleteDeckCommand;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        deleteDeckCommand = new DeleteDeckCommand();
    }

    /**
     * Тест на корректных данных
     */
    @Test
    void testExistingDeck() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("DelDeck");

        String ans = deleteDeckCommand.execute(decks, new String[]{"DelDeck"});
        Assertions.assertEquals("Колода DelDeck была успешно удалена", ans);
    }

    /**
     * Пустой список колод
     */
    @Test
    void testEmptyDeckList() {
        DeckManager decks = userDecksData.getUserDecks(existUser);

        String ans = deleteDeckCommand.execute(decks, new String[]{"MyDeck"});
        Assertions.assertEquals("Колода с именем MyDeck не существует в менеджере", ans);
    }
}