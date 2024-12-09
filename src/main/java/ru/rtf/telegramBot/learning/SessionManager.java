package ru.rtf.telegramBot.learning;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Управляет сессиями обучения пользователей
 */
public class SessionManager {
    /**
     * Хранилище активных сессий пользователей
     */
    private final Map<Long, LearningSession> sessions;

    /**
     * Инициализировать хранилище сессий
     */
    public SessionManager() {
        this.sessions = new HashMap<>();
    }

    /**
     * Начать новую сессию обучения для пользователя
     *
     * @param chatId          Идентификатор чата
     * @param learningSession Сессия обучения
     * @throws IllegalStateException  Имеется активная сессия обучения
     * @throws NoSuchElementException Колода не содержит карточек, доступных для изучения
     */
    public void start(Long chatId, LearningSession learningSession) {
        if (sessions.containsKey(chatId)) {
            throw new IllegalStateException("Имеется активная сессия обучения");
        }
        if (!learningSession.hasCardsToLearn()) {
            throw new NoSuchElementException("Колода не содержит карточек, доступных для изучения");
        }
        sessions.put(chatId, learningSession);
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
     * @return Экземпляр сессии или null, если активной сессии нет
     */
    public LearningSession get(Long chatId) {
        return sessions.get(chatId);
    }
}
