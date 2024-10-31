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

        userDecksData = new UserDecksData();
        //создать команды
        uploadCommands();
        commandManager = new CommandManager(commands, userDecksData);
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
            String messageResultExecution = commandManager.execute(chatId, message.getText());
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
        commands.put("/start", new StartCommand());
        commands.put("/help", new HelpCommand());
        // команды для работы с колодами
        commands.put("/list_decks", new ListDecksCommand());
        commands.put("/create_deck", new CreateDeckCommand());
        commands.put("/rename_deck", new RenameDeckCommand());
        commands.put("/delete_deck", new DeleteDeckCommand());
        // команды для работы с картами
        commands.put("/list_cards", new ListCardsCommand());
        commands.put("/create_card", new CreateCardCommand());
        commands.put("/edit_card_term", new EditCardTermCommand());
        commands.put("/edit_card_def", new EditCardDefCommand());
        commands.put("/delete_card", new DeleteCardCommand());
        commands.put("/list_card", new ListCardCommand());
    }
}