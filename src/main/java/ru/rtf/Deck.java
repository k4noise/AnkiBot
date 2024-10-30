package ru.rtf;

import java.util.*;

/**
 * Колода с картами для обучения
 * Взаимодействие с картами идет через класс CardManager
 *
 * @author k4noise
 * @since 22.10.2024
 */
public class Deck {
    /**
     * Имя колоды - идентификатор
     */
    private final String name;
    /**
     * Карты колоды, где ключ - термин карты, значение - сама карта
     */
    private final Map<String, Card> cards;

    /**
     * Инициализировать колоду
     *
     * @param name Имя колоды
     * @throws IllegalArgumentException Имя колоды не может быть пустым
     */
    public Deck(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Имя колоды не может быть пустым");
        }
        this.name = name;
        this.cards = new LinkedHashMap<>();
    }

    /**
     * Инициализация колоды с сохранением данных карт
     *
     * @param name  Имя колоды
     * @param cards Карты колоды
     * @throws IllegalArgumentException Имя колоды не может быть пустым
     */
    private Deck(String name, Map<String, Card> cards) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Имя колоды не может быть пустым");
        }
        this.name = name;
        this.cards = cards;
    }

    /**
     * Получить имя колоды
     *
     * @return Имя колоды
     */
    public String getName() {
        return name;
    }

    /**
     * Изменить название, создавая экземпляр новой колоды
     *
     * @param newName Новое имя
     * @return Новая колода
     */
    public Deck updateName(String newName) {
        return new Deck(newName, cards);
    }

    /**
     * Получить количество карт в колоде
     *
     * @return Количество карт
     */
    public int getCardsCount() {
        return cards.size();
    }

    /**
     * Добавить новую карту в колоду
     * Карта может быть добавлена, если термин внутри колоды уникален
     *
     * @param term       Термин новой карты
     * @param definition Определение новой карты
     * @throws IllegalArgumentException Карта с таким термином существует в колоде
     */
    public void addCard(String term, String definition) {
        if (cards.containsKey(term))
            throw new IllegalArgumentException("Карта с таким термином существует в колоде");
        cards.put(term, new Card(term, definition));
    }

    /**
     * Добавить новую карту в колоду
     *
     * @param card карта для добавления
     * @throws IllegalArgumentException Карта с таким термином существует в колоде
     */
    public void addCard(Card card) {
        if (cards.containsKey(card.getTerm()))
            throw new IllegalArgumentException("Карта с таким термином существует в колоде");
        cards.put(card.getTerm(), card);
    }

    /**
     * Обновить термин карты
     * Карта создается заново и заносится в карты колоды, предыдущая карта удаляется
     *
     * @param oldTerm Старый термин карты
     * @param newTerm Новый термин карты
     * @throws NoSuchElementException   Карты с таким термином не существует в колоде
     * @throws IllegalArgumentException Карта с новым термином существует в колоде
     */
    public void updateCardTerm(String oldTerm, String newTerm) {
        if (!cards.containsKey(oldTerm))
            throw new NoSuchElementException("Карты с таким термином не существует в колоде");
        if (cards.containsKey(newTerm))
            throw new IllegalArgumentException("Карта с новым термином существует в колоде");
        Card oldCard = cards.get(oldTerm);
        cards.remove(oldTerm);
        cards.put(newTerm, oldCard.changeTerm(newTerm));
    }

    /**
     * Обновить определение карты
     *
     * @param term          Термин карты
     * @param newDefinition Новое определение карты
     * @throws NoSuchElementException Карты с таким термином не существует в колоде
     */
    public void updateCardDefinition(String term, String newDefinition) {
        if (!cards.containsKey(term))
            throw new NoSuchElementException("Карты с таким термином не существует в колоде");
        cards.get(term).changeDefinition(newDefinition);
    }

    /**
     * Получить карту из колоды
     *
     * @param term Термин карты
     * @throws NoSuchElementException Карты с таким термином не существует в колоде
     */
    public Card getCard(String term) {
        if (!cards.containsKey(term))
            throw new NoSuchElementException("В колоде нет такой карты");
        return cards.get(term);
    }

    /**
     * Получить все карты из колоды
     *
     * @return Карты из колоды
     */
    public Collection<Card> getCards() {
        return cards.values();
    }

    /**
     * Удалить карту из колоды
     *
     * @param term Термин карты к удалению
     * @throws NoSuchElementException Карты с таким термином не существует в колоде
     */
    public void removeCard(String term) {
        if (!cards.containsKey(term))
            throw new NoSuchElementException("Карты с таким термином не существует в колоде");
        cards.remove(term);
    }

    /**
     * Получить описание всех карт колоды
     *
     * @return Описание карт
     */
    public String getCardsDescription() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards.values()) {
            sb.append(card.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Deck deck = (Deck) object;
        return Objects.equals(name, deck.name);
    }

    @Override
    public String toString() {
        return String.format("%s: %d карт", name, getCardsCount());
    }
}