package ru.rtf.telegramBot;

import ru.rtf.DeckManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Хранилище колод пользователей
 */
public class UserDecksData {
    /**
     * Хранит колоды по идентификатору чата
     */
    private final Map<Long, DeckManager> userDecks;

    /**
     * Инициализировать хранилище колод
     */
    public UserDecksData() {
        userDecks = new HashMap<>();
    }

    /**
     * Проверяет добавлен ли пользователь в список соответствий
     *
     * @param chatId идентификатор чата
     */
    public boolean containsUser(Long chatId) {
        return userDecks.containsKey(chatId);
    }

    /**
     * Добавляет нового пользователя в список соответствий
     *
     * @param chatId идентификатор чата
     */
    public void addUser(Long chatId) {
        userDecks.put(chatId, new DeckManager());
    }

    /**
     * Возвращает колоды конкретного пользователя
     *
     * @param chatId идентификатор чата
     * @return колоды
     */
    public DeckManager getUserDecks(Long chatId) {
        return userDecks.get(chatId);
    }
}
