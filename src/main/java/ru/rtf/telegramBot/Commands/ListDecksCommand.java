package ru.rtf.telegramBot.Commands;

import ru.rtf.Deck;
import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

/**
 * класс просмотра всех колод /list-decks
 */
public class ListDecksCommand implements Command {

    /**
     * поле для отправки сообщений пользователю
     */
    private final SenderMessages senderMessages;
    /**
     * Соответствие пользователей и их колод
     */
    private final UserDecksData userDecksData;
    /**
     * Количество параметров команды
     * нет параметров
     */
    public final int COUNT_PARAMS = 0;

    public ListDecksCommand(SenderMessages senderMessages, UserDecksData userDecksData) {
        this.senderMessages = senderMessages;
        this.userDecksData = userDecksData;
    }

    /**
     * выполнить команду
     *
     * @param chatId идентификатор чата
     * @param params параметры команды без ее имени
     */
    @Override
    public void execution(Long chatId, String[] params) {
        DeckManager userDeckManager = userDecksData.getUserDecks(chatId);

        //сообщение пользователю о выполнении
        //TODO
        String decksText = LineNamesDecks(new DeckManager());//исправить
        senderMessages.sendMessage(chatId, "Ваши колоды:\n"+decksText);
    }

    /**
     * Создает строку из имен колод
     *
     * @return имена колод через перевод строки
     */
    private String LineNamesDecks(DeckManager deckManager) {
        StringBuilder names = new StringBuilder();
        for (Deck deck : deckManager.getDecks()) {
            names.append(deck.getName()).append("\n");
        }
        return names.toString();
    }

    /**
     * Возвращает количество параметров нужных команде для выполнения
     *
     * @return количество параметров
     */
    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }

//    /**
//     * Проверяет есть ли у пользователя колоды
//     *
//     * @return логический ответ
//     */
//    private boolean heckingForEmptiness(Long chatId) {
//        DeckManager deckManager = userDecksData.getUserDecks(chatId);
//        if (deckManager == null || deckManager.getDecks().isEmpty()) {
//            senderMessages.sendMessage(chatId, "У Вас пока нет ни одной колоды, создайте первую (/create-deck <название>)");
//            return false;
//        }
//        return true;
//    }
}
