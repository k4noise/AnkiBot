package ru.rtf;

import java.util.Set;

/**
 * Менеджер управления колодами пользователя
 *
 * @author k4noise
 * @since 21.10.2024
 */
public class DeckManager {
    private Set<Deck> decks;

    /**
     * Инициализация менеджера управления колодами
     */
    public DeckManager() {
    }

    /**
     * Добавить новую колоду в менеджера
     * Колода может быть создана, если имя колоды не используется другими колодами
     *
     * @param name Имя новой колоды
     * @throws IllegalArgumentException Колода с таким именем уже существует
     */
    public void addDeck(String name) throws IllegalArgumentException {
    }

    /**
     * Удалить колоду из менеджера
     *
     * @param name Имя колоды для удаления
     * @throws IllegalArgumentException Колоды с таким именем не существует
     * @throws IllegalArgumentException Менеджер колод пуст
     */
    public void removeDeck(String name) throws IllegalArgumentException {
    }

    /**
     * Получить все колоды из менеджера
     *
     * @return Все сохраненные колоды
     */
    public Set<Deck> getDecks() {
    }
}
