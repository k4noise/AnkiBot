package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import org.junit.jupiter.api.Assertions;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тест обработчика команды отображения колод пользователя
 */
public class ListDecksCommandHandlerTest {
    /**
     * id пользователя, для которого было инициализировано хранилище колод
     */
    private final Long existUser = 987654321L;
    /**
     * Хранилище колод пользователей
     */
    private UserDecksData userDecksData;
    /**
     * Обработчик команды для отображения колод
     */
    private ListDecksCommandHandler listDecksCommandHandler;


    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        listDecksCommandHandler = new ListDecksCommandHandler();
    }

    /**
     * Тест на пустом списке колод
     */
    @Test
    void testExecuteWithNoDecksEmpty() {
        // попытка выполнить команду
        String ans = listDecksCommandHandler.execute(userDecksData.getUserDecks(existUser), null);

        // Проверка, что отправляется корректное сообщение
        Assertions.assertEquals("У Вас пока нет ни одной колоды, создайте первую /create_deck <название>", ans);
    }

    /**
     * Тест на непустом списке колод
     */
    @Test
    void testExecuteWithDecks() {

        DeckManager deckManager = userDecksData.getUserDecks(existUser);
        deckManager.addDeck("first");
        deckManager.addDeck("second");

        // попытка выполнить команду
        String ans = listDecksCommandHandler.execute(deckManager, null);

        Assertions.assertEquals("""
                Ваши колоды:
                first: 0 карт
                second: 0 карт"""
                , ans);
    }
}
