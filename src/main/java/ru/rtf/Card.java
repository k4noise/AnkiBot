package ru.rtf;

import java.util.Objects;

/**
 * Класс карты
 */
public class Card {
    /**
     * Термин
     */
    private String term;
    /**
     * Определение
     */
    private String definition;

    /**
     * Создание карты
     *
     * @param term       термин
     * @param definition определение
     * @throws IllegalArgumentException Термин и определение не могут быть пустыми
     */
    public Card(String term, String definition) {
        if (term.isEmpty() || definition.isEmpty())
            throw new IllegalArgumentException("Термин и определение не могут быть пустыми");
        this.term = term.toLowerCase();
        this.definition = definition;
    }

    /**
     * Возвращает термин
     */
    public String getTerm() {
        return term;
    }

    /**
     * Возвращает определение
     */
    public String getDefinition() {
        return definition;
    }

    /**
     * Меняет термин
     */
    void changeTerm(String newTerm) {
        this.term = newTerm.toLowerCase();
    }

    /**
     * Меняет определение в карте
     *
     * @param newDefinition новое определение
     */
    public void changeDefinition(String newDefinition) {
        if (newDefinition.isEmpty())
            throw new IllegalArgumentException("Определение не может быть пустым");
        definition = newDefinition;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return Objects.equals(term, card.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(term);
    }

    /**
     * Возвращает описание карточки
     * <p>в формате "термин" = определение</p>
     */
    public String getDescription() {
        return String.format("\"%s\" = %s", term, definition);
    }
}