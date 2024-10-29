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
    public CommandManager(Map<String, Command> commands, SenderMessages senderMessages, UserDecksData userDecksData) {
        this.commands = commands;
        this.userDecksData = userDecksData;
        this.senderMessages = senderMessages;
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
        ParserMessageComand partsMessage = new ParserMessageComand(text);
        //добавление менеджера колод новому пользователю
        if(!userDecksData.containsUser(chatId))
            userDecksData.addUser(chatId);
        //поиск класса введенной команды
        Command command;
        try{
            command = getCommand(partsMessage.nameCommand());
        }catch (IllegalArgumentException e){
            senderMessages.sendMessage(chatId, e.getMessage());
            return;
        }
        //проверка параметров
        String[] paramsCommand = partsMessage.paramsCommand();
        if (!checkParameters(command, paramsCommand)) {
            //Выполнение команды
            command.execution(chatId, paramsCommand);
        }
        else {
            senderMessages.sendMessage(chatId,
                    "Ошибка параметров команды.\n Проверьте на соответствие шаблону (/help)");
        }
    }

    private boolean checkParameters(Command command, String[] paramsCommand){
        int correctCountParams = command.getCountParams();
        return (paramsCommand == null && correctCountParams == 0) ||
                (paramsCommand != null && paramsCommand.length >= correctCountParams);
    }
}
