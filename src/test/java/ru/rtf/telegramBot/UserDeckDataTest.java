package ru.rtf.telegramBot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;

/**
 * Тесты для класса связи пользователей и их колод
 */
class UserDecksDataTest {

    private UserDecksData userDecksData;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
    }

    /**
     * user при добавлении должен сохраняться в userDecksData
     */
    @Test
    void testAddUser() {
        Long chatId = 123456789L;
        userDecksData.addUser(chatId);

        Assertions.assertTrue(userDecksData.containsUser(chatId),
                "user при добавлении должен сохраняться в userDecksData");
    }

    /**
     * автоматическое создание deckManager вместе с пользователем
     */
    @Test
    void testCreatesNewDeckManager() {
        Long chatId = 987654321L;
        userDecksData.addUser(chatId);

        DeckManager deckManager = userDecksData.getUserDecks(chatId);
        Assertions.assertNotNull(deckManager, "у пользователя должен быть экземпляр deckManager");
    }

    /**
     * возвращает null для не добавленного пользователя
     */
    @Test
    void testDecksForNonExistingUser() {
        Long chatId = 202020202L;

        DeckManager deckManager = userDecksData.getUserDecks(chatId);
        Assertions.assertNull(deckManager, "Пользователь не добавлен в список, у него не должно быть колод");
    }
}
