package ru.rtf.telegramBot;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.commands.*;
import ru.rtf.telegramBot.learning.LearningSession;
import ru.rtf.telegramBot.learning.SessionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для обработки сообщений пользователя
 * <p>Управляет передачей сообщения нужному обработчику</p>
 */
public class MessageHandler {
    /**
     * Хранилище команд
     */
    private final Map<String, CommandHandler> commands;
    /**
     * Хранилище менеджеров колод пользователя
     */
    private final Map<Long, DeckManager> userDeckManagers;
    /**
     * Менеджер сессий пользователя
     */
    private final SessionManager sessionManager;

    /**
     * Инициализирует поля и добавляет команды по умолчанию в список
     */
    public MessageHandler() {
        this(new HashMap<>(), new SessionManager());
        createDefaultCommandsWithHandlers();
    }

    /**
     * Инициализирует поля и добавляет пользовательские команды в список
     *
     * @param commands       Хранилище команд
     * @param sessionManager Менеджер сессий
     */
    public MessageHandler(Map<String, CommandHandler> commands, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.commands = commands;
        this.userDeckManagers = new HashMap<>();
    }

    /**
     * Обрабатывает сообщение пользователя
     *
     * @param chatId Идентификатор чата
     * @param text   Сообщение пользователя
     * @return Результат обработки
     */
    public String handle(Long chatId, String text) {
        boolean isCommand = text
                .trim()
                .startsWith("/");

        return isCommand || !sessionManager.hasActive(chatId)
                ? handleCommand(chatId, text)
                : handleAnswerInLearning(chatId, text);
    }

    /**
     * Обрабатывает сообщение пользователя как команду
     *
     * @return Результат выполнения команды
     */
    private String handleCommand(Long chatId, String text) {
        CommandParser commandParser = new CommandParser(text);
        DeckManager userDeckManager = userDeckManagers.computeIfAbsent(chatId, id -> new DeckManager());

        String commandName = commandParser.getCommandName();
        CommandHandler commandHandler = commands.get(commandName);
        if (commandHandler == null) {
            return "Команда " + commandName + " не распознана";
        }

        String[] commandParams = commandParser.getCommandParams();
        return checkArgumentsCount(commandHandler, commandParams)
                ? commandHandler.handle(userDeckManager, chatId, commandParams)
                : "Ошибка параметров команды\n Проверьте на соответствие шаблону \\(/help\\)";
    }

    /**
     * Обрабатывает сообщение пользователя как ответ в режиме обучения
     *
     * @return Результат обработки ответа
     */
    private String handleAnswerInLearning(Long chatId, String text) {
        LearningSession learningSession = sessionManager.get(chatId);
        boolean isRightAnswer = learningSession.checkAnswer(text);
        String activeCardDescription = learningSession.pullActiveCardDescription();

        String checkMessage = isRightAnswer
                ? LearningSession.CORRECT_ANSWER_INFO.formatted(activeCardDescription)
                : LearningSession.INCORRECT_ANSWER_INFO.formatted(activeCardDescription);

        if (!learningSession.hasCardsToLearn()) {
            sessionManager.end(chatId);
            return checkMessage + '\n' + "Вы прошли все карточки в колоде!";
        }
        return checkMessage + '\n' + learningSession.formQuestion();
    }

    /**
     * <p>Проверка наличия необходимого количества аргументов для команды</p>
     * <p>Количество аргументов зависит от конкретной команды</p>
     *
     * @param commandHandler Обработчик команды
     * @param commandParams  Параметры для запуска команды
     * @return Достаточно ли количества параметров для запуска команды
     */
    private boolean checkArgumentsCount(CommandHandler commandHandler, String[] commandParams) {
        int correctCountParams = commandHandler.getParamsCount();

        if (commandParams == null) {
            return correctCountParams == 0;
        }

        return commandParams.length >= correctCountParams;
    }

    /**
     * Создает команды по умолчанию вместе с обработчиками команд
     */
    private void createDefaultCommandsWithHandlers() {
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
        //команды режимов обучения
        commands.put("/check", new LearnCheckCommandHandler(sessionManager));
        commands.put("/end_check", new EndCheckCommandHandler(sessionManager));
    }
}