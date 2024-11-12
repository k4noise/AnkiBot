package ru.rtf.telegramBot;

import ru.rtf.telegramBot.learning.SessionManager;

/**
 * Интерфейс для обработчиков команд бота в режиме обучения
 */
public interface LearningCommandHandler {
    /**
     * Сообщение об ошибке исполнения команды
     */
    String MESSAGE_COMMAND_ERROR = """
            Ошибка выполнения команды. Подробности:
            %s""";

    /**
     * Обрабатывает команду в режиме обучения
     *
     * @param sessionManager Менеджер сессий пользователей
     * @param chatId         Идентификатор пользователя
     * @return сообщение о результате обработки команды
     */
    String handle(SessionManager sessionManager, Long chatId);

    /**
     * Возвращает количество параметров нужных команде для выполнения
     *
     * @return количество параметров
     */
    int getParamsCount();
}

