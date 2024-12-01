package ru.rtf.telegramBot;

import ru.rtf.DeckManager;

/**
 * Интерфейс для обработчиков команд бота
 */
public interface CommandHandler {
    /**
     * Сообщение об ошибке исполнения команды
     */
    String MESSAGE_COMMAND_ERROR = """
            Ошибка выполнения команды. Подробности:
            %s""";

    /**
     * Обрабатывает команду
     *
     * @param usersDecks - колоды пользователя
     * @param chatId     - идентификатор чата
     * @param params     - параметры необходимые команде
     * @return сообщение о результате обработки команды
     */
    String handle(DeckManager usersDecks, Long chatId, String[] params);

    /**
     * Возвращает количество параметров нужных команде для выполнения
     *
     * @return количество параметров
     */
    int getParamsCount();
}
