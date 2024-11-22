package ru.rtf;

import java.util.Objects;

/**
 * Класс карты
 */
public class Card {
    /**
     * Максимально возможное количество баллов
     */
    public static final int MAX_SCORE = 12;
    /**
     * Минимально возможное количество баллов
     */
    private static final int MIN_SCORE = 0;
    /**
     * Минимально возможное количество баллов для получения статуса "Изучена"
     */
    private static final int MIN_SCORE_IN_STUDIED_STATUS = 10;
    /**
     * Минимально возможное количество баллов для получения статуса "Частично изучена"
     */
    private static final int MIN_SCORE_IN_PARTIALLY_STUDIED_STATUS = 5;


    /**
     * Термин - идентификатор внутри колоды
     */
    private final String term;
    /**
     * Определение
     */
    private String definition;
    /**
     * Баллы
     */
    private int score;

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
        this.term = term;
        this.definition = definition;
        this.score = MIN_SCORE;
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
     * Меняет термин в копии карты
     *
     * @param newTerm новый термин
     * @return новая карта
     */
    public Card changeTerm(String newTerm) {
        return new Card(newTerm, definition);
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

    /**
     * Добавить балл карточке
     */
    public void addScore() {
        addScore(1);
    }

    /**
     * Добавить баллы карточке
     *
     * @param scoreDiff Количество баллов для добавления
     */
    public void addScore(int scoreDiff) {
        if (scoreDiff < 0 || score == MAX_SCORE) {
            return;
        }
        score = Math.min(MAX_SCORE, score + scoreDiff);
    }

    /**
     * Убавить балл карточки
     */
    public void subtractScore() {
        if (score == MIN_SCORE) {
            return;
        }
        score--;
    }

    /**
     * Вернуть балл карточки
     */
    public int getScore() {
        return score;
    }

    /**
     * Вернуть статус карты
     */
    public CardLearningStatus getStatus() {
        if (score >= MIN_SCORE_IN_STUDIED_STATUS) {
            return CardLearningStatus.STUDIED;
        } else if (score >= MIN_SCORE_IN_PARTIALLY_STUDIED_STATUS) {
            return CardLearningStatus.PARTIALLY_STUDIED;
        }
        return CardLearningStatus.NOT_STUDIED;
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

    @Override
    public String toString() {
        return String.format("\"%s\" = %s", term, definition);
    }
}