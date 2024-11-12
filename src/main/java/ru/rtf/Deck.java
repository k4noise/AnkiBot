package ru.rtf;

import java.util.*;

/**
 * Колода с картами для обучения
 *
 * @author Кистанова Марина
 * @since 22.10.2024
 */
public class Deck {
    /**
     * Имя колоды - идентификатор
     */
    private final String name;
    /**
     * Карты колоды, где ключ - термин карты, значение - сама карта
     * Термин карты хранится в нижнем регистре для обеспечения регистронезависимости при получении карты
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
        validateUnique(term);
        cards.put(term.toLowerCase(), new Card(term, definition));
    }

    /**
     * Добавить новую карту в колоду
     *
     * @param card карта для добавления
     * @throws IllegalArgumentException Карта с термином term существует в колоде
     */
    public void addCard(Card card) {
        String term = card.getTerm();
        validateUnique(term);
        cards.put(term.toLowerCase(), card);
    }

    /**
     * Обновить термин карты
     * <p>Карта создается заново и заносится в карты колоды, предыдущая карта удаляется</p>
     *
     * @param oldTerm Старый термин карты
     * @param newTerm Новый термин карты
     * @throws NoSuchElementException   Карты с термином term не существует в колоде
     * @throws IllegalArgumentException Карта с термином term существует в колоде
     */
    public void updateCardTerm(String oldTerm, String newTerm) {
        validateExists(oldTerm);
        validateUnique(newTerm);

        Card oldCard = cards.get(oldTerm.toLowerCase());
        cards.remove(oldTerm.toLowerCase());
        cards.put(newTerm.toLowerCase(), oldCard.changeTerm(newTerm));
    }

    /**
     * Обновить определение карты
     *
     * @param term          Термин карты
     * @param newDefinition Новое определение карты
     * @throws NoSuchElementException Карта с термином term не существует в колоде
     */
    public void updateCardDefinition(String term, String newDefinition) {
        validateExists(term);
        cards.get(term.toLowerCase()).changeDefinition(newDefinition);
    }

    /**
     * Получить карту из колоды
     *
     * @param term Термин карты
     * @throws NoSuchElementException Карта с термином term не существует в колоде
     */
    public Card getCard(String term) {
        validateExists(term);
        return cards.get(term.toLowerCase());
    }

    /**
     * Получить все карты из колоды
     */
    public Collection<Card> getCards() {
        return cards.values();
    }

    /**
     * Удалить карту из колоды по термину
     *
     * @throws NoSuchElementException Карта с термином term не существует в колоде
     */
    public void removeCard(String term) {
        validateExists(term);
        cards.remove(term.toLowerCase());
    }

    /**
     * Получить описание всех карт колоды
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

    /**
     * Проверяет колоду на уникальность карты по термину
     *
     * @throws IllegalArgumentException Карта с термином term существует в колоде
     */
    private void validateUnique(String term) {
        if (cards.containsKey(term.toLowerCase()))
            throw new IllegalArgumentException("Карта с термином " + term + " существует в колоде");

    }

    /**
     * Проверяет колоду на существование карты по термину
     *
     * @throws NoSuchElementException Карта с термином term не существует в колоде
     */
    private void validateExists(String term) {
        if (!cards.containsKey(term.toLowerCase()))
            throw new NoSuchElementException("Карта с термином " + term + " не существует в колоде");
    }
}