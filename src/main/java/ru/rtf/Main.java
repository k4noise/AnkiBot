package ru.rtf;

import ru.rtf.telegramBot.TelegramBotCore;

/**
 * Главный класс для запуска приложения
 */
public class Main {
    /**
     * Главный метод для запуска приложения.
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args) {

        String telegramBotName = "telegram_botName";
        String telegramToken = System.getenv("telegram_token");
        new TelegramBotCore(telegramBotName, telegramToken)
                .start();
    }
}