package ru.rtf.telegramBot;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * интерфейс для команд бота
 */
public interface Command {

    /**
     * выполнить команду
     * @param chatId в каком чате выполнить
     * @param text текст вызова команды
     */
    void execution(Long chatId, String text);
}
