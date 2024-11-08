package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тест обработчика команды создания новой колоды
 */
class CreateDeckCommandHandlerTest {

    /**
     * id пользователя, для которого было инициализировано хранилище колод
     */
    private final Long existUser = 987654321L;
    /**
     * Хранилище колод пользователей
     */
    private UserDecksData userDecksData;
    /**
     * Обработчик команды для создания новой колоды
     */
    private CreateDeckCommandHandler createDeckCommandHandler;


    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        createDeckCommandHandler = new CreateDeckCommandHandler();
    }

    /**
     * Корректное добавление колоды
     */
    @Test
    void testSimpleAddDeck() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        String ans = createDeckCommandHandler.execute(decks, new String[]{"Deck"});

        Assertions.assertEquals(1, decks.getDecks().size());
        Assertions.assertEquals("Колода Deck успешно добавлена", ans);
    }

    /**
     * Тест для некорректной формулировки команды
     */
    @Test
    void testIncorrectWordingCommand() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("name");

        String ans = createDeckCommandHandler.execute(decks, new String[]{"name"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Колода с именем name существует в менеджере""", ans);
    }
}