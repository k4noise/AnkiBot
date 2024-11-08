package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import org.junit.jupiter.api.Assertions;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тесты для команды переименование колоды
 */
class RenameDeckCommandHandlerTest {
    private final Long existUser = 987654321L;
    private UserDecksData userDecksData;
    private RenameDeckCommandHandler renameDeckCommandHandler;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        renameDeckCommandHandler = new RenameDeckCommandHandler();
    }

    /**
     * Тест на корректных данных
     */
    @Test
    void testCorrectNames() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("OldName");

        String ans = renameDeckCommandHandler.execution(decks, new String[]{"OldName", "NewName"});

        Assertions.assertEquals("Колода успешно переименована: OldName -> NewName", ans);
    }

    /**
     * Тест на корректных данных названия с пробелами
     */
    @Test
    void testCorrectBigNames() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Old big Name");

        String ans = renameDeckCommandHandler.execution(decks, new String[]{"Old big Name", "New Name"});

        Assertions.assertEquals("Колода успешно переименована: Old big Name -> New Name", ans);
    }

    /**
     * Тест с пустой коллекцией колод
     */
    @Test
    void testExecutionWithNoDecks() {

        String ans = renameDeckCommandHandler.execution(userDecksData.getUserDecks(existUser),
                new String[]{"OldName", "NewName"});
        Assertions.assertEquals("Колода с именем OldName не существует в менеджере", ans);
    }
}

