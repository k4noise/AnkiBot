package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;

/**
 * Класс команды /start
 * обычно используется при начале диалога с ботом
 * выводит приветственное сообщение
 */
public class StartCommand implements Command {

    /**
     * Количество параметров команды
     * нет параметров
     */
    private final int COUNT_PARAMS = 0;

    @Override
    public String execution(DeckManager usersDecks, String[] params) {
        return "Добро пожаловать в AnkiBot. Введите команду /help, чтобы посмотреть доступные команды";
    }

    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
