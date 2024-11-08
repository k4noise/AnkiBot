package ru.rtf;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Менеджер управления колодами пользователя
 * <p>Для работы с картами колоды следует получить экземпляр колоды через метод {@link DeckManager#getDeck}</p>
 *
 * @author k4noise
 * @since 21.10.2024
 */
public class DeckManager {
    /**
     * Колоды менеджера, где ключ - имя колоды, значение - сама колода
     * Имя колоды хранится в нижнем регистре для обеспечения регистронезависимости при получении колоды
     */
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
     * @throws IllegalArgumentException Колода с именем name существует в менеджере
     */
    public void addDeck(String name) {
        validateUnique(name);
        decks.put(name.toLowerCase(), new Deck(name));
    }

    /**
     * Получить колоду из менеджера с указанным именем
     *
     * @throws NoSuchElementException Колода с именем name не существует в менеджере
     */
    public Deck getDeck(String name) {
        validateExists(name);
        return decks.get(name.toLowerCase());
    }

    /**
     * Изменить имя колоды
     * <p>Имя изменяется в менеджере колод и внутри самой колоды</p>
     *
     * @param oldName Старое имя колоды
     * @param newName Новое имя колоды
     * @throws NoSuchElementException   Колода с именем name не существует в менеджере
     * @throws IllegalArgumentException Колода с именем name существует в менеджере
     */
    public void updateDeckName(String oldName, String newName) {
        validateExists(oldName);
        validateUnique(newName);

        Deck deck = decks.remove(oldName.toLowerCase());
        decks.put(newName.toLowerCase(), deck.updateName(newName));
    }

    /**
     * Удалить колоду из менеджера по имени
     *
     * @throws NoSuchElementException Колода с именем name не существует в менеджере
     */
    public void removeDeck(String name) {
        validateExists(name);
        decks.remove(name.toLowerCase());
    }

    /**
     * Получить все колоды из менеджера
     */
    public Collection<Deck> getDecks() {
        return decks.values();
    }

    /**
     * Проверяет менеджер на уникальность колоды по имени
     *
     * @throws IllegalArgumentException Колода с именем name существует в менеджере
     */
    private void validateUnique(String name) {
        if (decks.containsKey(name.toLowerCase()))
            throw new IllegalArgumentException("Колода с именем " + name + " существует в менеджере");
    }

    /**
     * Проверяет менеджер на существование колоды по имени
     *
     * @throws NoSuchElementException Колода с именем name не существует в менеджере
     */
    private void validateExists(String name) {
        if (!decks.containsKey(name.toLowerCase()))
            throw new NoSuchElementException("Колода с именем " + name + " не существует в менеджере");
    }
}
