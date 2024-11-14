package ru.rtf.telegramBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.rtf.telegramBot.learning.SessionManager;

/**
 * Базовый класс телеграм бота
 */
public class TelegramBotCore extends TelegramLongPollingBot {
    /**
     * Имя телеграм бота
     */
    private final String telegramBotName;
    /**
     * Управляет командами бота
     */
    private final CommandManager commandManager;
    /**
     * Управляет сессиями обучения
     */
    private final SessionManager sessionManager;

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

        sessionManager = new SessionManager();
        commandManager = new CommandManager(sessionManager);
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
            boolean isCommand = message.getText()
                    .trim()
                    .startsWith("/");

            String messageResultExecution = isCommand || !sessionManager.hasActive(chatId)
                    ? commandManager.handle(chatId, message.getText())
                    : sessionManager.handle(chatId, message.getText());
            sendMessage(chatId, messageResultExecution);
        }
    }

    /**
     * Отправить сообщение пользователю
     *
     * @param chatId  идентификатор чата
     * @param message текст сообщения
     */
    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setParseMode("MarkdownV2");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.err.println("Не удалось отправить сообщение. " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return telegramBotName;
    }
}