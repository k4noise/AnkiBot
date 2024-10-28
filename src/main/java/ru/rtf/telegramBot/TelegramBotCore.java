package ru.rtf.telegramBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Базовый класс телеграм бота
 */
public class TelegramBotCore extends TelegramLongPollingBot {

    private final String telegramBotName;
    /**
     * хранит в себе список команд для бота
     */
    private final CommandManager commandManager;
    /**
     * может возвращать список колод пользователя по id чата
     */
    private final UserDecksData userDecksData;

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
        SenderMessages senderMessages = new SenderMessages(this);
        userDecksData = new UserDecksData();
        commandManager = new CommandManager(senderMessages, userDecksData);
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

    /**
     * вызывается каждый раз при отправке сообщения пользователем
     * исполняет введенную команду
     *
     * @param update информация о сообщении
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            commandManager.execution(message.getChatId(), message.getText());
        }
    }

    /**
     * Возвращает имя бота
     *
     * @return имя
     */
    @Override
    public String getBotUsername() {
        return telegramBotName;
    }
}