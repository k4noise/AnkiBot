package ru.rtf.telegramBot.learning;

import ru.rtf.Card;

import java.util.*;

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
     * Статистика сеанса
     */
    protected final EnumMap<AnswerStatus, Integer> stats;

    /**
     * Инициализировать режим обучения
     *
     * @param cards Карты к обучению
     */
    public LearningSession(Collection<Card> cards) {
        allCards = new ArrayDeque<>(cards);
        stats = new EnumMap<>(AnswerStatus.class);
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
     * @return Статус ответа - правильный, частично правильный или неправильный
     * @throws NoSuchElementException Нет активной карты
     */
    public abstract AnswerStatus checkAnswer(String answer);

    /**
     * Вернуть описание режима
     * <p>Содержит краткое имя режима и его описание</p>
     */
    public abstract String getDescription();

    /**
     * Вернуть количество баллов к добавлению за правильный ответ пользователя
     */
    public abstract int getRightAnswerScoreAddition();

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
     * Обновить статистику ответов
     */
    public void updateStats(AnswerStatus status) {
        stats.merge(status, 1, Integer::sum);
    }

    /**
     * Обновить балл карты согласно статусу ответа
     */
    public void updateCardScore(Card card, AnswerStatus status) {
        if (status.equals(AnswerStatus.RIGHT)) {
            card.addScore(getRightAnswerScoreAddition());
        } else if (status.equals(AnswerStatus.WRONG)) {
            card.subtractScore();
        }
    }

    /**
     * Вернуть статистику ответов
     */
    public EnumMap<AnswerStatus, Integer> getStats() {
        return stats;
    }
}
