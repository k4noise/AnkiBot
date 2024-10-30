package ru.rtf.telegramBot.Commands;

import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;

/**
 * Класс команды /help
 */
public class HelpCommand implements Command {
    /**
     * Поле для отправки сообщений пользователю
     */
    private final SenderMessages senderMessages;

    /**
     * Количество параметров команды
     * нет параметров
     */
    private final int COUNT_PARAMS = 0;

    /**
     * Создание экземпляра команды для добавления новой карты
     *
     * @param senderMessages может отправлять сообщения
     */
    public HelpCommand(SenderMessages senderMessages) {
        this.senderMessages = senderMessages;
    }

    @Override
    public void execution(Long chatId, String[] params) {

    }

    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
