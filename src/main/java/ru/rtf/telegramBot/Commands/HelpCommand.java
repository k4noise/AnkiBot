package ru.rtf.telegramBot.Commands;

import ru.rtf.telegramBot.Command;
import ru.rtf.telegramBot.SenderMessages;

/**
 * Класс команды /help
 */
public class HelpCommand implements Command {
    /**
     * Поле для отправки сообщений пользователю
     */
    private final SenderMessages senderMessages;

    /**
     * Количество параметров команды
     * нет параметров
     */
    private final int COUNT_PARAMS = 0;

    /**
     * Создание экземпляра команды для добавления новой карты
     *
     * @param senderMessages может отправлять сообщения
     */
    public HelpCommand(SenderMessages senderMessages) {
        this.senderMessages = senderMessages;
    }

    @Override
    public void execution(Long chatId, String[] params) {
        String help = """
                Anki — это телеграм бот для изучения карточек - их термина и определения, которое может использоваться для изучения новых языков, памяти, подготовки к экзаменам и других целях.
                
                Операции с колодами:
                    Создание колоды: /create-deck название
                    Переименование колоды: /rename-deck название := новое_название
                    Удаление колоды: /delete-deck название
                    Просмотр карточек колоды: /list-cards название
                Операции с карточками:
                    Создание карточки: /create-card название_колоды : термин = определение
                    Редактирование термина: /edit-card-term название_колоды : термин := новое_название
                    Редактирование определения: /edit-card-def название_колоды : термин = новое_определение
                    Удаление карточки: /delete-card название_колоды : термин
                    Просмотр карточки: /list-card название_колоды : термин
                """;
        senderMessages.sendMessage(chatId, help);
    }

    @Override
    public int getCountParams() {
        return COUNT_PARAMS;
    }
}
