package ru.rtf.telegramBot.learning;

import ru.rtf.Card;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Сеанс обучения по колоде для пользователя
 * <p>Создается только на один сеанс</p>
 */
public abstract class LearningSession {

    /**
     * Карты к изучению
     */
    protected final Queue<Card> allCards;

    /**
     * Инициализировать режим обучения
     *
     * @param cards Карты к обучению
     */
    public LearningSession(Collection<Card> cards) {
        allCards = new ArrayDeque<>(cards);
    }

    /**
     * Сформировать вопрос по карте, не показывавшейся пользователю в течении сеанса
     *
     * @throws NoSuchElementException Нет активной карты
     */
    public abstract String formQuestion();

    /**
     * Проверить, правильно ли пользователь ответил на вопрос по карте
     * <p>Показ ответа подразумевает исключение текущей карточки из списка изучаемых</p>
     *
     * @param answer Ответ пользователя
     * @throws NoSuchElementException Нет активной карты
     */
    public abstract boolean checkAnswer(String answer);

    /**
     * Проверяет, остались ли карты для обучения
     */
    public boolean hasCardsToLearn() {
        return !allCards.isEmpty();
    }

    /**
     * Вернуть активную карту
     *
     * @throws NoSuchElementException Нет активной карты
     */
    public Card getActiveCard() {
        Card activeCard = allCards.peek();
        if (activeCard == null)
            throw new NoSuchElementException("Нет активной карты");
        return activeCard;
    }

    /**
     * Убирает активную карту из текущей сессии обучения
     */
    public void removeActiveCardFromStudy() {
        allCards.remove();
    }

    /**
     * Вернуть описание режима
     * <p>Содержит краткое имя режима и его описание</p>
     */
    public abstract String getDescription();
}
