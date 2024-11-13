package ru.rtf;

import java.util.*;

/**
 * Колода с картами для обучения
 */
public class Deck {
    /**
     * Имя колоды
     */
    private String name;
    /**
     * Карты колоды
     */
    private final Map<String, Card> cards;

    /**
     * Инициализировать колоду
     *
     * @param name Имя колоды
     * @throws IllegalArgumentException Имя колоды не может быть пустым
     */
    public Deck(String name) {
        this(name, new LinkedHashMap<>());
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
        this.name = name.toLowerCase();
        this.cards = cards;
    }

    /**
     * Получить имя колоды
     */
    public String getName() {
        return name;
    }

    /**
     * Изменить название колоды
     *
     * @param newName Новое имя
     */
    void changeName(String newName) {
        this.name = newName.toLowerCase();
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
        String lowerCaseTerm = term.toLowerCase();
        validateUnique(lowerCaseTerm);
        cards.put(lowerCaseTerm, new Card(term, definition));
    }

    /**
     * Добавить новую карту в колоду
     *
     * @param card карта для добавления
     * @throws IllegalArgumentException Карта с термином term существует в колоде
     */
    public void addCard(Card card) {
        String lowerCaseTerm = card.getTerm().toLowerCase();
        validateUnique(lowerCaseTerm);
        cards.put(lowerCaseTerm, card);
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
        String lowerCaseOldTerm = oldTerm.toLowerCase();
        String lowerCaseNewTerm = newTerm.toLowerCase();
        validateExists(lowerCaseOldTerm);
        validateUnique(lowerCaseNewTerm);

        Card card = cards.remove(lowerCaseOldTerm);
        card.changeTerm(newTerm);
        cards.put(lowerCaseNewTerm, card);
    }

    /**
     * Обновить определение карты
     *
     * @param term          Термин карты
     * @param newDefinition Новое определение карты
     * @throws NoSuchElementException Карта с термином term не существует в колоде
     */
    public void updateCardDefinition(String term, String newDefinition) {
        String lowerCaseTerm = term.toLowerCase();
        validateExists(lowerCaseTerm);
        cards.get(lowerCaseTerm).changeDefinition(newDefinition);
    }

    /**
     * Получить карту из колоды
     *
     * @param term Термин карты
     * @throws NoSuchElementException Карта с термином term не существует в колоде
     */
    public Card getCard(String term) {
        String lowerCaseTerm = term.toLowerCase();
        validateExists(lowerCaseTerm);
        return cards.get(lowerCaseTerm);
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
        String lowerCaseTerm = term.toLowerCase();
        validateExists(lowerCaseTerm);
        cards.remove(lowerCaseTerm);
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
        if (cards.containsKey(term))
            throw new IllegalArgumentException("Карта с термином " + term + " существует в колоде");

    }

    /**
     * Проверяет колоду на существование карты по термину
     *
     * @throws NoSuchElementException Карта с термином term не существует в колоде
     */
    private void validateExists(String term) {
        if (!cards.containsKey(term))
            throw new NoSuchElementException("Карта с термином " + term + " не существует в колоде");
    }
}