package ru.rtf.telegramBot.Commands;

import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;

/**
 * класс команды /start
 * обычно используется при начале диалога с ботом
 */
public class StartCommand implements Command {

    /**
     * поле для отправки сообщений пользователю
     */
    private final SenderMessages senderMessages;

    public StartCommand(SenderMessages senderMessages) {
        this.senderMessages = senderMessages;
    }

    /**
     * выполнить команду
     * вывод приветственного сообщения
     *
     * @param chatId в каком чате выполнить
     * @param text   текст вызова команды
     */
    @Override
    public void execution(Long chatId, String text) {
        senderMessages.sendMessage(chatId,
                "Добро пожаловать в AnkiBot. Введите команду /help, чтобы посмотреть доступные команды");
    }
}
