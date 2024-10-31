package ru.rtf.telegramBot;

import ru.rtf.DeckManager;

/**
 * Интерфейс для команд бота
 */
public interface Command {

    /**
     * Выполняет команду.
     *
     * @param usersDecks - колоды пользователя
     * @param params - параметры необходимые команде
     * @return сообщение с результатом выполнения команды
     */
    String execute(DeckManager usersDecks, String[] params);

    /**
     * Возвращает количество параметров нужных команде для выполнения
     * @return количество параметров
     */
    int getParamsCount();
}
