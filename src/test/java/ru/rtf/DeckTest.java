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

    // TODO: тесты, как будет реализован CardManager
}