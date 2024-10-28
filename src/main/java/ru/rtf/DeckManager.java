package ru.rtf;

import java.util.Collection;
import java.util.LinkedHashMap;
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
        decks = new LinkedHashMap<>();
    }

    /**
     * Добавить новую колоду в менеджера
     * Колода может быть создана, если имя колоды не используется другими колодами
     *
     * @param name Имя новой колоды
     * @throws IllegalArgumentException Колода с таким именем уже существует
     */
    public void addDeck(String name) {
        if (decks.containsKey(name)) {
            throw new IllegalArgumentException("Колода с именем " + name + " уже существует, выберите другое имя");
        }
        decks.put(name, new Deck(name));
    }

    /**
     * Получить колоду из менеджера с указанным именем
     *
     * @return Найденная колода
     * @throws NoSuchElementException Колоды с таким именем не существует
     */
    public Deck getDeck(String name) {
        if (decks.containsKey(name)) {
            return decks.get(name);
        }
        throw new NoSuchElementException("Колода с именем" + name + "не существует");
    }

    /**
     * Изменить имя колоды
     * Имя изменяется в менеджере колод и внутри самой колоды
     *
     * @param oldName Старое имя колоды
     * @param newName Новое имя колоды
     * @throws NoSuchElementException   Колоды с таким именем не существует
     * @throws IllegalArgumentException Колода с таким именем уже существует
     */
    public void updateDeckName(String oldName, String newName) {
        if (!decks.containsKey(oldName)) {
            throw new NoSuchElementException("Колода с именем" + oldName + "не существует");
        }
        if (decks.containsKey(newName)) {
            throw new IllegalArgumentException("Колода с именем " + newName + " уже существует, выберите другое имя");
        }

        Deck deck = decks.remove(oldName);
        deck.updateName(newName);
        decks.put(newName, deck);
    }

    /**
     * Удалить колоду из менеджера
     *
     * @param name Имя колоды для удаления
     * @throws NoSuchElementException Колоды с таким именем не существует
     */
    public void removeDeck(String name) {
        if (!decks.containsKey(name)) {
            throw new NoSuchElementException("Колода с именем" + name + "не существует");
        }
        decks.remove(name);
    }

    /**
     * Получить все колоды из менеджера
     *
     * @return Все сохраненные колоды
     */
    public Collection<Deck> getDecks() {
        return decks.values();
    }
}
