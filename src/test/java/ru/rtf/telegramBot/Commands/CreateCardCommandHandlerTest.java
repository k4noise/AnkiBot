package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тест обработчика команды создания новой карты в колоде
 */
public class CreateCardCommandHandlerTest {
    /**
     * id пользователя, для которого было инициализировано хранилище колод
     */
    private final Long existUser = 987654321L;
    /**
     * Хранилище колод пользователей
     */
    private UserDecksData userDecksData;
    /**
     * Команда для создания новой карты в колоде
     */
    private CreateCardCommandHandler createCardCommandHandler;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        createCardCommandHandler = new CreateCardCommandHandler();
    }

    /**
     * Корректное добавление карты в колоду
     */
    @Test
    void testCorrectAddCard() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");
        String ans = createCardCommandHandler.execute(decks, new String[]{"Deck", "name term", "Hello world"});

        Assertions.assertEquals(1, decks.getDecks().size());
        Assertions.assertEquals("Карта с термином name term была успешно добавлена в колоду Deck", ans);
    }

    /**
     * Тест добавление карты с существующим термином
     */
    @Test
    void testIncorrectTerm() {

        DeckManager deckManager = userDecksData.getUserDecks(existUser);
        deckManager.addDeck("Deck");
        deckManager.getDeck("Deck").addCard("old term", "def");
        String ans = createCardCommandHandler.execute(deckManager, new String[]{"Deck", "old term", "Hello world"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Карта с термином old term существует в колоде""", ans);
    }

    /**
     * Тест добавление карты в несуществующую колоду
     */
    @Test
    void testIncorrectDeck() {

        String ans = createCardCommandHandler.execute(userDecksData.getUserDecks(existUser), new String[]{"Deck", "term", "Hello world"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Колода с именем Deck не существует в менеджере""", ans);
    }
}
