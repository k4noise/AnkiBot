package ru.rtf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Тесты на класс колоды {@link Deck}
 *
 * @author k4noise
 * @since 22.10.2024
 */
class DeckTest {

    /**
     * Тестирование конструктора {@link Deck}
     */
    @Test
    @DisplayName("Инициализация колоды")
    void testDeckInit() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);

        Assertions.assertEquals(deckName, deck.getName(), "Колода не может быть создана без имени");
    }

    /**
     * Тестирование метода {@link Deck#hashCode} на колодах с одинаковым именем
     */
    @Test
    @DisplayName("Сравнение хешей колод")
    void testHashCode() {
        String deckName = "Deck";
        Deck deck1 = new Deck(deckName);
        Deck deck2 = new Deck(deckName);

        Assertions.assertEquals(
                deck1.hashCode(),
                deck2.hashCode(),
                "Хеши колод с одинаковым именем должны быть равны"
        );
    }

    /**
     * Тестирование метода {@link Deck#equals} на колодах с одинаковым именем
     */
    @Test
    @DisplayName("Сравнение колод")
    void testEquals() {
        String deckName = "Deck";
        Deck deck1 = new Deck(deckName);
        Deck deck2 = new Deck(deckName);

        Assertions.assertEquals(deck1, deck2, "Колоды с одинаковым именем должны быть равны");
    }

    /**
     * Тестирование метода {@link Deck#getName}
     */
    @Test
    @DisplayName("Получение имени колоды")
    void getName() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);

        Assertions.assertEquals(
                deckName,
                deck.getName(),
                "Имя колоды должно соответствовать первоначальному имени"
        );
    }

    /**
     * Тестирование метода {@link Deck#updateName}
     */
    @Test
    @DisplayName("Изменение имени колоды")
    void updateName() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);

        String newDeckName = deckName + '1';
        deck.updateName(newDeckName);


        Assertions.assertNotEquals(
                deckName,
                deck.getName(),
                "Имя колоды должно было измениться"
        );
        Assertions.assertEquals(
                newDeckName,
                deck.getName(),
                "Имя колоды должно соответствовать новому имени"
        );
    }

    // Взаимодействие с картами //

    /**
     * Тестирование метода {@link Deck#addCard} на корректных данных
     */
    @Test
    @DisplayName("Добавление карты в колоду")
    void addCard() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);

        String term = "term", definition = "def";
        deck.addCard(term, definition);

        Collection<Card> cards = deck.getCards();
        Assertions.assertFalse(cards.isEmpty(), "После добавления карты колода не может быть пуста");
        Assertions.assertEquals(1, cards.size(), "Должна быть сохранена только одна карта в колоде");
        // TODO: проверка термина карты
    }

    /**
     * Тестирование метода {@link Deck#addCard} на повторяющихся данных
     */
    @Test
    @DisplayName("Добавление карты в колоду с существующим термином")
    void addCardWithDuplicateTerm() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);

        String term = "term", definition = "def";
        deck.addCard(term, definition);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> deck.addCard(term, definition),
                "Карта с существующим термином не может быть добавлена в колоду"
        );
    }

    /**
     * Тестирование метода {@link Deck#updateTerm(String, String)} на корректных данных
     */
    @Test
    @DisplayName("Изменение термина в колоде")
    void updateTerm() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);

        String term = "term", definition = "def";
        deck.addCard(term, definition);

        String newTerm = term + '1';
        deck.updateTerm(term, newTerm);

        // TODO: проверка карты по термину
    }

    /**
     * Тестирование метода {@link Deck#updateTerm} на несуществующих данных
     */
    @Test
    @DisplayName("Изменение термина несуществующей карты в колоде")
    void updateTermNonExistentCard() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);

        String term = "term";
        String newTerm = term + '1';


        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.updateTerm(term, newTerm),
                "Невозможно изменить термин у несуществующей карты"
        );
    }

    /**
     * Тестирование метода {@link Deck#updateTerm} на дублирующихся данных
     */
    @Test
    @DisplayName("Изменение термина в колоде на повторяющееся")
    void updateTermExistentCardTerm() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);

        String term = "term", definition = "def";
        String newTerm = term + '1';

        deck.addCard(term, definition);
        deck.addCard(newTerm, definition);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> deck.updateTerm(term, newTerm),
                "Невозможно изменить термин, так как он используется в другой карте"
        );
    }

    /**
     * Тестирование метода {@link Deck#updateDefinition} на корректных данных
     */
    @Test
    @DisplayName("Изменение определения в колоде")
    void updateDefinition() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);

        String term = "term", definition = "def";
        deck.addCard(term, definition);

        String newDefinition = term + '1';
        deck.updateDefinition(term, newDefinition);

        // TODO: проверка определений карты
    }

    /**
     * Тестирование метода {@link Deck#updateDefinition} на отсутствующих данных
     */
    @Test
    @DisplayName("Изменение определения для несуществующей карты в колоде")
    void updateDefinitionNonExistentCard() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);

        String term = "term", definition = "def";

        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.updateDefinition(term, definition),
                "Невозможно изменить определение у несуществующей карты"
        );
    }

    /**
     * Тестирование метода {@link Deck#getCard} на корректных данных
     */
    @Test
    @DisplayName("Получение созданной карты")
    void getCard() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);

        String term = "term", definition = "def";
        deck.addCard(term, definition);

        // TODO: проверка данных карты
    }

    /**
     * Тестирование метода {@link Deck#getCard} на отсутствующих данных
     */
    @Test
    @DisplayName("Получение созданной карты")
    void getNonExistentCard() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        String term = "term";

        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.getCard(term),
                "Невозможно получить несуществующую карту"
        );
    }

    /**
     * Тестирование метода {@link Deck#getCards} на корректных данных
     */
    @Test
    @DisplayName("Получение созданных карт")
    void getCards() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);

        List<String> cardTerms = List.of("term1", "term2");
        String definition = "def";
        cardTerms.forEach(cardTerm -> deck.addCard(cardTerm, definition));

        Collection<Card> cards = deck.getCards();

        Assertions.assertEquals(
                cardTerms.size(),
                cards.size(),
                "Количество созданных карт должно совпадать с количеством полученных карт"
        );
    }

    /**
     * Тестирование метода {@link Deck#removeCard} на корректных данных
     */
    @Test
    @DisplayName("Удаление созданной карты")
    void removeCard() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        String term = "term", definition = "def";
        deck.addCard(term, definition);

        deck.removeCard(term);
        Collection<Card> cards = deck.getCards();
        Assertions.assertTrue(cards.isEmpty(), "Не должно быть сохраненных карт в колоде");
    }

    /**
     * Тестирование метода {@link Deck#removeCard} на отсутствующих данных
     */
    @Test
    @DisplayName("Удаление несуществующей карты")
    void removeNonExistentCard() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        String term = "term";

        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.removeCard(term),
                "Невозможно удалить не сохраненную в колоде карту"
        );
    }
}