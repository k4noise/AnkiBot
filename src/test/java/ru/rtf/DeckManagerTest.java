package ru.rtf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Тесты на класс менеджера управления колодами {@link DeckManager}
 *
 * @author Кистанова Марина
 * @since 21.10.2024
 */
class DeckManagerTest {
    /**
     * Менеджер управления колодами
     */
    private DeckManager deckManager;

    /**
     * Создание нового экземпляра менеджера колод и добавление колоды для каждого теста
     */
    @BeforeEach
    void setUp() {
        deckManager = new DeckManager();
        deckManager.addDeck("Deck");
    }

    /**
     * Тестирование метода {@link DeckManager#addDeck} и {@link DeckManager#getDeck}
     */
    @Test
    @DisplayName("Добавление и получение новой колоды")
    void testAddGetDeck() {
        Assertions.assertEquals(
                "Deck",
                deckManager.getDeck("Deck").getName(),
                "Имя колоды должно соответствовать изначальному"
        );
    }

    /**
     * Тестирование метода {@link DeckManager#addDeck} на повторяющихся данных
     */
    @Test
    @DisplayName("Добавление колоды с существующим именем")
    void testAddDeckWithDuplicateName() {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> deckManager.addDeck("Deck"),
                "Колода с существующим именем не может быть добавлена"
        );
        Assertions.assertEquals("Колода с именем Deck существует в менеджере", exception.getMessage());
    }

    /**
     * Тестирование регистронезависимости метода {@link DeckManager#addDeck}
     */
    @Test
    @DisplayName("Проверка регистронезависимости при добавлении колоды с существующим именем")
    void testAddDeckWithDuplicateNameIgnoreCase() {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> deckManager.addDeck("DECK"),
                "Колода с существующим именем не может быть добавлена"
        );
        Assertions.assertEquals("Колода с именем DECK существует в менеджере", exception.getMessage());
    }

    /**
     * Тестирование метода {@link DeckManager#removeDeck} на корректных данных
     */
    @Test
    @DisplayName("Удаление созданной колоды")
    void testRemoveDeck() {
        deckManager.removeDeck("Deck");
        Collection<Deck> decks = deckManager.getDecks();

        Assertions.assertTrue(decks.isEmpty(), "Не должно быть сохраненных колод в менеджере");
    }


    /**
     * Тестирование метода {@link DeckManager#removeDeck} на несуществующих данных
     */
    @Test
    @DisplayName("Удаление несуществующей колоды")
    void testRemoveNonExistentDeck() {
        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deckManager.removeDeck("wrongDeckName"),
                "Невозможно удалить не сохраненную в менеджере колод колоду"
        );
        Assertions.assertEquals("Колода с именем wrongDeckName не существует в менеджере", exception.getMessage());
    }

    /**
     * Тестирование регистронезависимости метода {@link DeckManager#removeDeck}
     */
    @Test
    @DisplayName("Проверка регистронезависимости при удалении созданной колоды")
    void testRemoveDeckIgnoreCase() {
        deckManager.removeDeck("DECK");
        Collection<Deck> decks = deckManager.getDecks();

        Assertions.assertTrue(decks.isEmpty(), "Не должно быть сохраненных колод в менеджере");
    }

    /**
     * Тестирование метода {@link DeckManager#updateDeckName} на корректных данных
     */
    @Test
    @DisplayName("Изменение имени у созданной колоды")
    void testUpdateNameDeck() {
        deckManager.updateDeckName("Deck", "Deck1");
        Deck deck = deckManager.getDeck("Deck1");

        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deckManager.getDeck("wrongDeckName"),
                "Невозможно получить колоду по старому имени"
        );
        Assertions.assertEquals("Колода с именем wrongDeckName не существует в менеджере", exception.getMessage());
        Assertions.assertEquals("Deck1", deck.getName(), "Колода должна иметь новое имя");
    }


    /**
     * Тестирование метода {@link DeckManager#updateDeckName} на несуществующих данных
     */
    @Test
    @DisplayName("Изменение имени у несуществующей колоды")
    void testUpdateNonExistentDeckName() {
        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deckManager.updateDeckName("Deck1", "newDeckName"),
                "Невозможно изменить имя у несуществующей колоды"
        );
        Assertions.assertEquals("Колода с именем Deck1 не существует в менеджере", exception.getMessage());
    }

    /**
     * Тестирование метода {@link DeckManager#updateDeckName} на повторяющихся данных
     */
    @Test
    @DisplayName("Изменение имени у колоды на повторяющееся")
    void testUpdateExistentDeckName() {
        deckManager.addDeck("newDeckName");

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> deckManager.updateDeckName("Deck", "newDeckName"),
                "Невозможно изменить имя, так как оно уже используется другой колодой"
        );
        Assertions.assertEquals("Колода с именем newDeckName существует в менеджере", exception.getMessage());
    }

    /**
     * Тестирование регистронезависимости метода {@link DeckManager#updateDeckName}
     */
    @Test
    @DisplayName("Проверка регистронезависимости при изменении имени у созданной колоды")
    void testUpdateNameDeckIgnoreCase() {
        deckManager.updateDeckName("DECK", "Deck1");
        Deck deck = deckManager.getDeck("Deck1");

        Assertions.assertEquals("Deck1", deck.getName(), "Колода должна иметь новое имя");
    }
}