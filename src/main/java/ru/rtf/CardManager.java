package ru.rtf;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
/**
 * Менеджер управления картами в колоде пользователя
 */
public class CardManager {
    // Взаимодействие с картами через CardManager //
    private final Map<String, Card> cards;
    public CardManager(){
        cards = new LinkedHashMap<>();
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
        if(cards.containsKey(term))
            throw new IllegalArgumentException("Карта с таким термином существует в колоде");
        cards.put(term, new Card(term, definition));
    }
    /**
     * Добавить новую карту в колоду
     * @param card карта для добавления
     */
    public void addCard(Card card) {
        if(cards.containsKey(card.getTerm()))
            throw new IllegalArgumentException("Карта с таким термином существует в колоде");
        cards.put(card.getTerm(), card);
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
        if(!cards.containsKey(oldTerm))
            throw new NoSuchElementException("Карты с таким термином не существует в колоде");
        if(cards.containsKey(newTerm))
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
    public void updateDefinition(String term, String newDefinition) {
        if(!cards.containsKey(term))
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
        if(!cards.containsKey(term))
            throw new NoSuchElementException("В колоде нет такой карты");
        return cards.get(term);
    }
    /**
     * Получить все карты из колоды
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
        if(!cards.containsKey(term))
            throw new NoSuchElementException("Карты с таким термином не существует в колоде");
        cards.remove(term);
    }
}