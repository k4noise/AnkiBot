package ru.rtf;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;

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
    private String name;

    /**
     * Менеджер карт, отвечающий за все операции с картами в колоде
     */
    private final CardManager cardManager;

    /**
     * Инициализировать колоду
     *
     * @param name Имя колоды
     */
    public Deck(String name) {
        this.name = name;
        cardManager = new CardManager();
    }

    /**
     * Вычислить хеш колоды по её названию
     *
     * @return Хеш названия колоды
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Сравнить текущую и переданную колоды на равенство
     *
     * @param object Переданная колода
     * @return Результаты сравнения на равенство
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Deck deck = (Deck) object;
        return Objects.equals(name, deck.name);
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
     * Изменить имя колоды
     *
     * @param newName Новое имя колоды
     */
    public void updateName(String newName) {
        name = newName;
    }
}
