package ru.rtf.telegramBot.commands;

import ru.rtf.Card;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;
import ru.rtf.telegramBot.learning.LearningSession;
import ru.rtf.telegramBot.learning.SessionManager;
import ru.rtf.telegramBot.learning.mode.MatchLearning;
import ru.rtf.telegramBot.learning.mode.MemoryLearning;
import ru.rtf.telegramBot.learning.mode.TypingLearning;

import java.util.Collection;

/**
 * Обработчик команд относящихся к режимам обучения
 */
public class LearnCheckCommandHandler implements CommandHandler {
    /**
     * Количество параметров команды
     * 1.тип режима обучения
     * 2.имя колоды
     */
    private final static int COUNT_PARAMS = 2;
    /**
     * Менеджер сессий пользователе
     */
    private final SessionManager sessionManager;

    public LearnCheckCommandHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public String handle(DeckManager usersDecks, Long chatId, String[] params) {
        String typeCheck = params[0].toLowerCase();
        String deckName = params[1];
        try {
            Collection<Card> cardsToLearn = usersDecks.getDeck(deckName).getCards();

            LearningSession learningSession;
            switch (typeCheck) {
                case "match":
                    learningSession = new MatchLearning(cardsToLearn);
                    break;
                case "typing":
                    learningSession = new TypingLearning(cardsToLearn);
                    break;
                case "memory":
                    learningSession = new MemoryLearning(cardsToLearn);
                    break;
                default:
                    return MESSAGE_COMMAND_ERROR.formatted("Неизвестный режим обучения");
            }

            return sessionManager.start(chatId, learningSession);

        } catch (Exception exception) {
            return MESSAGE_COMMAND_ERROR.formatted(exception.getMessage());
        }
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
