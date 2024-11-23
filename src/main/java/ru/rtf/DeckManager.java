package ru.rtf;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Менеджер управления колодами пользователя
 * <p>Для работы с картами колоды следует получить экземпляр колоды через метод {@link DeckManager#getDeck}</p>
 */
public class DeckManager {
    /**
     * Колоды менеджера
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
        String lowerCaseName = name.toLowerCase();
        validateUnique(lowerCaseName);
        decks.put(lowerCaseName, new Deck(name));
    }

    /**
     * Получить колоду из менеджера с указанным именем
     *
     * @throws NoSuchElementException Колода с именем name не существует в менеджере
     */
    public Deck getDeck(String name) {
        String lowerCaseName = name.toLowerCase();
        validateExists(lowerCaseName);
        return decks.get(lowerCaseName);
    }

    /**
     * Изменить имя колоды
     * <p>Колода перекладывается заново в хранилище колод для изменения ключа доступа к ней</p>
     *
     * @param oldName Старое имя колоды
     * @param newName Новое имя колоды
     * @throws NoSuchElementException   Колода с именем name не существует в менеджере
     * @throws IllegalArgumentException Колода с именем name существует в менеджере
     */
    public void updateDeckName(String oldName, String newName) {
        String lowerCaseOldName = oldName.toLowerCase();
        String lowerCaseNewName = newName.toLowerCase();
        validateExists(lowerCaseOldName);
        validateUnique(lowerCaseNewName);

        Deck deck = decks.remove(lowerCaseOldName);
        deck.changeName(newName);
        decks.put(lowerCaseNewName, deck);
    }

    /**
     * Удалить колоду из менеджера по имени
     *
     * @throws NoSuchElementException Колода с именем name не существует в менеджере
     */
    public void removeDeck(String name) {
        String lowerCaseName = name.toLowerCase();
        validateExists(lowerCaseName);
        decks.remove(lowerCaseName);
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
        if (decks.containsKey(name))
            throw new IllegalArgumentException("Колода с именем " + name + " существует в менеджере");
    }

    /**
     * Проверяет менеджер на существование колоды по имени
     *
     * @throws NoSuchElementException Колода с именем name не существует в менеджере
     */
    private void validateExists(String name) {
        if (!decks.containsKey(name))
            throw new NoSuchElementException("Колода с именем " + name + " не существует в менеджере");
    }
}