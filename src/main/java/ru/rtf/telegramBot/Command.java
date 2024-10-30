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
     * @return сообщение об успешном завершении или об ошибке
     */
    String execution(DeckManager usersDecks, String[] params);

    /**
     * Возвращает количество параметров нужных команде для выполнения
     * @return количество параметров
     */
    int getCountParams();
}
