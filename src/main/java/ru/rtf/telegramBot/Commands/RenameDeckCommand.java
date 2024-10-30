package ru.rtf.telegramBot.Commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;
import ru.rtf.telegramBot.UserDecksData;

import java.util.NoSuchElementException;

/**
 * Класс команды переименования колоды /rename-deck (старое название) (новое название)
 */
public class RenameDeckCommand implements Command {

    /**
     * Поле для отправки сообщений пользователю
     */
    private final SenderMessages senderMessages;
    /**
     * Соответствие пользователей и их колод
     */
    private final UserDecksData userDecksData;
    /**
     * Количество параметров команды
     * 1.старое имя колоды
     * 2.новое имя колоды
     */
    private final int COUNT_PARAMS = 2;

    /**
     * Создание экземпляра команды для добавления новой карты
     *
     * @param senderMessages может отправлять сообщения
     * @param userDecksData  может получать колоды пользователя
     */
    public RenameDeckCommand(SenderMessages senderMessages, UserDecksData userDecksData) {
        this.senderMessages = senderMessages;
        this.userDecksData = userDecksData;
    }

    @Override
    public void execution(Long chatId, String[] params) {
        DeckManager userDeckManager = userDecksData.getUserDecks(chatId);

        //обработка параметров
        String oldDeckName = params[0];
        String newDeckName = params[1];

        //попытка изменить имя колоды
        //TODO изменить реализацию
        try {
            userDeckManager.updateDeckName(oldDeckName, newDeckName);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            senderMessages.sendMessage(chatId, e.getMessage());
            return;
        }
        //сообщение пользователю о выполнении
        senderMessages.sendMessage(chatId, "Переименование " + oldDeckName + " -> " + newDeckName);
    }

    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
