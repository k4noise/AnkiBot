package ru.rtf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
     * Экземпляр колоды
     */
    private Deck deck;

    @BeforeEach
    void createDeck() {
        deck = new Deck("Deck");
    }

    /**
     * Тестирование конструктора {@link Deck} на корректных данных
     */
    @Test
    @DisplayName("Корректная инициализация колоды")
    void testDeckCorrectInit() {
        Assertions.assertEquals("Deck", deck.getName(), "Колода должна быть создана с указанным именем");
    }

    /**
     * Тестирование конструктора {@link Deck} на некорректных данных
     */
    @Test
    @DisplayName("Некорректная инициализация колоды")
    void testDeckIncorrectInit() {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Deck(""),
                "Создание колоды с пустым именем невозможно"
        );
        Assertions.assertEquals(
                "Имя колоды не может быть пустым",
                exception.getMessage(),
                "Невозможно создать колоду с пустым именем"
        );
    }

    /**
     * Тестирование метода {@link Deck#updateName}
     */
    @Test
    @DisplayName("Изменение имени колоды")
    void testChangeName() {
        deck.addCard("term", "definition");
        Deck deckWithNewName = deck.updateName("Deck1");

        Assertions.assertEquals(
                "Deck",
                deck.getName(),
                "Имя у первоначального экземпляра колоды не должно измениться"
        );
        Assertions.assertNotEquals(deck, deckWithNewName, "Колоды не могут быть равными, так как термин разный");
        Assertions.assertEquals(deck.getCardsCount(),
                deckWithNewName.getCardsCount(),
                "Все карты должны сохраняться"
        );
    }

    /**
     * Тестирование метода {@link Deck#addCard} и {@link Deck#getCard}
     */
    @Test
    @DisplayName("Добавление и получение карты из колоды")
    void testAddGetCard() {
        deck.addCard("term", "definition");

        Card card = deck.getCard("term");
        Assertions.assertEquals("term", card.getTerm(), "Термин должен соответствовать указанному при создании");
        Assertions.assertEquals("definition",
                card.getDefinition(),
                "Определение должен соответствовать указанному при создании"
        );
    }

    /**
     * Тестирование метода {@link Deck#addCard} с дублирующимися данными
     */
    @Test
    @DisplayName("Повторное добавление карты в колоду")
    void testAddRepeatCard() {
        deck.addCard("term", "definition");
        int deckCardsCount = deck.getCardsCount();

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> deck.addCard("term", "definition"),
                "Добавление карты с существующим термином невозможно"
        );
        Assertions.assertEquals("Карта с термином term существует в колоде", exception.getMessage());
        Assertions.assertEquals(deckCardsCount, deck.getCardsCount(), "Количество карт не должно измениться");
    }

    /**
     * Тестирование регистронезависимости метода {@link Deck#addCard}
     */
    @Test
    @DisplayName("Проверка регистронезависимости при добавлении карты в колоду")
    void testCorrectAddCardIgnoreCase() {
        deck.addCard("term", "definition");

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> deck.addCard("TERM", "definition"),
                "Добавление карты с существующим термином невозможно"
        );
        Assertions.assertEquals("Карта с термином TERM существует в колоде", exception.getMessage());
    }

    /**
     * Тестирование перегрузки метода {@link Deck#addCard} с корректными данными
     */
    @Test
    @DisplayName("Добавление экземпляра карты в колоду")
    void testAddCardInstance() {
        Deck deck = new Deck("Deck");
        Card card = new Card("term", "definition");
        deck.addCard(card);
        Assertions.assertEquals(card, deck.getCard("term"), "Карта не должна измениться при добавлении");
    }

    /**
     * Тестирование регистронезависимости перегрузки метода {@link Deck#addCard}
     */
    @Test
    @DisplayName("Проверка регистронезависимости при добавлении экземпляра карты в колоду")
    void testAddCardInstanceIgnoreCase() {
        Card card = new Card("TERM", "definition");
        deck.addCard(card);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> deck.addCard(card),
                "Повторное добавление карты запрещено"
        );
        Assertions.assertEquals("Карта с термином TERM существует в колоде", exception.getMessage());
    }

    /**
     * Тестирование метода {@link Deck#updateCardTerm} на корректных данных
     */
    @Test
    @DisplayName("Изменение термина у карты")
    void testUpdateCardTerm() {
        Card card = new Card("term", "definition");
        deck.addCard(card);

        deck.updateCardTerm("term", "newTerm");

        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.getCard("term"),
                "Старый экземпляр карты не должен существовать"
        );
        Assertions.assertEquals("Карта с термином term не существует в колоде", exception.getMessage());
    }

    /**
     * Тестирование метода {@link Deck#updateCardTerm} на отсутствующих данных
     */
    @Test
    @DisplayName("Изменение термина у отсутствующей карты")
    void testUpdateNonExistentCardTerm() {
        Deck deck = new Deck("Deck");

        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.updateCardTerm("term", "newTerm"),
                "Невозможно изменить термин у отсутствующей карты"
        );
        Assertions.assertEquals("Карта с термином term не существует в колоде", exception.getMessage());
    }

    /**
     * Тестирование метода {@link Deck#updateCardTerm} на дублирующихся данных
     */
    @Test
    @DisplayName("Изменение термина у карты на повторный")
    void testUpdateCardRepeatTerm() {
        deck.addCard("term", "definition");
        deck.addCard("newTerm", "definition");

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> deck.updateCardTerm("term", "newTerm"),
                "Невозможно изменить термин на повторный"
        );
        Assertions.assertEquals("Карта с термином newTerm существует в колоде", exception.getMessage());
    }

    /**
     * Тестирование регистронезависимости метода {@link Deck#updateCardTerm}
     */
    @Test
    @DisplayName("Проверка регистронезависимости при изменении термина у карты")
    void testUpdateCardTermIgnoreCase() {
        Card card = new Card("term", "definition");
        deck.addCard(card);
        deck.updateCardTerm("TERM", "newTerm");

        Assertions.assertEquals("definition", deck.getCard("newTerm").getDefinition(), "Определение должно сохраниться");
    }

    /**
     * Тестирование метода {@link Deck#updateCardDefinition} на корректных данных
     */
    @Test
    @DisplayName("Изменение определения у карты")
    void testUpdateCardDefinition() {
        deck.addCard("term", "definition");

        deck.updateCardDefinition("term", "newDefinition");
        Card updatedCard = deck.getCard("term");

        Assertions.assertEquals("newDefinition", updatedCard.getDefinition(), "Определение должно было измениться");
    }

    /**
     * Тестирование метода {@link Deck#updateCardDefinition} на отсутствующих данных
     */
    @Test
    @DisplayName("Изменение определения у отсутствующей карты")
    void testUpdateCardNonExistentDefinition() {
        Deck deck = new Deck("Deck");

        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.updateCardDefinition("term", "newDefinition"),
                "Невозможно изменить определение у отсутствующей карты"
        );
        Assertions.assertEquals("Карта с термином term не существует в колоде", exception.getMessage());
    }

    /**
     * Тестирование регистронезависимости метода {@link Deck#updateCardDefinition}
     */
    @Test
    @DisplayName("Проверка регистронезависимости при изменении определения у карты")
    void testUpdateCardDefinitionIgnoreCase() {
        Card card = new Card("term", "definition");
        deck.addCard(card);
        deck.updateCardDefinition("TERM", "newDefinition");

        Assertions.assertEquals("newDefinition", deck.getCard("term").getDefinition(), "Определение должно было измениться");
    }

    /**
     * Тестирование метода {@link Deck#getCard} на отсутствующих данных
     */
    @Test
    @DisplayName("Получение отсутствующей карты")
    void testGetNonExistentCard() {
        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.getCard("term"),
                "Невозможно получить данные отсутствующей карты"
        );
        Assertions.assertEquals("Карта с термином term не существует в колоде", exception.getMessage());
    }

    /**
     * Тестирование регистронезависимости метода {@link Deck#getCard}
     */
    @Test
    @DisplayName("Проверка регистронезависимости при получении карты")
    void testGetCardIgnoreCase() {
        Card card = new Card("term", "definition");
        deck.addCard(card);

        Card actualCard = deck.getCard("TERM");
        Assertions.assertEquals(card, actualCard, "Карты должны быть одинаковыми");
    }

    /**
     * Тестирование метода {@link Deck#removeCard} на корректных данных
     */
    @Test
    @DisplayName("Удаление карты")
    void testRemoveCard() {
        String term = "term", definition = "definition";
        deck.addCard(term, definition);
        deck.removeCard(term);

        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.getCard(term),
                "Удаленная карта не должна существовать в колоде после ее удаления"
        );
        Assertions.assertEquals("Карта с термином term не существует в колоде", exception.getMessage());
    }

    /**
     * Тестирование метода {@link Deck#removeCard} на отсутствующих данных
     */
    @Test
    @DisplayName("Удаление отсутствующей карты")
    void testRemoveNonExistentCard() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        String term = "term";


        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.removeCard(term),
                "Невозможно удалить отсутствующую карту"
        );
        Assertions.assertEquals("Карта с термином term не существует в колоде", exception.getMessage());
    }

    /**
     * Тестирование регистронезависимости метода {@link Deck#removeCard}
     */
    @Test
    @DisplayName("Проверка регистронезависимости при удалении карты")
    void testRemoveCardIgnoreCase() {
        deck.addCard("term", "definition");

        deck.removeCard("TERM");
        NoSuchElementException exception = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.getCard("term"),
                "Удаленная карта не должна существовать в колоде после ее удаления"
        );
        Assertions.assertEquals("Карта с термином term не существует в колоде", exception.getMessage());
    }
}