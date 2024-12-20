package ru.rtf.telegramBot.commands;

import ru.rtf.DeckManager;
import ru.rtf.telegramBot.CommandHandler;

/**
 * Обработчик команды справки
 */
public class HelpCommandHandler implements CommandHandler {

    /**
     * Количество параметров команды
     * нет параметров
     */
    private static final int COUNT_PARAMS = 0;

    @Override
    public String handle(DeckManager usersDecks, Long chatId, String[] params) {
        String help = """
                Anki — это телеграм бот для изучения карточек - их термина и определения, которое может использоваться для изучения новых языков, памяти, подготовки к экзаменам и других целях.
                
                Операции с колодами:
                    Создание колоды:
                        /create_deck название
                    Переименование колоды:
                        /rename_deck название := новое_название
                    Удаление колоды:
                        /delete_deck название
                    Просмотр карточек колоды:
                        /list_cards название
                Операции с карточками:
                    Создание карточки:
                        /create_card название_колоды : термин = определение
                    Редактирование термина:
                        /edit_card_term название_колоды : термин := новое_название
                    Редактирование определения:
                        /edit_card_def название_колоды : термин = новое_определение
                    Удаление карточки:
                        /delete_card название_колоды : термин
                    Просмотр карточки:
                        /list_card название_колоды : термин
                Обучение:
                    Режим "соответствие":
                        /check match: название_колоды
                    Режим "ввод термина":
                        /check typing: название_колоды
                    Режим "карточки":
                        /check memory: название_колоды
                    Выход из любого режима обучения:
                        /end_check
                """;
        return help;
    }

    @Override
    public int getParamsCount() {
        return COUNT_PARAMS;
    }
}
