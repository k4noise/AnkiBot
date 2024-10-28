package ru.rtf.telegramBot;

import ru.rtf.telegramBot.Commands.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс управления командами бота
 */
public class CommandManager {

    /**
     * список команд (название, экземпляр соответствующего класса)
     */
    private final Map<String, Command> commands;
    /**
     * может возвращать список колод пользователя по id чата
     */
    private final UserDecksData userDecksData;
    /**
     * единый объект для отправки сообщений пользователю
     */
    private final SenderMessages senderMessages;

    /**
     * инициализирует поля и добавляет команды в список
     *
     * @param senderMessages может отправлять сообщения
     * @param userDecksData  для доступа к колодам конкретного пользователя
     */
    public CommandManager(SenderMessages senderMessages, UserDecksData userDecksData) {
        commands = new HashMap<>();
        this.userDecksData = userDecksData;
        this.senderMessages = senderMessages;
        uploadCommands();
    }

    /**
     * добавление команд, их имени и экземпляра соответствующего класса в список команд
     */
    private void uploadCommands() {
        commands.put("/start", new StartCommand(senderMessages));
        commands.put("/help", new HelpCommand(senderMessages));
        commands.put("/list-decks", new ListDecksCommand(senderMessages, userDecksData));
        commands.put("/create-deck", new CreateDeckCommand(senderMessages, userDecksData));
        commands.put("/rename-deck", new RenameDeckCommand(senderMessages, userDecksData));
        commands.put("/delete-deck", new DeleteDeckCommand(senderMessages, userDecksData));
        //TODO добавить команды с карточками
    }

    /**
     * Возвращает команду по ее названию
     *
     * @param nameCommand название команды (начинается с "/")
     * @return экземпляр команды
     */
    public Command getCommand(String nameCommand) {
        if (commands.containsKey(nameCommand))
            return commands.get(nameCommand);
        throw new IllegalArgumentException("Команда " + nameCommand + " не распознана");
    }

    public void execution(Long chatId, String text) {
        String nameCommand = nameCommandInMassege(text);
        getCommand(nameCommand).execution(chatId, text);
    }

    /**
     * Возвращает команду из пользовательского сообщения
     *
     * @param message сообщение пользователя
     * @return имя команды
     */
    private String nameCommandInMassege(String message) {
        return message.split(" ")[0];
    }
}
