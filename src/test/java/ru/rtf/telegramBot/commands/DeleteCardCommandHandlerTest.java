package ru.rtf.telegramBot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;

/**
 * Тест обработчика команды удаления карты из колоды
 */
public class DeleteCardCommandHandlerTest {
    /**
     * Обработчик команды для удаления карты из колоды
     */
    private DeleteCardCommandHandler deleteCardCommandHandler;
    /**
     * Менеджер колод пользователя
     */
    private DeckManager deckManager;
    /**
     * Идентификатор чата
     */
    private Long chatId = 1L;

    @BeforeEach
    void setUp() {
        deleteCardCommandHandler = new DeleteCardCommandHandler();
        deckManager = new DeckManager();
    }

    /**
     * Корректное удаление
     */
    @Test
    void testCorrectDelCard() {
        deckManager.addDeck("Deck");
        deckManager.getDeck("Deck").addCard("del term", "def");
        String message = deleteCardCommandHandler.handle(deckManager, chatId, new String[]{"Deck", "del term"});

        Assertions.assertEquals(0, deckManager.getDeck("Deck").getCards().size());
        Assertions.assertEquals("Карта с термином \"del term\" была успешно удалена из колоды Deck", message);
    }

    /**
     * Удаление несуществующей карты
     */
    @Test
    void testIncorrectTerm() {
        deckManager.addDeck("Deck");
        String message = deleteCardCommandHandler.handle(deckManager, chatId, new String[]{"Deck", "term"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("""
                Ошибка выполнения команды Подробности:
                Карта с термином term не существует в колоде""", message);
    }

    /**
     * Тест несуществующей колоды
     */
    @Test
    void testIncorrectDeck() {
        String message = deleteCardCommandHandler.handle(deckManager, chatId, new String[]{"Deck2", "term"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("""
                Ошибка выполнения команды Подробности:
                Колода с именем deck2 не существует в менеджере""", message);
    }
}
