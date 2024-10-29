package ru.rtf.telegramBot.Commands;

import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;

/**
 * класс команды /help
 */
public class HelpCommand implements Command {
    /**
     * поле для отправки сообщений пользователю
     */
    private final SenderMessages senderMessages;

    /**
     * Количество параметров команды
     * нет параметров
     */
    private final int COUNT_PARAMS = 0;

    public HelpCommand(SenderMessages senderMessages) {
        this.senderMessages = senderMessages;
    }


    /**
     * выполнить команду
     *
     * @param chatId идентификатор чата
     * @param params параметры команды без ее имени
     */
    @Override
    public void execution(Long chatId, String[] params) {

    }
    /**
     * Возвращает количество параметров нужных команде для выполнения
     *
     * @return количество параметров
     */
    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
