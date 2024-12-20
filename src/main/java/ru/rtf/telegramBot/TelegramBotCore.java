package ru.rtf.telegramBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Базовый класс телеграм бота
 */
public class TelegramBotCore extends TelegramLongPollingBot {
    /**
     * Имя телеграм бота
     */
    private final String telegramBotName;
    /**
     * Обработчик сообщений пользователя
     */
    private final MessageHandler messageHandler;

    /**
     * Создание экземпляра бота
     * и SenderMessages для отправки сообщений пользователю
     *
     * @param telegramBotName имя телеграм бота
     * @param token           токен
     */
    public TelegramBotCore(String telegramBotName, String token) {
        super(token);
        this.telegramBotName = telegramBotName;
        this.messageHandler = new MessageHandler();
    }

    /**
     * Запускает телеграм бота
     */
    public void start() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
            System.out.println("Telegram бот запущен");
        } catch (TelegramApiException e) {
            throw new RuntimeException("Не удалось запустить телеграм бота", e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            sendMessage(chatId, messageHandler.handle(chatId, message.getText()));
        }
    }

    @Override
    public String getBotUsername() {
        return telegramBotName;
    }

    /**
     * Отправить сообщение пользователю
     *
     * @param chatId  идентификатор чата
     * @param message текст сообщения
     */
    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.err.println("Не удалось отправить сообщение. " + e.getMessage());
        }
    }
}