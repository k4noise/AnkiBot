package ru.rtf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
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
        deckManager.addDeck(deckName);

        Collection<Deck> decks = deckManager.getDecks();

        Assertions.assertFalse(decks.isEmpty());
        Assertions.assertEquals(1, decks.size(), "Должна быть сохранена только одна колода в менеджере");
        // TODO: проверка имени колоды
    }

    /**
     * Тестирование метода {@link DeckManager#addDeck} на повторяющихся данных
     */
    @Test
    @DisplayName("Добавление колоды с существующим именем")
    void testAddDeckWithDuplicateName() {
        String deckName = "Deck";
        deckManager.addDeck(deckName);

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
        deckManager.addDeck(deckName);
        deckManager.removeDeck(deckName);
        Collection<Deck> decks = deckManager.getDecks();

        Assertions.assertTrue(decks.isEmpty(), "Не должно быть сохраненных колод в менеджере");
    }


    /**
     * Тестирование метода {@link DeckManager#removeDeck} на несуществующих данных
     */
    @Test
    @DisplayName("Удаление несуществующей колоды")
    void testRemoveNonExistentDeck() {
        String deckName = "Deck";
        String wrongDeckName = deckName + '1';
        deckManager.addDeck(deckName);

        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deckManager.removeDeck(wrongDeckName),
                "Невозможно удалить не сохраненную в менеджере колод колоду"
        );
    }

    /**
     * Тестирование метода {@link DeckManager#getDeck}
     */
    @Test
    @DisplayName("Получение созданной колоды")
    void testGetDeck() {
        String deckName = "Deck";
        deckManager.addDeck(deckName);

        Deck deck = deckManager.getDeck(deckName);

        //TODO проверка имени колоды
    }

    /**
     * Тестирование метода {@link DeckManager#getDeck}
     */
    @Test
    @DisplayName("Получение несуществующей колоды")
    void testGetNonExistentDeck() {
        String deckName = "Deck";
        String wrongDeckName = deckName + '1';
        deckManager.addDeck(deckName);

        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deckManager.getDeck(wrongDeckName),
                "Невозможно получить не сохраненную в менеджере колод колоду"
        );
    }

    /**
     * Тестирование метода {@link DeckManager#getDecks} с ненулевым количество колод в менеджере
     */
    @Test
    @DisplayName("Получение созданных колод")
    void testGetDecks() {
        List<String> deckNames = List.of("Deck1", "Deck2");
        deckNames.forEach(deckName -> deckManager.addDeck(deckName));
        Collection<Deck> decks = deckManager.getDecks();

        Assertions.assertEquals(
                deckNames.size(),
                decks.size(),
                "Количество созданных колод должно совпадать с количеством полученных колод"
        );
        //TODO проверка имен колод
    }

    /**
     * Тестирование метода {@link DeckManager#updateDeckName} на корректных данных
     */
    @Test
    @DisplayName("Изменение имени у созданной колоды")
    void testUpdateNameDeck() {
        String deckName = "Deck";
        deckManager.addDeck(deckName);
        String newDeckName = deckName + '1';
        deckManager.updateDeckName(deckName, newDeckName);

        Deck deck = deckManager.getDeck(newDeckName);

        Assertions.assertThrows(NoSuchElementException.class,
                () -> deckManager.getDeck(deckName),
                "Невозможно получить колоду по старому имени"
        );
        // TODO: проверка имени новой колоды
    }


    /**
     * Тестирование метода {@link DeckManager#updateDeckName} на несуществующих данных
     */
    @Test
    @DisplayName("Изменение имени у несуществующей колоды")
    void testUpdateNonExistentDeckName() {
        String deckName = "Deck";
        String newDeckName = deckName + '1';

        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deckManager.updateDeckName(deckName, newDeckName),
                "Невозможно изменить имя у несуществующей колоды"
        );
    }

    /**
     * Тестирование метода {@link DeckManager#updateDeckName} на повторяющихся данных
     */
    @Test
    @DisplayName("Изменение имени у колоды на повторяющееся")
    void testUpdateExistentDeckName() {
        String deckName = "Deck";
        String newDeckName = deckName + '1';
        deckManager.addDeck(deckName);
        deckManager.addDeck(newDeckName);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> deckManager.updateDeckName(deckName, newDeckName),
                "Невозможно изменить имя, так как оно уже используется другой колодой"
        );
    }
}