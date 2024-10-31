package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import org.junit.jupiter.api.Assertions;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тест команды переименования колоды {@link RenameDeckCommand}
 */
class RenameDeckCommandTest {
    /**
     * id пользователя, для которого было инициализировано хранилище колод
     */
    private final Long existUser = 987654321L;
    /**
     * Хранилище колод пользователей
     */
    private UserDecksData userDecksData;
    /**
     * Команда для переименования колоды
     */
    private RenameDeckCommand renameDeckCommand;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        renameDeckCommand = new RenameDeckCommand();
    }

    /**
     * Тест на корректных данных
     */
    @Test
    void testCorrectNames() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("OldName");

        String ans = renameDeckCommand.execute(decks, new String[]{"OldName", "NewName"});

        Assertions.assertEquals("Колода успешно переименована: OldName -> NewName", ans);
    }

    /**
     * Тест на корректных данных названия с пробелами
     */
    @Test
    void testCorrectBigNames() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Old big Name");

        String ans = renameDeckCommand.execute(decks, new String[]{"Old big Name", "New Name"});

        Assertions.assertEquals("Колода успешно переименована: Old big Name -> New Name", ans);
    }

    /**
     * Тест с пустой коллекцией колод
     */
    @Test
    void testExecuteWithNoDecks() {

        String ans = renameDeckCommand.execute(userDecksData.getUserDecks(existUser),
                new String[]{"OldName", "NewName"});
        Assertions.assertEquals("Колода с именем OldName не существует в менеджере", ans);
    }
}

