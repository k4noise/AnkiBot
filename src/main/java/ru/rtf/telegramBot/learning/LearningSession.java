package ru.rtf.telegramBot.learning;

/**
 * Сеанс обучения по колоде для пользователя
 * <p>Создается только на один сеанс</p>
 *
 * @author k4noise
 * @since 10.11.2024
 */
public interface LearningSession {

    /**
     * Формирует вопрос по карте, не показывавшейся пользователю в течении сеанса
     *
     * @return Сформулированный вопрос
     */
    String formQuestion();

    /**
     * Проверить, правильно ли пользователь ответил на вопрос по карте
     *
     * @param answer Ответ пользователя
     * @return Правильность ответа
     */
    boolean checkAnswer(String answer);

    /**
     * Получить вид режима обучения
     */
    LearningMode getMode();
}
