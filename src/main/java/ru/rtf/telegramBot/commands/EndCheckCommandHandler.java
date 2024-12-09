package ru.rtf.telegramBot.commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;
import ru.rtf.telegramBot.learning.SessionManager;

import java.util.NoSuchElementException;

/**
 * Обработчик команды досрочного завершения обучения
 */
public class EndCheckCommandHandler implements CommandHandler {
    /**
     * Количество параметров команды
     * нет параметров
     */
    private static final int COUNT_PARAMS = 0;

    /**
     * Менеджер сессий пользователей
     */
    private final SessionManager sessionManager;

    public EndCheckCommandHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public String handle(DeckManager userDecks, Long chatId, String[] params) {
        try {
            sessionManager.end(chatId);
            return "Вы досрочно завершили сессию";
        } catch (NoSuchElementException exception) {
            return MESSAGE_COMMAND_ERROR.formatted(exception.getMessage());
        }
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
