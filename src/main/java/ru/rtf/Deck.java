package ru.rtf;

import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Колода с картами для обучения
 *
 * @author k4noise
 * @since 22.10.2024
 */
public class Deck {
    /**
     * Имя колоды - идентификатор
     */
    private String name;
    /**
     * Список карт колоды
     */
    private final Map<String, Card> cards;

    /**
     * Инициализировать колоду
     *
     * @param name Имя колоды
     */
    public Deck(String name) {
    }

    /**
     * Вычислить хеш колоды по её названию
     *
     * @return Хеш названия колоды
     */
    @Override
    public int hashCode() {
    }

    /**
     * Сравнить текущую и переданную колоды на равенство
     *
     * @param object Переданная колода
     * @return Результаты сравнения на равенство
     */
    @Override
    public boolean equals(Object object) {
    }

    /**
     * Получить имя колоды
     *
     * @return Имя колоды
     */
    public String getName() {
    }

    /**
     * Изменить имя колоды
     *
     * @param newName Новое имя колоды
     */
    public void updateName(String newName) {
    }

    // Взаимодействие с картами //

    /**
     * Добавить новую карту в колоду
     * Карта может быть добавлена, если термин внутри колоды уникален
     *
     * @param term       Термин новой карты
     * @param definition Определение новой карты
     * @throws IllegalArgumentException Карта с таким термином существует в колоде
     */
    public void addCard(String term, String definition) {
    }

    /**
     * Обновить термин карты
     * Термин изменяется в колоде и в самой карте
     *
     * @param oldTerm Старый термин карты
     * @param newTerm Новый термин карты
     * @throws NoSuchElementException   Карты с таким термином не существует в колоде
     * @throws IllegalArgumentException Карта с новым термином существует в колоде
     */
    public void updateTerm(String oldTerm, String newTerm) {
    }

    /**
     * Обновить определение карты
     *
     * @param term          Термин карты
     * @param newDefinition Новое определение карты
     * @throws NoSuchElementException Карты с таким термином не существует в колоде
     */
    public void updateDefinition(String term, String newDefinition) {
    }

    /**
     * Получить карту из колоды
     *
     * @param term Термин карты
     * @throws NoSuchElementException Карты с таким термином не существует в колоде
     */
    public Card getCard(String term)  {
    }

    /**
     * Получить все карты из колоды
     * @return Карты из колоды
     */
    public Collection<Card> getCards() {
    }

    /**
     * Удалить карту из колоды
     *
     * @param term Термин карты к удалению
     * @throws NoSuchElementException Карты с таким термином не существует в колоде
     */
    public void removeCard(String term) {
    }
}
