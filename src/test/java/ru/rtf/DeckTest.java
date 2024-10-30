package ru.rtf;

import org.junit.jupiter.api.Assertions;
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
     * Тестирование конструктора {@link Deck} на корректных данных
     */
    @Test
    @DisplayName("Корректная инициализация колоды")
    void testDeckCorrectInit() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);

        Assertions.assertEquals(deckName, deck.getName(), "Колода не может быть создана без имени");
    }

    /**
     * Тестирование конструктора {@link Deck} на некорректных данных
     */
    @Test
    @DisplayName("Некорректная инициализация колоды")
    void testDeckIncorrectInit() {
        String deckName = "";

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Deck(deckName),
                "Создание колоды с пустым именем невозможно"
        );
    }

    /**
     * Тестирование метода {@link Deck#getName}
     */
    @Test
    @DisplayName("Получение имени колоды")
    void testGetName() {
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
    void testChangeName() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        deck.addCard("term", "definition");

        String newDeckName = deckName + '1';
        Deck deckWithNewName = deck.updateName(newDeckName);

        Assertions.assertEquals(
                deckName,
                deck.getName(),
                "Имя у первоначального экземпляра колоды не должно измениться"
        );
        Assertions.assertNotEquals(deck, deckWithNewName, "Колоды не могут быть равные, так как термин разный");
        Assertions.assertEquals(deck.getCardsDescription(),
                deckWithNewName.getCardsDescription(),
                "Все карты должны сохраняться"
        );
    }

    /**
     * Тестирование метода {@link Deck#getCardsCount()}
     */
    @Test
    @DisplayName("Получение количества карт в колоде")
    void testCardCount() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);

        Assertions.assertEquals(0, deck.getCardsCount(), "Колода должна быть пустой");
        deck.addCard("term", "definition");
        Assertions.assertEquals(1, deck.getCardsCount(), "В колоде должна быть только одна карта");
    }

    /**
     * Тестирование метода {@link Deck#addCard} с корректными данными
     */
    @Test
    @DisplayName("Добавление карты в колоду")
    void testCorrectAddCard() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        int deckInitCardsCount = deck.getCardsCount();
        String term = "term", definition = "definition";
        deck.addCard(term, definition);

        Card card = deck.getCard(term);
        Assertions.assertEquals(term, card.getTerm(), "Термин должен соответствовать указанному при создании");
        Assertions.assertEquals(definition,
                card.getDefinition(),
                "Определение должен соответствовать указанному при создании"
        );
        Assertions.assertEquals(deckInitCardsCount + 1, deck.getCardsCount(), "Должна добавиться только одна карта");
    }

    /**
     * Тестирование метода {@link Deck#addCard} с дублирующимися данными
     */
    @Test
    @DisplayName("Повторное добавление карты в колоду")
    void testAddRepeatCard() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        String term = "term", definition = "definition";
        deck.addCard(term, definition);
        int deckCardsCount = deck.getCardsCount();

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> deck.addCard(term, definition),
                "Добавление карты с существующим термином невозможно"
        );
        Assertions.assertEquals(deckCardsCount, deck.getCardsCount(), "Количество карт не должно измениться");
    }

    /**
     * Тестирование перегрузки метода {@link Deck#addCard} с корректными данными
     */
    @Test
    @DisplayName("Добавление экземпляра карты в колоду")
    void testAddCardInstance() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        int deckInitCardsCount = deck.getCardsCount();
        String term = "term", definition = "definition";
        Card card = new Card(term, definition);
        deck.addCard(card);

        Assertions.assertEquals(card, deck.getCard(term), "Карта не должна измениться при добавлении");
        Assertions.assertEquals(deckInitCardsCount + 1, deck.getCardsCount(), "Должна добавиться только одна карта");
    }

    /**
     * Тестирование перегрузки метода {@link Deck#addCard} с дублирующимися данными
     */
    @Test
    @DisplayName("Добавление дубля экземпляра карты в колоду")
    void testAddCardRepeatInstance() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        String term = "term", definition = "definition";
        Card card = new Card(term, definition);
        deck.addCard(card);
        int deckCardsCount = deck.getCardsCount();

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> deck.addCard(card),
                "Повторное добавление карты запрещено"
        );
        Assertions.assertEquals(deckCardsCount, deck.getCardsCount(), "Количество карт не должно измениться");
    }

    /**
     * Тестирование метода {@link Deck#updateCardTerm} на корректных данных
     */
    @Test
    @DisplayName("Изменение термина у карты")
    void testUpdateCardTerm() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        String term = "term", definition = "definition";
        Card card = new Card(term, definition);
        deck.addCard(card);
        int deckCardsCount = deck.getCardsCount();

        String newTerm = term + '1';
        deck.updateCardTerm(term, newTerm);

        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.getCard(term),
                "Старый экземпляр карты не должен существовать"
        );
        Assertions.assertNotEquals(card, deck.getCard(newTerm), "Экземпляр карты должен измениться");
        Assertions.assertEquals(definition, deck.getCard(newTerm).getDefinition(), "Определение должно сохраниться");
        Assertions.assertEquals(deckCardsCount, deck.getCardsCount(), "Количество карт в колоде не должно измениться");
    }

    /**
     * Тестирование метода {@link Deck#updateCardTerm} на отсутствующих данных
     */
    @Test
    @DisplayName("Изменение термина у отсутствующей карты")
    void testUpdateNonExistentCardTerm() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        String term = "term", definition = "definition";
        Card card = new Card(term, definition);
        deck.addCard(card);
        int deckCardsCount = deck.getCardsCount();

        String newTerm = term + '1';
        deck.updateCardTerm(term, newTerm);

        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.updateCardTerm(term, newTerm),
                "Невозможно изменить термин у отсутствующей карты"
        );
        Assertions.assertEquals(deckCardsCount, deck.getCardsCount(), "Количество карт в колоде не должно измениться");
    }

    /**
     * Тестирование метода {@link Deck#updateCardTerm} на дублирующихся данных
     */
    @Test
    @DisplayName("Изменение термина у карты на повторный")
    void testUpdateCardRepeatTerm() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        String term = "term", definition = "definition";
        String newTerm = term + '1';
        Card card = new Card(term, definition);
        deck.addCard(card);
        deck.addCard(newTerm, definition);
        int deckCardsCount = deck.getCardsCount();

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> deck.updateCardTerm(term, newTerm),
                "Невозможно изменить термин на повторный"
        );
        Assertions.assertEquals(deckCardsCount, deck.getCardsCount(), "Количество карт в колоде не должно измениться");
    }

    /**
     * Тестирование метода {@link Deck#updateCardDefinition} на корректных данных
     */
    @Test
    @DisplayName("Изменение определения у карты")
    void testUpdateCardDefinition() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        String term = "term", definition = "definition";
        Card card = new Card(term, definition);
        deck.addCard(card);

        String newDefinition = definition + '1';
        deck.updateCardDefinition(term, newDefinition);
        Card updatedCard = deck.getCard(term);

        Assertions.assertEquals(card, updatedCard, "Экземпляр карты не должен измениться");
        Assertions.assertEquals(card.getDefinition(), updatedCard.getDefinition(), "Определение должно было измениться");
    }

    /**
     * Тестирование метода {@link Deck#updateCardDefinition} на отсутствующих данных
     */
    @Test
    @DisplayName("Изменение определения у отсутствующей карты")
    void testUpdateCardNonExistentDefinition() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        String term = "term", definition = "definition";
        String newDefinition = definition + '1';


        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.updateCardDefinition(term, newDefinition),
                "Невозможно изменить определение у отстутствующей карты"
        );
    }

    /**
     * Тестирование метода {@link Deck#getCard} на существующих данных
     */
    @Test
    @DisplayName("Получение карты")
    void testGetCard() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        String term = "term", definition = "definition";
        Card card = new Card(term, definition);
        deck.addCard(card);

        Card actualCard = deck.getCard(term);

        Assertions.assertEquals(card, actualCard, "Карты должны быть одинаковыми");
    }

    /**
     * Тестирование метода {@link Deck#getCard} на отсутствующих данных
     */
    @Test
    @DisplayName("Получение отсутствующей карты")
    void testGetNonExistentCard() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        String term = "term";

        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.getCard(term),
                "Невозможно получить данные отсутствующей карты"
        );
    }

    /**
     * Тестирование метода {@link Deck#getCards}
     */
    @Test
    @DisplayName("Получение всех карт")
    void testGetCards() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);

        Assertions.assertEquals(0, deck.getCards().size(), "В колоде не должно быть карт");

        String term = "term", definition = "definition";
        Card card = new Card(term, definition);
        deck.addCard(card);

        Assertions.assertEquals(1, deck.getCards().size(), "Должна быть добавлена карта");
        Assertions.assertEquals(card, deck.getCards().iterator().next(), "Карта должна находиться в колоде");
    }

    /**
     * Тестирование метода {@link Deck#hashCode}
     */
    @Test
    @DisplayName("Сравнение хешей колод")
    void testHashCode() {
        String deckName = "Deck";
        String anotherDeckName = deckName + '1';
        Deck deck1 = new Deck(deckName);
        Deck deck2 = new Deck(deckName);
        Deck deck3 = new Deck(anotherDeckName);

        Assertions.assertEquals(
                deck1.hashCode(),
                deck2.hashCode(),
                "Хеши колод с одинаковым именем должны быть равны"
        );

        Assertions.assertNotEquals(
                deck2.hashCode(),
                deck3.hashCode(),
                "Хеши колод с разными именами должны быть разными"
        );
    }

    /**
     * Тестирование метода {@link Deck#removeCard} на корректных данных
     */
    @Test
    @DisplayName("Удаление карты")
    void testRemoveCard() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        String term = "term", definition = "definition";
        deck.addCard(term, definition);

        deck.removeCard(term);

        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.getCard(term),
                "Удаленная карта не должна существовать в колоде после ее удаления"
        );
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


        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> deck.removeCard(term),
                "Невозможно удалить отсутствующую карту"
        );
    }

    /**
     * Тестирование метода {@link Deck#getCardsDescription()}
     */
    @Test
    @DisplayName("Получение определений карт")
    void testGetCardsDescription() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);
        List<Card> cards = List.of(new Card("a", "a"),
                new Card("b", "b"),
                new Card("c", "c")
        );

        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            deck.addCard(card);
            sb.append(card).append("\n");
        }

        Assertions.assertEquals(
                sb.toString(),
                deck.getCardsDescription(),
                "Должно быть описание всех карт колоды без изменения их порядка"
        );
    }

    /**
     * Тестирование метода {@link Deck#equals}
     */
    @Test
    @DisplayName("Сравнение колод")
    void testEquals() {
        String deckName = "Deck";
        String anotherDeckName = deckName + '1';
        Deck deck1 = new Deck(deckName);
        Deck deck2 = new Deck(deckName);
        Deck deck3 = new Deck(anotherDeckName);

        Assertions.assertEquals(deck1, deck2, "Колоды с одинаковым именем должны быть равны");
        Assertions.assertNotEquals(deck1, deck3, "Колоды с разными именами не должны быть равны");
    }

    /**
     * Тестирование метода {@link Deck#toString} на колодах с одинаковым именем
     */
    @Test
    @DisplayName("Превращение колоды в строку")
    void testToString() {
        String deckName = "Deck";
        Deck deck = new Deck(deckName);

        Assertions.assertEquals(String.format("%s: 0 карт", deckName), deck.toString(), "Колода не должна содержать карты");

        String term = "term", definition = "definition";
        Card card = new Card(term, definition);
        deck.addCard(card);

        Assertions.assertEquals(String.format("%s: 1 карт", deckName), deck.toString(), "Колода не должна содержать карты");
    }
}