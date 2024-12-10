package ru.rtf.telegramBot;

import ru.rtf.Card;
import ru.rtf.Deck;
import ru.rtf.telegramBot.learning.AnswerStatus;
import ru.rtf.telegramBot.learning.CardLearningStatus;

import java.util.EnumMap;
import java.util.Map;

/**
 * Рассчитывает статистики обучения
 */
public class StatsCalculator {
    /**
     * Максимально возможный процент изученности колоды
     */
    private static final int MAX_PERCENTAGE = 100;
    /**
     * Коэффициент для расчета статистики по ответам
     */
    private static final int PERCENTAGE_COEFFICIENT = MAX_PERCENTAGE / 2;

    /**
     * Минимально возможное количество баллов для получения статуса "Частично изучена"
     */
    private static final int MIN_SCORE_IN_PARTIALLY_STUDIED_STATUS = 5;
    /**
     * Минимально возможное количество баллов для получения статуса "Изучена"
     */
    private static final int MIN_SCORE_IN_STUDIED_STATUS = 10;

    /**
     * Хранилище коэффициентов для расчета статистики ответов
     */
    private final Map<AnswerStatus, Integer> answerScores;

    /**
     * Инициализация калькулятора статистики
     */
    public StatsCalculator() {
        answerScores = new EnumMap<>(AnswerStatus.class);
        answerScores.put(AnswerStatus.RIGHT, 1);
        answerScores.put(AnswerStatus.PARTIALLY_RIGHT, 0);
        answerScores.put(AnswerStatus.WRONG, -1);
    }

    /**
     * Получить процент успешности сеанса обучения целым числом с отбрасыванием дробной части
     *
     * @param stats Статистика сеанса
     */
    public int getSuccessLearningPercentage(EnumMap<AnswerStatus, Integer> stats) {
        int totalWeightedScore = 0;
        int totalAnswers = 0;

        for (AnswerStatus status : stats.keySet()) {
            int count = stats.getOrDefault(status, 0);
            totalAnswers += count;
            totalWeightedScore += count * PERCENTAGE_COEFFICIENT * (1 + answerScores.get(status));
        }

        return totalAnswers == 0 ? 0 : totalWeightedScore / totalAnswers;
    }

    /**
     * Получить процент изученности колоды целым числом с отбрасыванием дробной части
     */
    public int getDeckLearningPercentage(Deck deck) {
        int maxPossibleScore = deck.getCards().size() * Card.MAX_SCORE;
        if (maxPossibleScore == 0) {
            return 0;
        }

        int totalScore = 0;
        for (Card card : deck.getCards()) {
            totalScore += card.getScore();
        }

        return totalScore * MAX_PERCENTAGE / maxPossibleScore;
    }

    /**
     * Возвращает статистику по статусам карточек в колоде. <br>
     * Если нет ни одной карты определенного статуса, то в качестве значения статуса будет null
     *
     * @return Статистика в виде словаря,
     * где ключ - статус карты {@link CardLearningStatus}, значение - количество карт с таким статусом
     */
    public EnumMap<CardLearningStatus, Integer> getCardsCountByStatus(Deck deck) {
        EnumMap<CardLearningStatus, Integer> cardStats = new EnumMap<>(CardLearningStatus.class);
        for (Card card : deck.getCards()) {
            cardStats.merge(getCardStatus(card), 1, Integer::sum);
        }
        return cardStats;
    }

    /**
     * Получить статус карты
     */
    private CardLearningStatus getCardStatus(Card card) {
        int score = card.getScore();
        if (score >= MIN_SCORE_IN_STUDIED_STATUS) {
            return CardLearningStatus.STUDIED;
        } else if (score >= MIN_SCORE_IN_PARTIALLY_STUDIED_STATUS) {
            return CardLearningStatus.PARTIALLY_STUDIED;
        }
        return CardLearningStatus.NOT_STUDIED;
    }
}
