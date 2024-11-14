package ru.rtf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Класс для тестирования класса карт
 */
public class CardTest {
    /**
     * Тесты для проверки работы конструктора
     * парамеры конструктора не должны быть пустыми
     */
    @Test
    @DisplayName("Проверка конструктора")
    void testCardInitEmptyTwoParams() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Card("", ""),
                "Карта с пустыми параметрами не может быть создана"
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Card("", "abс"),
                "Карта с пустым термином не может быть создана"
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Card("term", ""),
                "Карта с пустым определением не может быть создана"
        );
    }

    /**
     * Проверка работы геттеров - термин
     */
    @Test
    @DisplayName("работа метода get() для термина")
    void testGetTerm() {
        Card card = new Card("cat", "кошка");
        String actualTerm = card.getTerm();
        Assertions.assertEquals("cat", actualTerm,
                "Возвращаемый термин не совпадает с заданным");
    }

    /**
     * Проверка работы геттеров - определение
     */
    @Test
    @DisplayName("работа метода get() для определения")
    void testGetDefinition() {
        Card card = new Card("fish", "рыба");
        String actualDefinition = card.getDefinition();
        Assertions.assertEquals("рыба", actualDefinition,
                "Возвращаемое определение не совпадает с заданным");
    }

    /**
     * Создание карты из существующей с новым термином и старым определением
     */
    @Test
    @DisplayName("изменение термина (новая карта)")
    void testChangeTerm() {
        Card originalCard = new Card("hero", "person who kill people");
        Card changedCard = originalCard.changeTerm("villain");
        Assertions.assertNotEquals(originalCard, changedCard,
                "При изменении термина карта не должна совпадать со своей старой версией");
        Assertions.assertEquals(originalCard.getDefinition(), changedCard.getDefinition(),
                "Определения должны совпадать");
        Assertions.assertEquals("villain", changedCard.getTerm(),
                "Термин должен совпадать с переданным значением");
    }

    /**
     * Изменение определения
     */
    @Test
    @DisplayName("изменение определения")
    void testChangeDefinition() {
        Card card = new Card("hero", "person who kill people");
        Card changedCard = new Card(card.getTerm(), card.getDefinition());
        changedCard.changeDefinition("person who saves people");
        Assertions.assertEquals(card, changedCard,
                "При изменении определения карта считается такой же");
        Assertions.assertEquals(card.getTerm(), changedCard.getTerm(),
                "Термины должны совпадать");
        Assertions.assertEquals("person who saves people", changedCard.getDefinition(),
                "Определение должно совпадать с переданным значением");
    }

    /**
     * Проверка корректности сравнения
     */
    @Test
    @DisplayName("Сравнение карт")
    void testEquals() {
        Card exCard = new Card("term", "definition");
        Card actCard1 = new Card("term", "definition");
        Card actCard2 = new Card("term", "else");
        Card actCard3 = new Card("else", "definition");
        Assertions.assertEquals(exCard, actCard1,
                "Карты созданные с одинаковыми параметрами должны быть равны");
        Assertions.assertEquals(exCard, actCard2,
                "Определение не должно влиять на сравнение карт");
        Assertions.assertNotEquals(exCard, actCard3,
                "Карты с разными терминами не могут совпадать");
    }

    /**
     * Проверка хеш кода
     */
    @Test
    @DisplayName("хеш код")
    void testHashCode() {
        Card exCard = new Card("term", "definition");
        Card actCard1 = new Card("term", "definition");
        Card actCard2 = new Card("term", "else");
        Card actCard3 = new Card("else", "definition");
        Assertions.assertEquals(exCard.hashCode(), actCard1.hashCode(),
                "Карты созданные с одинаковыми параметрами должны иметь одинаковый хеш");
        Assertions.assertEquals(exCard.hashCode(), actCard2.hashCode(),
                "Определение не должно влиять на хеш карт");
        Assertions.assertNotEquals(exCard.hashCode(), actCard3.hashCode(),
                "Карты с разными терминами не должны иметь один хеш");
    }

    /**
     * Строковое представление
     */
    @Test
    @DisplayName("Строковое представление")
    void testToString() {
        Card card = new Card("commit", "фиксация изменений");
        Assertions.assertEquals("\"commit\" = фиксация изменений", card.toString(),
                "строковое представление не совпадает с ожидаемым");
    }
}