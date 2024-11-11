package ru.rtf.telegramBot.learning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

/**
 * Тестирование менеджера сессий обучения
 *
 * @author k4noise
 * @since 10.11.2024
 */
class UsersSessionManagerTest {
    /**
     * Идентификатор чата
     */
    private final Long chatId = 1L;
    /**
     * Менеджер сессий обучения пользователя
     */
    private UsersSessionManager sessionManager;

    /**
     * Создание менеджера сессий обучения для каждого теста
     */
    @BeforeEach
    void createNewSessionManager() {
        sessionManager = new UsersSessionManager();
    }

    /**
     * Проверка начала новой сессии обучения {@link UsersSessionManager#start} при отсутствии активных сессий
     * <p>также проверяется наличие активной сессии обучения {@link UsersSessionManager#hasActive}</p>
     */
    @Test
    @DisplayName("Корректное начало новой сессии")
    void testStartAndCheckNewSessionActivity() {
        sessionManager.start(chatId, LearningMode.MATCH);
        LearningSession session = sessionManager.get(chatId);

        Assertions.assertEquals(LearningMode.MATCH, session.getMode(), "");
        Assertions.assertTrue(sessionManager.hasActive(chatId), "Должна быть создана активная сессия");
    }

    /**
     * Проверка начала новой сессии обучения {@link UsersSessionManager#start} при наличии активной сессии
     */
    @Test
    @DisplayName("Некорректное начало сессии при наличии активной")
    void testStartSessionWithActive() {
        sessionManager.start(chatId, LearningMode.MATCH);

        IllegalStateException exception = Assertions.assertThrows(
                IllegalStateException.class,
                () -> sessionManager.start(chatId, LearningMode.MEMORY),
                "Невозможно начать новую сессию при наличии активной"
        );
        Assertions.assertEquals("Имеется активная сессия обучения", exception.getMessage());
    }

    /**
     * Проверка окончания существующей активной сессии обучения
     */
    @Test
    @DisplayName("Окончание активной сессии")
    void testEndExistingSession() {
        sessionManager.start(chatId, LearningMode.MATCH);
        sessionManager.end(chatId);

        Assertions.assertFalse(sessionManager.hasActive(chatId), "Не должно быть активной сессии");
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