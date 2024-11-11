package ru.rtf.telegramBot.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rtf.DeckManager;
import org.junit.jupiter.api.Assertions;

/**
 * Тест обработчика команды редактирования карты в колоде
 */
public class ListCardCommandHandlerTest {
    /**
     * Обработчик команды для отображения карты
     */
    private ListCardCommandHandler listCardCommandHandler;
    /**
     * Менеджер колод пользователя
     */
    private DeckManager deckManager;

    @BeforeEach
    void setUp() {
        listCardCommandHandler = new ListCardCommandHandler();
        deckManager = new DeckManager();
        deckManager.addDeck("Deck");
    }

    /**
     * Корректный вывод
     */
    @Test
    void testCorrectPrintCard() {
        deckManager.getDeck("Deck").addCard("term", "какое-то описание");

        String message = listCardCommandHandler.handle(deckManager, new String[]{"Deck", "term"});
        Assertions.assertEquals("\"term\" = какое-то описание", message);
    }

    /**
     * Несуществующий термин
     */
    @Test
    void testIncorrectTerm() {
        String message = listCardCommandHandler.handle(deckManager, new String[]{"Deck", "term"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Карта с термином term не существует в колоде""", message);
    }

    /**
     * Тест несуществующей колоды
     */
    @Test
    void testIncorrectDeck() {
        String message = listCardCommandHandler.handle(deckManager, new String[]{"Deck2", "term"});

        // Проверяем отправку сообщения об ошибке
        Assertions.assertEquals("""
                Ошибка выполнения команды. Подробности:
                Колода с именем Deck2 не существует в менеджере""", message);
    }
}
