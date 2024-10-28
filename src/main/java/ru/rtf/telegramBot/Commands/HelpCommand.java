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

    public HelpCommand(SenderMessages senderMessages) {
        this.senderMessages = senderMessages;
    }

    /**
     * Выполнить команду
     *
     * @param chatId в каком чате выполнить
     * @param text   текст вызова команды
     */
    @Override
    public void execution(Long chatId, String text) {
        //TODO
    }
}
