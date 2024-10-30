package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import org.junit.jupiter.api.Assertions;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тесты для команды просмотра имеющихся у пользователя колод
 */
public class ListDecksCommandTest {
    private final Long existUser = 987654321L;
    private UserDecksData userDecksData;
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
    void testExecutionWithNoDecksEmpty() {
        // попытка выполнить команду
        String ans = listDecksCommand.execution(userDecksData.getUserDecks(existUser), null);

        // Проверка, что отправляется корректное сообщение
        Assertions.assertEquals("У Вас пока нет ни одной колоды, создайте первую /create_deck <название>", ans);
    }

    /**
     * Тест на непустом списке колод
     */
    @Test
    void testExecutionWithDecks() {

        DeckManager deckManager = userDecksData.getUserDecks(existUser);
        deckManager.addDeck("first");
        deckManager.addDeck("second");

        // попытка выполнить команду
        String ans = listDecksCommand.execution(deckManager, null);

        Assertions.assertEquals("Ваши колоды:\nfirst: 0 карт\n" +
                "second: 0 карт\n", ans);
    }
}
