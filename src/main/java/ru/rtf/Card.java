package ru.rtf;

import java.util.Objects;

/**
 * класс карты
 */
public class Card {
    /**
     * Термин - идентификатор внутри колоды
     */
    private final String term;
    /**
     * определение
     */
    private String definition;

    /**
     * Создание карты
     *
     * @param term       термин
     * @param definition определение
     */
    public Card(String term, String definition) {
        if (term.isEmpty() || definition.isEmpty())
            throw new IllegalArgumentException("Термин и определение не могут быть пустыми");
        this.term = term;
        this.definition = definition;
    }

    /**
     * возвращает термин
     *
     * @return термин
     */
    public String getTerm() {
        return term;
    }

    /**
     * возвращает определение
     *
     * @return определние
     */
    public String getDefinition() {
        return definition;
    }

    /**
     * Меняет термин и возвращает измененную карту
     * меняет хеш карты!
     *
     * @param newTerm новый термин
     * @return новая карта
     */
    public Card changeTerm(String newTerm) {
        return new Card(newTerm, definition);
    }

    /**
     * Меняет определение в карте
     * не меняет хеш
     *
     * @param newDefinition новое определение
     */
    public void changeDefinition(String newDefinition) {
        if (newDefinition.isEmpty())
            throw new IllegalArgumentException("Определение не может быть пустым");
        definition = newDefinition;
    }

    /**
     * Сравнивает карты
     * только по термину
     *
     * @param obj объект сравнения
     * @return совпадает ли с этой картой
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return Objects.equals(term, card.term);
    }

    /**
     * Возвращает хеш карты
     * только по термину
     *
     * @return хеш
     */
    @Override
    public int hashCode() {
        return Objects.hash(term);
    }

    /**
     * возвращает строковое представление карты
     *
     * @return строковое представление
     */
    @Override
    public String toString() {
        return String.format("\"%s\" = %s", term, definition);
    }
}