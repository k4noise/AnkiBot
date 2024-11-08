package ru.rtf.telegramBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
    private final CommandHandlerManager commandHandlerManager;
    /**
     * Может возвращать список колод пользователя по id чата
     */
    private final UserDecksData userDecksData;
    /**
     * Хранилище текстовой команды бота и команды для выполнения
     */
    private final Map<String, CommandHandler> commands = new LinkedHashMap<>();

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

        userDecksData = new UserDecksData();
        //создать команды
        uploadCommands();
        commandHandlerManager = new CommandHandlerManager(commands, userDecksData);
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
            String messageResultExecution = commandHandlerManager.execution(chatId, message.getText());
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

    /**
     * Добавление команд, их имени и экземпляра соответствующего класса в список команд
     */
    private void uploadCommands() {
        commands.put("/start", new StartCommandHandler());
        commands.put("/help", new HelpCommandHandler());
        // команды для работы с колодами
        commands.put("/list_decks", new ListDecksCommandHandler());
        commands.put("/create_deck", new CreateDeckCommandHandler());
        commands.put("/rename_deck", new RenameDeckCommandHandler());
        commands.put("/delete_deck", new DeleteDeckCommandHandler());
        // команды для работы с картами
        commands.put("/list_cards", new ListCardsCommandsHandler());
        commands.put("/create_card", new CreateCardCommandHandler());
        commands.put("/edit_card_term", new EditCardTermCommandHandler());
        commands.put("/edit_card_def", new EditCardDefCommandHandler());
        commands.put("/delete_card", new DeleteCardCommandHandler());
        commands.put("/list_card", new ListCardCommandHandler());
    }
}