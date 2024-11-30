package ru.rtf.telegramBot.commands;

import org.junit.jupiter.api.*;
import ru.rtf.Card;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.learning.SessionManager;
import ru.rtf.telegramBot.learning.mode.MatchLearning;

import java.util.Collection;

/**
 * Тест обработчика команды завершения сессии обучения
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EndCheckCommandHandlerTest {
    /**
     * Обработчик команды для завершения режима обучения
     */
    private EndCheckCommandHandler endCheckCommandHandler;
    /**
     * Менеджер сессий пользователей
     */
    private SessionManager sessionManager;
    /**
     * Менеджер колод пользователя
     */
    private DeckManager deckManager;
    /**
     * Идентификатор чата
     */
    private final Long chatId = 1L;

    /**
     * Инициализировать колоду с картами и хранилища для обработчика команды
     */
    @BeforeAll
    void setUp() {
        deckManager = new DeckManager();
        deckManager.addDeck("Deck");
        deckManager.getDeck("Deck")
                .addCard(new Card("term", "def"));

        sessionManager = new SessionManager();
        endCheckCommandHandler = new EndCheckCommandHandler(sessionManager);
    }

    /**
     * Тест невозможности окончания несуществующей сессии
     */
    @Test
    @DisplayName("Выход вне сессии")
    void testEndNonExistSession() {
        String message = endCheckCommandHandler.handle(deckManager, chatId, null);
        Assertions.assertEquals("""
                Ошибка выполнения команды Подробности:
                Нет активной сессии обучения""", message);
    }

    /**
     * Тест корректного выхода из активной сессии
     */
    @Test
    @DisplayName("Выход из активной сессии")
    void testEndExistSession() {
        Collection<Card> cards = deckManager.getDeck("Deck").getCards();
        sessionManager.start(chatId, new MatchLearning(cards));
        String message = endCheckCommandHandler.handle(deckManager, chatId, null);
        Assertions.assertEquals("Вы досрочно завершили сессию", message);
    }
}