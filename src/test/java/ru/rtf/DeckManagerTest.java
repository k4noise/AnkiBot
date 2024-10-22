package ru.rtf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

/**
 * Тесты на класс менеджера управления колодами {@link DeckManager}
 *
 * @author k4noise
 * @since 21.10.2024
 */
class DeckManagerTest {
    /**
     * Менеджер управления колодами
     */
    private DeckManager deckManager;

    /**
     * Создание нового экземпляра тестируемого класса для каждого теста
     */
    @BeforeEach
    void setUp() {
        deckManager = new DeckManager();
    }

    /**
     * Тестирование метода {@link DeckManager#addDeck} на уникальных данных
     */
    @Test
    @DisplayName("Добавление новой колоды")
    void testAddDeck() {
        String deckName = "Deck";
        try {
            deckManager.addDeck(deckName);
        } catch (IllegalArgumentException exception) {
            Assertions.fail("Добавление новой колоды с уникальным именем не должно приводить к исключению: " + exception.getMessage());
        }
        Set<Deck> decks = deckManager.getDecks();

        Assertions.assertFalse(decks.isEmpty());
        Assertions.assertEquals(1, decks.size(), "Должна быть сохранена только одна колода в менеджере");
    }

    /**
     * Тестирование метода {@link DeckManager#addDeck} на повторяющихся данных
     */
    @Test
    @DisplayName("Добавление колоды с существующим именем")
    void testAddDeckWithDuplicateName() {
        String deckName = "Deck";
        try {
            deckManager.addDeck(deckName);
        } catch (IllegalArgumentException exception) {
            Assertions.fail("Добавление новой колоды с уникальным именем не должно приводить к исключению: " + exception.getMessage());
        }

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> deckManager.addDeck(deckName),
                "Колода с существующим именем не может быть добавлена"
        );
    }

    /**
     * Тестирование метода {@link DeckManager#removeDeck} на корректных данных
     */
    @Test
    @DisplayName("Удаление созданной колоды")
    void testRemoveDeck() {
        String deckName = "Deck";
        try {
            deckManager.addDeck(deckName);
            deckManager.removeDeck(deckName);
        } catch (IllegalArgumentException exception) {
            Assertions.fail("Ошибка при добавлении или удалении колоды: " + exception.getMessage());
        }
        Set<Deck> decks = deckManager.getDecks();

        Assertions.assertTrue(decks.isEmpty(), "Не должно быть сохраненных колод в менеджере");
    }

    /**
     * Тестирование метода {@link DeckManager#removeDeck} при пустом менеджере колод
     */
    @Test
    @DisplayName("Удаление колоды из пустого менеджера колод")
    void testRemoveNonExistentDeckInEmptyManager() {
        String deckName = "Deck";
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> deckManager.removeDeck(deckName),
                "Невозможно удалить колоду, если менеджер колод пуст"
        );
    }

    /**
     * Тестирование метода {@link DeckManager#removeDeck} на несуществующих данных
     */
    @Test
    @DisplayName("Удаление несуществующей колоды")
    void testRemoveNonExistentDeck() {
        String deckName = "Deck";
        String wrongDeckName = deckName + '1';
        try {
            deckManager.addDeck(deckName);
        } catch (IllegalArgumentException exception) {
            Assertions.fail("Добавление новой колоды с уникальным именем не должно приводить к исключению: " + exception.getMessage());
        }

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> deckManager.removeDeck(wrongDeckName),
                "Невозможно удалить не сохраненную в менеджере колод колоду"
        );
    }

    /**
     * Тестирование метода {@link DeckManager#getDecks} с ненулевым количество колод в менеджере
     */
    @Test
    @DisplayName("Получение созданных колод")
    void testGetDecks() {
        List<String> deckNames = List.of("Deck1", "Deck2");
        deckNames.forEach(deckName -> Assertions.assertDoesNotThrow(
                        () -> deckManager.addDeck(deckName),
                        "Добавление новых колод не должно приводить к исключению"
                )
        );
        Set<Deck> decks = deckManager.getDecks();

        Assertions.assertEquals(deckNames.size(), decks.size(), "");
        //TODO проверка содержимого колод
    }
}