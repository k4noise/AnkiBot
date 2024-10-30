package ru.rtf.telegramBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.rtf.telegramBot.Commands.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Базовый класс телеграм бота
 */
public class TelegramBotCore extends TelegramLongPollingBot {
    /**
     * Имя телеграм бота
     */
    private final String telegramBotName;
    /**
     * Хранит в себе список команд для бота
     */
    private final CommandManager commandManager;
    /**
     * Может возвращать список колод пользователя по id чата
     */
    private final UserDecksData userDecksData;
    /**
     * Хранилище текстовой команды бота и команды для выполнения
     */
    private final Map<String, Command> commands = new LinkedHashMap<>();

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
        //создать класс вывода сообщений пользователю
        SenderMessages senderMessages = new SenderMessages(this);

        userDecksData = new UserDecksData();
        //создать команды
        uploadCommands(senderMessages);
        commandManager = new CommandManager(commands, senderMessages, userDecksData);
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
            commandManager.execution(message.getChatId(), message.getText());
        }
    }

    @Override
    public String getBotUsername() {
        return telegramBotName;
    }

    /**
     * Добавление команд, их имени и экземпляра соответствующего класса в список команд
     */
    private void uploadCommands(SenderMessages senderMessages) {
        commands.put("/start", new StartCommand(senderMessages));
        commands.put("/help", new HelpCommand(senderMessages));
        // команды для работы с колодами
        commands.put("/list-decks", new ListDecksCommand(senderMessages, userDecksData));
        commands.put("/create-deck", new CreateDeckCommand(senderMessages, userDecksData));
        commands.put("/rename-deck", new RenameDeckCommand(senderMessages, userDecksData));
        commands.put("/delete-deck", new DeleteDeckCommand(senderMessages, userDecksData));
        // команды для работы с картами
        commands.put("/list-cards", new ListCardsCommands(senderMessages, userDecksData));
        commands.put("/create-card", new CreateCardCommand(senderMessages, userDecksData));
        commands.put("/edit-card-term", new EditCardTermCommand(senderMessages, userDecksData));
        commands.put("/edit-card-def", new EditCardDefCommand(senderMessages, userDecksData));
        commands.put("/delete-card", new DeleteCardCommand(senderMessages, userDecksData));
        commands.put("/list-card", new ListCardCommand(senderMessages, userDecksData));
    }
}