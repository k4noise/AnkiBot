package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.telegramBot.UserDecksData;
import ru.rtf.DeckManager;

/**
 * Тест обработчика команды удаление колоды
 */
class DeleteDeckCommandHandlerTest {
    /**
     * id пользователя, для которого было инициализировано хранилище колод
     */
    private final Long existUser = 987654321L;
    /**
     * Хранилище колод пользователей
     */
    private UserDecksData userDecksData;
    /**
     * Обработчик команды для удаления колоды
     */
    private DeleteDeckCommandHandler deleteDeckCommandHandler;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        deleteDeckCommandHandler = new DeleteDeckCommandHandler();
    }

    /**
     * Тест на корректных данных
     */
    @Test
    void testExistingDeck() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("DelDeck");

        String ans = deleteDeckCommandHandler.execute(decks, new String[]{"DelDeck"});
        Assertions.assertEquals("Колода DelDeck была успешно удалена", ans);
    }

    /**
     * Пустой список колод
     */
    @Test
    void testEmptyDeckList() {
        DeckManager decks = userDecksData.getUserDecks(existUser);

        String ans = deleteDeckCommandHandler.execute(decks, new String[]{"MyDeck"});
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Колода с именем MyDeck не существует в менеджере""", ans);
    }
}