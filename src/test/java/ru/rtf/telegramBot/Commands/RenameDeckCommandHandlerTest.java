package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import org.junit.jupiter.api.Assertions;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тест обработчика команды переименования колоды
 */
class RenameDeckCommandHandlerTest {
    /**
     * id пользователя, для которого было инициализировано хранилище колод
     */
    private final Long existUser = 987654321L;
    /**
     * Хранилище колод пользователей
     */
    private UserDecksData userDecksData;
    /**
     * Обработчик команды для переименования колоды
     */
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

        String ans = renameDeckCommandHandler.execute(decks, new String[]{"OldName", "NewName"});

        Assertions.assertEquals("Колода успешно переименована: OldName -> NewName", ans);
    }

    /**
     * Тест на корректных данных названия с пробелами
     */
    @Test
    void testCorrectBigNames() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Old big Name");

        String ans = renameDeckCommandHandler.execute(decks, new String[]{"Old big Name", "New Name"});

        Assertions.assertEquals("Колода успешно переименована: Old big Name -> New Name", ans);
    }

    /**
     * Тест с пустой коллекцией колод
     */
    @Test
    void testExecuteWithNoDecks() {

        String ans = renameDeckCommandHandler.execute(userDecksData.getUserDecks(existUser),
                new String[]{"OldName", "NewName"});
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Колода с именем OldName не существует в менеджере""", ans);
    }
}

