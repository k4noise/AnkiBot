package ru.rtf.telegramBot.commands;

import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;
import ru.rtf.telegramBot.StatsCalculator;

/**
 * Обработчик команды получения общей статистики по всем колодам пользователя
 * <p>/stats</p>
 */
public class StatsCommandHandler implements CommandHandler {

    /**
     * Количество параметров команды
     * нет параметров
     */
    private static final int COUNT_PARAMS = 0;

    /**
     * Калькулятор статистики
     */
    private final StatsCalculator statsCalculator = new StatsCalculator();

    @Override
    public String handle(DeckManager usersDecks, Long chatId, String[] params) {

        if (usersDecks.getDecks().isEmpty())
            return "У Вас пока нет ни одной колоды, создайте первую /create_deck <название>";

        StringBuilder decksAndPercentages = new StringBuilder();
        for (Deck deck : usersDecks.getDecks()) {
            int percent = statsCalculator.getDeckLearningPercentage(deck);
            String deckProgressPercent = "'%s':\t%d%%\n".formatted(deck.getName(), percent);
            //'DeckName':   percent%
            decksAndPercentages.append(deckProgressPercent);
        }
        return "Прогресс изучения колод в процентах:\n" + decksAndPercentages;
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
