package ru.rtf.telegramBot.commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

/**
 * Обработчик команды начала диалога с пользователем
 */
public class StartCommandHandler implements CommandHandler {

    /**
     * Количество параметров команды
     * нет параметров
     */
    private final int COUNT_PARAMS = 0;

    @Override
    public String handle(DeckManager usersDecks, Long id, String[] params) {
        return "Добро пожаловать в AnkiBot. Введите команду /help, чтобы посмотреть доступные команды";
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
