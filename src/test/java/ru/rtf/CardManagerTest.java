package ru.rtf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
/**
 * Класс для тестирования класса CardManager
 */
public class CardManagerTest {
    private CardManager cardManager;
    @BeforeEach
    void setUp() {
        cardManager = new CardManager();
    }
    /**
     * тесты на добавление карты в колоду
     */
    @Test
    @DisplayName("добавление карты с помощью её термина и определения")
    void testAddCardTermAndDefinition() {
        cardManager.addCard("cat", "Кошка");
        Assertions.assertEquals(1, cardManager.getCards().size());
        Assertions.assertEquals(new Card("cat", "Кошка"),
                cardManager.getCard("cat"),
                "Карта не должна меняться при добавлении");
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                cardManager.addCard("cat", "Кошка")
        );
        cardManager.addCard("mouse", "мышь");
        Assertions.assertEquals(2, cardManager.getCards().size(),
                "ошибка добавления новой карты");
    }
    @Test
    @DisplayName("добавление карты с помощью экземпляра карты")
    void testAddCardWithCard() {
        Card card = new Card("mouse", "мышь");
        cardManager.addCard(card);
        Assertions.assertEquals(1, cardManager.getCards().size());
        Assertions.assertEquals(card, cardManager.getCard("mouse"),
                "Карта не должна меняться при добавлении");
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                cardManager.addCard(card)
        );
        cardManager.addCard("cat", "кошка");
        Assertions.assertEquals(2, cardManager.getCards().size(),
                "ошибка добавления новой карты");
    }
    /**
     * тесты на изменение карты в колоде
     */
    @Test
    @DisplayName("Изменение термина карты и ее ключа")
    void testUpdateTerm() {
        cardManager.addCard("old", "definition");
        cardManager.updateTerm("old", "new");
        Assertions.assertNotNull(cardManager.getCard("new"));
        Assertions.assertThrows(NoSuchElementException.class, () ->
                        cardManager.getCard("old"),
                "Карта со старым термином должна быть удалена из колоды"
        );
        Assertions.assertThrows(NoSuchElementException.class, () ->
                cardManager.updateTerm("notExist", "newTerm")
        );
    }
    @Test
    @DisplayName("Нельзя измененить термин на существующий в колоде")
    void testUpdateTerm_throwsExceptionIfNewTermExists() {
        cardManager.addCard("exist", "definition");
        cardManager.addCard("term", "definition");
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                cardManager.updateTerm("term", "exist")
        );
    }
    @Test
    @DisplayName("Изменение определения карты")
    void testUpdateDefinition() {
        cardManager.addCard("commit", "комит");
        cardManager.updateDefinition("commit", "фиксация изменений");
        Assertions.assertEquals("фиксация изменений",
                cardManager.getCard("commit").getDefinition(),
                "Определение карты должно было измениться");
        Assertions.assertThrows(NoSuchElementException.class, () ->
                cardManager.updateDefinition("notExist", "newDefinition")
        );
    }
    /**
     * Возвращение карт из колоды
     */
    @Test
    @DisplayName("Возвращение карты из колоды по термину")
    void testGetCard() {
        cardManager.addCard("exist", "definition");
        Card card = cardManager.getCard("exist");
        Assertions.assertEquals("exist", card.getTerm());
        Assertions.assertEquals("definition", card.getDefinition());
        Assertions.assertThrows(NoSuchElementException.class, () ->
                cardManager.getCard("notExist")
        );
    }
    @Test
    @DisplayName("Вернуть все карты колоды")
    void testGetCards() {
        cardManager.addCard("cat", "кошка");
        cardManager.addCard("fish", "рыба");
        cardManager.addCard("mouse", "мышь");
        Collection<Card> allCards = new HashSet<>(cardManager.getCards());
        Assertions.assertEquals(3, allCards.size());
    }
    @Test
    @DisplayName("Удалить карту")
    void removeCard_successful() {
        cardManager.addCard("del", "definition");
        cardManager.removeCard("del");
        Assertions.assertEquals(0, cardManager.getCards().size());
        Assertions.assertThrows(NoSuchElementException.class, () ->
                cardManager.removeCard("notExist")
        );
    }
}