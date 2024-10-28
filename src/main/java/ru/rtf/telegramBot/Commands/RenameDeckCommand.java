package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

import java.util.NoSuchElementException;

/**
 * Класс команды переимоенования колоды /rename-deck (старое название) (новое название)
 */
public class RenameDeckCommand implements Command {

    /**
     * поле для отправки сообщений пользователю
     */
    private final SenderMessages senderMessages;
    /**
     * Соответствие пользователей и их колод
     */
    private final UserDecksData userDecksData;

    /**
     * Количество аргументов в сообщении пользователя
     * 1. сама команда (/rename-deck)
     * 2. старое название колоды
     * 3. новое название
     */
    private final int COUNT_ARGS = 3;

    public RenameDeckCommand(SenderMessages senderMessages, UserDecksData userDecksData) {
        this.senderMessages = senderMessages;
        this.userDecksData = userDecksData;
    }

    /**
     * выполнить команду
     *
     * @param chatId в каком чате выполнить
     * @param text   текст вызова команды
     */
    @Override
    public void execution(Long chatId, String text) {

        String[] words = text.split(" ");
        if (words.length < COUNT_ARGS) {
            senderMessages.sendMessage(chatId,
                    "Команда отменена. Команда должна соответствовать шаблону:\n /rename-deck <название колоды> <новое название>");
            return;
        }
        String oldName = words[1];
        String newName = words[2];

        //есть ли у пользователя вообще колоды
        if (heckingForEmptiness(chatId)) {
            //достать старую колоду (если она существует) и переименовать, если имя ещё не занято
            DeckManager deckManager = userDecksData.getUserDecks(chatId);
            try {
                deckManager.updateDeckName(oldName, newName);
            } catch (NoSuchElementException | IllegalArgumentException e) {
                senderMessages.sendMessage(chatId, e.getMessage());
                return;
            }
            //вывести пользователю сообщение об успешности переименования
            senderMessages.sendMessage(chatId, "Переименование " + oldName + " -> " + newName);
        }
    }

    /**
     * Проверяет, есть ли у пользователя колоды
     *
     * @return логический ответ
     */
    private boolean heckingForEmptiness(Long chatId) {
        DeckManager deckManager = userDecksData.getUserDecks(chatId);
        if (deckManager == null || deckManager.getDecks().isEmpty()) {
            senderMessages.sendMessage(chatId, "У Вас пока нет ни одной колоды, создайте первую (/create-deck <название>)");
            return false;
        }
        return true;
    }
}
