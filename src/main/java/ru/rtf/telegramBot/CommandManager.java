package ru.rtf.telegramBot;

import java.util.Map;

/**
 * Класс управления командами бота
 */
public class CommandManager {

    /**
     * Хранилище команд (название, экземпляр соответствующего класса)
     */
    private final Map<String, Command> commands;
    /**
     * Хранилище колод пользователя по id чата
     */
    private final UserDecksData userDecksData;

    /**
     * Инициализирует поля и добавляет команды в список
     *
     * @param commands словарь команд
     * @param userDecksData для доступа к колодам конкретного пользователя
     */
    public CommandManager(Map<String, Command> commands, UserDecksData userDecksData) {
        this.commands = commands;
        this.userDecksData = userDecksData;
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

    /**
     * Выполнение команды по сообщению пользователя
     *
     * @param chatId Идентификатор чата телеграма
     * @param text   Сообщение пользователя
     * @return сообщение об успешном выполнении команды или об ошибке
     */
    public String execution(Long chatId, String text) {
        ParserMessageCommand partsMessage = new ParserMessageCommand(text);
        //добавление менеджера колод новому пользователю
        if (!userDecksData.containsUser(chatId))
            userDecksData.addUser(chatId);
        //поиск класса введенной команды
        Command command;
        try {
            command = getCommand(partsMessage.nameCommand());
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        //проверка параметров
        String[] paramsCommand = partsMessage.paramsCommand();
        if (checkParameters(command, paramsCommand)) {
            //Выполнение команды
            return command.execution(userDecksData.getUserDecks(chatId), paramsCommand);
        }
        return "Ошибка параметров команды.\n Проверьте на соответствие шаблону (/help)";
    }

    /**
     * <p>Проверка наличия необходимого количества аргументов для команды.</p>
     * <p>Количество аргументов зависит от конкретной команды</p>
     *
     * @param command       Команда
     * @param paramsCommand Параметры для запуска команды
     * @return Достаточно ли количества параметров для запуска команды
     */
    private boolean checkParameters(Command command, String[] paramsCommand) {
        int correctCountParams = command.getCountParams();
        return (paramsCommand == null && correctCountParams == 0) ||
                (paramsCommand != null && paramsCommand.length >= correctCountParams);
    }
}
