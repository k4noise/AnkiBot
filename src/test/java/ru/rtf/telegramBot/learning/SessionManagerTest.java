package ru.rtf.telegramBot.learning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.rtf.Card;

import java.util.Collection;
import java.util.List;
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
    private Collection<Card> cards = List.of(
      new Card("term", "def"),
      new Card("test", "test")
    );

    /**
     * Создание менеджера сессий обучения для каждого теста
     */
    @BeforeEach
    void createNewSessionManager() {
        sessionManager = new SessionManager();
    }

    /**
     * Проверка начала новой сессии обучения {@link SessionManager#start} при отсутствии активных сессий
     * <p>также проверяется наличие активной сессии обучения {@link SessionManager#hasActive}</p>
     */
    @Test
    @DisplayName("Корректное начало новой сессии")
    void testStartAndCheckNewSessionActivity() {
        sessionManager.start(chatId, LearningMode.MATCH, cards);
        LearningSession session = sessionManager.get(chatId);

        Assertions.assertEquals(LearningMode.MATCH, session.getMode(), "");
        Assertions.assertTrue(sessionManager.hasActive(chatId), "Должна быть создана активная сессия");
        //TODO проверка сообщения старта
    }

    /**
     * Проверка начала новой сессии обучения {@link SessionManager#start} при наличии активной сессии
     */
    @Test
    @DisplayName("Некорректное начало сессии при наличии активной")
    void testStartSessionWithActive() {
        sessionManager.start(chatId, LearningMode.MATCH, cards);

        IllegalStateException exception = Assertions.assertThrows(
                IllegalStateException.class,
                () -> sessionManager.start(chatId, LearningMode.MEMORY, cards),
                "Невозможно начать новую сессию при наличии активной"
        );
        Assertions.assertEquals("Имеется активная сессия обучения", exception.getMessage());
    }

    /**
     * Проверка начала новой сессии обучения {@link SessionManager#start} с пустым списком карт
     */
    @Test
    @DisplayName("Некорректное начало сессии при отсутствии карт")
    void testStartSessionWithEmptyCards() {
        sessionManager.start(chatId, LearningMode.MATCH, cards);

        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> sessionManager.start(chatId, LearningMode.MEMORY, List.of()),
                "Невозможно начать новую сессию с пустым списком карт"
        );
        Assertions.assertEquals("Колода не содержит карточек, доступных для изучения", exception.getMessage());
    }

    /**
     * Проверка досрочного окончания существующей активной сессии обучения
     */
    @Test
    @DisplayName("Досрочное окончание активной сессии")
    void testEndExistingSession() {
        sessionManager.start(chatId, LearningMode.MATCH, cards);
        sessionManager.end(chatId);

        Assertions.assertFalse(sessionManager.hasActive(chatId), "Не должно быть активной сессии");
    }

    //TODO проверка планового окончания сессии

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

    //TODO тесты на handle
}