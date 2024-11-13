package ru.rtf.telegramBot.learning;

/**
 * Сеанс обучения по колоде для пользователя
 * <p>Создается только на один сеанс</p>
 */
public interface LearningSession {
    /**
     * Шаблон сообщения показа ответа
     */
    String SHOW_RIGHT_ANSWER = """
            Правильный ответ:
            %s""";
    /**
     * Шаблон сообщения правильного ответа
     */
    String CORRECT_ANSWER_INFO = "Верно\\! " + SHOW_RIGHT_ANSWER;
    /**
     * Шаблон сообщения неправильного ответа
     */
    String INCORRECT_ANSWER_INFO = "Неверно\\. " + SHOW_RIGHT_ANSWER;

    /**
     * Сформировать вопрос по карте, не показывавшейся пользователю в течении сеанса
     *
     * @return Сформулированный вопрос
     */
    String formQuestion();

    /**
     * Проверить, правильно ли пользователь ответил на вопрос по карте и вернуть результат проверки
     * <p>Показ ответа подразумевает исключение текущей карточки из списка изучаемых</p>
     *
     * @param answer Ответ пользователя
     * @return Результат проверки
     */
    String messageCheckAnswer(String answer);

    /**
     * Проверяет, остались ли карты для обучения
     */
    boolean hasCardsToLearn();

    /**
     * Вернуть описание активной карты - термин: определение
     */
    String pullActiveCardDescription();

    /**
     * Вернуть описание режима
     * <p>Содержит краткое имя режима и его описание</p>
     */
    String getDescription();
}
