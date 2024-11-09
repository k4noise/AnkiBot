package ru.rtf.telegramBot;

import ru.rtf.DeckManager;

/**
 * Интерфейс для обработчиков команд бота
 */
public interface CommandHandler {

    /**
     * Выполняет команду.
     *
     * @param usersDecks - колоды пользователя
     * @param params     - параметры необходимые команде
     * @return сообщение об успешном завершении или об ошибке
     */
    String execute(DeckManager usersDecks, String[] params);

    /**
     * Возвращает количество параметров нужных команде для выполнения
     *
     * @return количество параметров
     */
    int getParamsCount();

    /**
     * Сообщение об ошибке исполнения команды
     */
    String MessageComandError = """
            Ошибка выполнения команды. Подробности:
            %s""";
}
