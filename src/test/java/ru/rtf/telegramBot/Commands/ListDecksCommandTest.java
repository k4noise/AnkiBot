package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import org.junit.jupiter.api.Assertions;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тест команды отображения колод пользователя {@link ListDecksCommand}
 */
public class ListDecksCommandTest {
    /**
     * id пользователя, для которого было инициализировано хранилище колод
     */
    private final Long existUser = 987654321L;
    /**
     * Хранилище колод пользователей
     */
    private UserDecksData userDecksData;
    /**
     * Команда для отображения колод
     */
    private ListDecksCommand listDecksCommand;


    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        listDecksCommand = new ListDecksCommand();
    }

    /**
     * Тест на пустом списке колод
     */
    @Test
    void testExecuteWithNoDecksEmpty() {
        // попытка выполнить команду
        String ans = listDecksCommand.execute(userDecksData.getUserDecks(existUser), null);

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
        String ans = listDecksCommand.execute(deckManager, null);

        Assertions.assertEquals("Ваши колоды:\nfirst: 0 карт\n" +
                "second: 0 карт\n", ans);
    }
}
