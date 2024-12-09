package ru.rtf.telegramBot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;

/**
 * Тест обработчика команды редактирования термина карты
 */
public class EditCardTermCommandHandlerTest {
    /**
     * Обработчик команды для редактирования термина карты
     */
    private EditCardTermCommandHandler editCardTermCommandHandler;
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
        editCardTermCommandHandler = new EditCardTermCommandHandler();
        deckManager = new DeckManager();
        deckManager.addDeck("Deck");
    }

    /**
     * Корректное изменение
     */
    @Test
    void testCorrectEditTerm() {
        deckManager.getDeck("Deck").addCard("term", "def");

        String newTerm = "new term";
        String message = editCardTermCommandHandler.handle(deckManager, chatId, new String[]{"Deck", "term", newTerm});
        Assertions.assertEquals("Термин карты был успешно изменен: \"new term\" = def", message);
    }

    /**
     * Изменение несуществующей карты
     */
    @Test
    void testIncorrectTerm() {
        String message = editCardTermCommandHandler.handle(deckManager, chatId,
                new String[]{"Deck", "term", "new term"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Карта с термином term не существует в колоде""", message);
    }

    /**
     * Тест несуществующая колода
     */
    @Test
    void testIncorrectDeck() {
        String message = editCardTermCommandHandler.handle(deckManager, chatId,
                new String[]{"Deck2", "term", "new term"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Колода с именем deck2 не существует в менеджере""", message);
    }
}
