package ru.rtf.telegramBot.Commands;

import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;

/**
 * класс команды /start
 * обычно используется при начале диалога с ботом
 * выводит приветственное сообщение
 */
public class StartCommand implements Command {

    /**
     * поле для отправки сообщений пользователю
     */
    private final SenderMessages senderMessages;

    /**
     * Количество параметров команды
     * нет параметров
     */
    private final int COUNT_PARAMS = 0;

    public StartCommand(SenderMessages senderMessages) {
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
        senderMessages.sendMessage(chatId,
                "Добро пожаловать в AnkiBot. Введите команду /help, чтобы посмотреть доступные команды");
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
