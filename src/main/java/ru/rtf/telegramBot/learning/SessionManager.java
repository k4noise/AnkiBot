package ru.rtf.telegramBot.learning;

import ru.rtf.Card;
import ru.rtf.telegramBot.learning.mode.MatchLearning;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * Управляет сессиями обучения пользователей
 */
public class SessionManager {
    /**
     * Хранилище активных сессий пользователей
     */
    private final Map<Long, LearningSession> sessions;

    /**
     * Инициализировать хранилище сессий
     */
    public SessionManager() {
        this.sessions = new HashMap<>();
    }

    /**
     * Начать новую сессию обучения для пользователя
     *
     * @param chatId Идентификатор чата
     * @param learningSession Сессия обучения
     * @return Описание режима и первый вопрос
     * @throws IllegalStateException  Имеется активная сессия обучения
     * @throws NoSuchElementException Колода не содержит карточек, доступных для изучения
     */
    public String start(Long chatId, LearningSession learningSession) {
        if (sessions.containsKey(chatId)) {
            throw new IllegalStateException("Имеется активная сессия обучения");
        }
        if (!learningSession.hasCardsToLearn()) {
            throw new NoSuchElementException("Колода не содержит карточек, доступных для изучения");
        }
        sessions.put(chatId, learningSession);
        return """
                Вы начали обучение %s
                Чтобы досрочно завершить сеанс, воспользуйтесь командой /end_check
                
                Ваш первый вопрос: %s"""
                .formatted(learningSession.getDescription(), learningSession.formQuestion());
    }

    /**
     * Обработать ответ пользователя
     *
     * @param chatId Идентификатор чата
     * @param text   Сообщение пользователя
     * @return Сообщение с результатом проверки ответа и новым вопросом
     */
    public String handle(Long chatId, String text) {
        LearningSession learningSession = sessions.get(chatId);
        boolean isRightAnswer = learningSession.checkAnswer(text);

        String activeCardDescription = learningSession.getActiveCardDescription();
        String checkMessage = isRightAnswer
                ? LearningSession.CORRECT_ANSWER_INFO.formatted(activeCardDescription)
                : LearningSession.INCORRECT_ANSWER_INFO.formatted(activeCardDescription);

        if (!learningSession.hasCardsToLearn()) {
            return checkMessage + '\n' + end(chatId);
        }
        return checkMessage + '\n' + learningSession.formQuestion();
    }

    /**
     * Завершить активную сессию обучения для пользователя
     *
     * @param chatId Идентификатор чата
     * @throws NoSuchElementException Нет активной сессии обучения
     */
    public String end(Long chatId) {
        if (!sessions.containsKey(chatId)) {
            throw new NoSuchElementException("Нет активной сессии обучения");
        }
        LearningSession session = sessions.remove(chatId);
        return session.hasCardsToLearn()
                ? "Вы досрочно завершили сессию"
                : "Вы прошли все карточки в колоде!";
    }

    /**
     * Проверить наличие активной сессии обучения для пользователя
     *
     * @param chatId Идентификатор чата
     */
    public boolean hasActive(Long chatId) {
        return sessions.containsKey(chatId);
    }

    /**
     * Получить активную сессию обучения для пользователя
     *
     * @param chatId Идентификатор чата
     */
    public LearningSession get(Long chatId) {
        return sessions.get(chatId);
    }
}
