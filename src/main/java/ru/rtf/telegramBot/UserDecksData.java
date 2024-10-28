package ru.rtf.telegramBot;

import ru.rtf.DeckManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для хранения соответствий между пользователями и их колодами
 */
public class UserDecksData {
    /**
     * хранит колоды по идентификатору чата
     */
    private Map<Long, DeckManager> userDesks;

    public UserDecksData() {
        userDesks = new HashMap<>();
    }

    /**
     * Проверяет добавлен ли пользователь в список соответствий
     *
     * @param chatId идентификатор чата
     */
    public boolean containsUser(Long chatId) {
        return userDesks.containsKey(chatId);
    }

    /**
     * Добавить нового пользователя в список соответствий
     *
     * @param chatId идентификатор чата
     */
    public void addUser(Long chatId) {
        userDesks.put(chatId, new DeckManager());
    }

    /**
     * возвращает колоды конкретного пользователя
     *
     * @param chatId идентификатор чата
     * @return колоды
     */
    public DeckManager getUserDecks(Long chatId) {
        return userDesks.get(chatId);
    }
}
