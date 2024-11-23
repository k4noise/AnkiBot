package ru.rtf.telegramBot;

import ru.rtf.Card;
import ru.rtf.Deck;
import ru.rtf.telegramBot.learning.AnswerStatus;

import java.util.EnumMap;
import java.util.Map;

/**
 * Рассчитывает статистики обучения
 */
public class StatsCalculator {
    /**
     * Коэффициент для расчета статистики по ответам
     */
    private static final int PERCENTAGE_COEFFICIENT = 50;
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
     * Получить процент успешности целым числом с отбрасыванием дробной части
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

        return totalScore * 100 / maxPossibleScore;
    }
}
