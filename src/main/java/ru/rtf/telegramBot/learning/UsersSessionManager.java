package ru.rtf.telegramBot.learning;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * Управляет сессиями обучения пользователей
 *
 * @author k4noise
 * @since 10.11.2024
 */
public class UsersSessionManager {
    /**
     * Хранилище активных сессий пользователей
     */
    private final Map<Long, LearningSession> sessions;
    /**
     * Хранилище ссылок на классы режимов обучения
     */
    private final Map<LearningMode, Supplier<LearningSession>> learningModes = Map.of(
            // оставлено в качестве шаблона мапы, хардкодить тут
            // LearningMode.TYPING, LearningSession::new,
    );

    /**
     * Инициализация хранилища сессий
     */
    public UsersSessionManager() {
        this.sessions = new HashMap<>();
    }

    /**
     * Начинает новую сессию обучения для пользователя
     *
     * @param chatId Идентификатор чата
     * @param mode   Режим обучения
     * @throws IllegalStateException Имеется активная сессия обучения
     */
    public void start(Long chatId, LearningMode mode) {
        if (sessions.containsKey(chatId)) {
            throw new IllegalStateException("Имеется активная сессия обучения");
        }
        sessions.put(chatId, learningModes.get(mode).get());
    }

    /**
     * Завершить активную сессию обучения для пользователя
     *
     * @param chatId Идентификатор чата
     * @throws NoSuchElementException Нет активной сессии обучения
     */
    public void end(Long chatId) {
        if (!sessions.containsKey(chatId)) {
            throw new NoSuchElementException("Нет активной сессии обучения");
        }
        sessions.remove(chatId);
    }

    /**
     * Проверить наличие активной сессии обучения для пользователя
     *
     * @param chatId Идентификатор чата
     */
    public boolean hasActive(Long chatId) {
        return sessions.containsKey(chatId);
    }

    /**
     * Получить активную сессию обучения для пользователя
     *
     * @param chatId Идентификатор чата
     */
    public LearningSession get(Long chatId) {
        return sessions.get(chatId);
    }
}
