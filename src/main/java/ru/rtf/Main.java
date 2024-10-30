package ru.rtf;

import ru.rtf.telegramBot.TelegramBotCore;

public class Main {
    public static void main(String[] args) {

        String telegramBotName = "AnkiSimpleBot";
        String telegramToken = System.getenv("telegram_token");
        new TelegramBotCore(telegramBotName, telegramToken)
                .start();
    }
}