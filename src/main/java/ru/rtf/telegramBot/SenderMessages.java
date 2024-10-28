package ru.rtf.telegramBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Класс, который отправляет сообщения пользователю
 */
public class SenderMessages {
    /**
     * Телеграм бот, через который будут отправляться сообщения
     */
    private final TelegramBotCore telegramBot;

    public SenderMessages(TelegramBotCore telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * Отправить сообщение
     *
     * @param chatId  идентификатор чата
     * @param message текст сообщения
     */
    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            System.err.println("Не удалось отправить сообщение. " + e.getMessage());
        }
    }
}
