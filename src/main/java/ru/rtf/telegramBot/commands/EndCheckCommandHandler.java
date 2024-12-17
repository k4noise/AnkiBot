package ru.rtf.telegramBot.commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;
import ru.rtf.telegramBot.StatsCalculator;
import ru.rtf.telegramBot.learning.AnswerStatus;
import ru.rtf.telegramBot.learning.LearningSession;
import ru.rtf.telegramBot.learning.SessionManager;

import java.util.Map;
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
    /**
     * Калькулятор статистики
     */
    private final StatsCalculator statsCalculator;

    public EndCheckCommandHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.statsCalculator = new StatsCalculator();
    }

    @Override
    public String handle(DeckManager userDecks, Long chatId, String[] params) {
        try {
            LearningSession session = sessionManager.get(chatId);
            sessionManager.end(chatId);
            Map<AnswerStatus, Integer> rawStats = session.getStats();
            return """
                    Вы досрочно завершили сессию
                    Вы помните %d%% терминов из показанных
                    """.formatted(statsCalculator.getSuccessLearningPercentage(rawStats));
        } catch (NoSuchElementException exception) {
            return MESSAGE_COMMAND_ERROR.formatted(exception.getMessage());
        }
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
