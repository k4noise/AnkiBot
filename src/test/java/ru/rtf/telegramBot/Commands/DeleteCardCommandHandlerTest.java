package ru.rtf.telegramBot.Commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.UserDecksData;

/**
 * Тест обработчика команды удаления карты из колоды
 */
public class DeleteCardCommandHandlerTest {
    /**
     * id пользователя, для которого было инициализировано хранилище колод
     */
    private final Long existUser = 987654321L;
    /**
     * Хранилище колод пользователей
     */
    private UserDecksData userDecksData;
    /**
     * Обработчик команды для удаления карты из колоды
     */
    private DeleteCardCommandHandler deleteCardCommandHandler;

    @BeforeEach
    void setUp() {
        userDecksData = new UserDecksData();
        userDecksData.addUser(existUser);
        deleteCardCommandHandler = new DeleteCardCommandHandler();
    }

    /**
     * Корректное удаление
     */
    @Test
    void testCorrectDelCard() {
        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");
        decks.getDeck("Deck").addCard("del term", "def");
        String ans = deleteCardCommandHandler.execute(decks, new String[]{"Deck", "del term"});

        Assertions.assertEquals(0, decks.getDeck("Deck").getCards().size());
        Assertions.assertEquals("Карта с термином \"del term\" была успешно удалена из колоды Deck", ans);
    }

    /**
     * Удаление несуществующей карты
     */
    @Test
    void testIncorrectTerm() {

        DeckManager decks = userDecksData.getUserDecks(existUser);
        decks.addDeck("Deck");
        String ans = deleteCardCommandHandler.execute(decks, new String[]{"Deck", "term"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Карта с термином term не существует в колоде""", ans);
    }

    /**
     * Тест несуществующая колода
     */
    @Test
    void testIncorrectDeck() {

        String ans = deleteCardCommandHandler.execute(userDecksData.getUserDecks(existUser), new String[]{"Deck", "term"});
        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Колода с именем Deck не существует в менеджере""", ans);
    }
}
