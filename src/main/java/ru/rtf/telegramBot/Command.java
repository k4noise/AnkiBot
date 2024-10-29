package ru.rtf.telegramBot;

/**
 * интерфейс для команд бота
 */
public interface Command {

    /**
     * выполнить команду
     * @param chatId идентификатор чата
     * @param params параметры команды без ее имени
     */
    void execution(Long chatId, String[] params);

    /**
     * Возвращает количество параметров нужных команде для выполнения
     * @return количество параметров
     */
    int getCountParams();
}
