package ru.rtf.telegramBot.learning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;
import ru.rtf.Deck;
import ru.rtf.telegramBot.learning.mode.MatchLearning;

import java.util.NoSuchElementException;

/**
 * Тестирование менеджера сессий обучения
 */
class SessionManagerTest {
    /**
     * Идентификатор чата
     */
    private final Long chatId = 1L;
    /**
     * Менеджер сессий обучения пользователя
     */
    private SessionManager sessionManager;
    /**
     * Колода для обучения
     */
    private Deck deck;

    /**
     * Создание менеджера сессий обучения для каждого теста
     */
    @BeforeEach
    void createNewSessionManager() {
        sessionManager = new SessionManager();
        deck = new Deck("Deck");
        deck.addCard(new Card("term", "def"));
    }

    /**
     * Проверка начала новой сессии обучения {@link SessionManager#start} при отсутствии активных сессий
     * <p>также проверяется наличие активной сессии обучения {@link SessionManager#hasActive}</p>
     */
    @Test
    @DisplayName("Корректное начало новой сессии")
    void testStartAndCheckNewSessionActivity() {
        String startMessage = sessionManager.start(chatId, new MatchLearning(deck));

        Assertions.assertTrue(sessionManager.hasActive(chatId), "Должна быть создана активная сессия");
        Assertions.assertEquals("""
                Вы начали обучение в режиме соответствия
                Показывается термин и определение, ваша задача - определить, соответствует ли термин определению
                Чтобы досрочно завершить сеанс, воспользуйтесь командой /end_check
                
                Ваш первый вопрос: Утверждение:
                term - def
                1 - верно, 0 - неверно""", startMessage);
    }

    /**
     * Проверка начала новой сессии обучения {@link SessionManager#start} при наличии активной сессии
     */
    @Test
    @DisplayName("Некорректное начало сессии при наличии активной")
    void testStartSessionWithActive() {
        sessionManager.start(chatId, new MatchLearning(deck));

        IllegalStateException exception = Assertions.assertThrows(
                IllegalStateException.class,
                () -> sessionManager.start(chatId, new MatchLearning(deck)),
                "Невозможно начать новую сессию при наличии активной"
        );
        Assertions.assertEquals("Имеется активная сессия обучения", exception.getMessage());
    }

    /**
     * Проверка начала новой сессии обучения {@link SessionManager#start} с пустой колодой
     */
    @Test
    @DisplayName("Некорректное начало сессии с пустой колодой")
    void testStartSessionWithEmptyCards() {
        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> sessionManager.start(chatId, new MatchLearning(new Deck("123"))),
                "Невозможно начать новую сессию с пустым списком карт"
        );
        Assertions.assertEquals("Колода не содержит карточек, доступных для изучения", exception.getMessage());
    }

    /**
     * Проверка досрочного окончания существующей активной сессии обучения
     */
    @Test
    @DisplayName("Досрочное окончание активной сессии")
    void testEarlyEndExistingSession() {
        sessionManager.start(chatId, new MatchLearning(deck));
        String endMessage = sessionManager.end(chatId);

        Assertions.assertFalse(sessionManager.hasActive(chatId), "Не должно быть активной сессии");
        Assertions.assertEquals("""
                Вы досрочно завершили сессию
                Вы помните 0% терминов из показанных""", endMessage);
    }

    /**
     * Проверка планового окончания существующей активной сессии обучения
     */
    @Test
    @DisplayName("Плановое окончание активной сессии")
    void testHandleEndExistingSession() {
        sessionManager.start(chatId, new MatchLearning(deck));
        String endMessage = sessionManager.handle(chatId, "1");

        Assertions.assertFalse(sessionManager.hasActive(chatId), "Не должно быть активной сессии");
        Assertions.assertEquals("""
                Верно! Правильный ответ:
                "term" = def
                Вы прошли все карточки в колоде!
                Вы помните 100% терминов из показанных""", endMessage);
    }

    /**
     * Проверка окончания несуществующей активной сессии обучения
     */
    @Test
    @DisplayName("Окончание несуществующей активной сессии")
    void testEndNonExistingSession() {
        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> sessionManager.end(chatId),
                "Невозможно окончить сессию, которой нет"
        );
        Assertions.assertEquals("Нет активной сессии обучения", exception.getMessage());
    }
}