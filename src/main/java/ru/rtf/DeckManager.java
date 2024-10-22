package ru.rtf;

import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Менеджер управления колодами пользователя
 *
 * @author k4noise
 * @since 21.10.2024
 */
public class DeckManager {
    private final Map<String, Deck> decks;

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
    public void addDeck(String name) {
    }

    /**
     * Получить колоду из менеджера с указанным именем
     *
     * @return Колода с указанным именем
     * @throws NoSuchElementException Колоды с таким именем не существует
     */
    public Deck getDeck(String name) {
    }

    /**
     * Изменить имя колоды
     * Имя изменяется в менеджере колод и внутри самой колоды
     *
     * @param oldName Старое имя колоды
     * @param newName Новое имя колоды
     * @throws NoSuchElementException Колоды с таким именем не существует
     * @throws IllegalArgumentException Колода с таким именем уже существует
     */
    public void updateDeckName(String oldName, String newName) {
    }

    /**
     * Удалить колоду из менеджера
     *
     * @param name Имя колоды для удаления
     * @throws NoSuchElementException Колоды с таким именем не существует
     */
    public void removeDeck(String name) {
    }

    /**
     * Получить все колоды из менеджера
     *
     * @return Все сохраненные колоды
     */
    public Collection<Deck> getDecks() {
    }
}
