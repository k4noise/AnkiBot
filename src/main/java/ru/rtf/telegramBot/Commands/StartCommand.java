package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;

/**
 * Команда /start
 * выводит приветственное сообщение
 */
public class StartCommand implements Command {

    /**
     * Количество параметров команды
     * нет параметров
     */
    private final int COUNT_PARAMS = 0;

    @Override
    public String execute(DeckManager usersDecks, String[] params) {
        return "Добро пожаловать в AnkiBot. Введите команду /help, чтобы посмотреть доступные команды";
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
