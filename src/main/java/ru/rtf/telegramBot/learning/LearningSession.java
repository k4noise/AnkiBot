package ru.rtf.telegramBot.learning;

import ru.rtf.Card;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

/**
 * Сеанс обучения по колоде для пользователя
 * <p>Создается только на один сеанс</p>
 */
public abstract class LearningSession {
    /**
     * Шаблон сообщения показа ответа
     */
    private static final String SHOW_RIGHT_ANSWER = """
            Правильный ответ:
            %s""";
    /**
     * Шаблон сообщения правильного ответа
     */
    public static final String CORRECT_ANSWER_INFO = "Верно! " + SHOW_RIGHT_ANSWER;
    /**
     * Шаблон сообщения неправильного ответа
     */
    public static final String INCORRECT_ANSWER_INFO = "Неверно. " + SHOW_RIGHT_ANSWER;

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
     */
    public abstract String formQuestion();

    /**
     * Проверить, правильно ли пользователь ответил на вопрос по карте
     * <p>Показ ответа подразумевает исключение текущей карточки из списка изучаемых</p>
     *
     * @param answer Ответ пользователя
     */
    public abstract boolean checkAnswer(String answer);

    /**
     * Проверяет, остались ли карты для обучения
     */
    public boolean hasCardsToLearn() {
        return !allCards.isEmpty();
    }

    /**
     * Вернуть строковое представление активной карты
     */
    public String getActiveCardDescription(){
        return allCards.peek().toString();
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
