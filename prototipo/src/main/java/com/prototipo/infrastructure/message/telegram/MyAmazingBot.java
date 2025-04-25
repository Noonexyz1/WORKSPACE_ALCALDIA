package com.prototipo.infrastructure.message.telegram;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;


@Slf4j
@Component
public class MyAmazingBot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient = new OkHttpTelegramClient("7903334844:AAEGWODZKlRWz4arXig9njLupzNqiB8MA4A");

    @Override
    public void consume(Update update) {
        // Buttons are wrapped in lists since each keyboard is a set of button rows
        List<InlineKeyboardRow> row = new ArrayList<>();
        row.add(new InlineKeyboardRow(InlineKeyboardButton
                .builder()
                .text("Opcion 1")
                .callbackData("idSolicitud")
                .build()));
        row.add(new InlineKeyboardRow(InlineKeyboardButton
                .builder()
                .text("Opcion 2")
                .callbackData("idSolicitud2")
                .build()));

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(row);

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            var msg = update.getMessage();
            var user = msg.getFrom();
            var id = user.getId();

            if (msg.getText().equals("/iniciar") ||
                    msg.getText().equals("iniciar") ||
                    msg.getText().equals("Iniciar")) {

                enviarMenu(id.toString(), "Bienvenid@ - Elige una opcion:", keyboardMarkup);
            }

        } else if (update.hasCallbackQuery()) {
            // Set variables
            String callData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callData.equals("idSolicitud")) {
                enviarMenuRespuesta(chatId, messageId);
            }
            if (callData.equals("idSolicitud2")) {
                enviarMenuRespuesta(chatId, messageId);
            }

        }

        /*Map<String, BiConsumer<Long, Long>> maps = new HashedMap<>();
        maps.put("comando1", this::enviarMenuRespuesta);

        maps.get("asdf").accept(23L, 234L);*/

    }

    @SneakyThrows
    private void enviarMenuRespuesta(long chatId, long messageId) {
        String answer = "Mensaje actualizado";
        EditMessageText newMessage = EditMessageText.builder()
                .chatId(chatId)
                .messageId(toIntExact(messageId))
                .text(answer)
                .build();
        try {
            telegramClient.execute(newMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public void enviarMenu(String who, String txt, InlineKeyboardMarkup kb) {
        SendMessage sm = SendMessage.builder()
                .chatId(who)
                .parseMode("HTML")
                .text(txt)
                .replyMarkup(kb)
                .text(txt)
                .build();

        try {
            telegramClient.execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public void saludo(String who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who) //Who are we sending a message to, osea el chat del usuario o el USUARIO directamente
                .text("Hola jeje")
                .build();    //Message content
        try {
            telegramClient.execute(sm);         //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }

    @SneakyThrows
    public void replicarMensaje(String who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who) //Who are we sending a message to, osea el chat del usuario o el USUARIO directamente
                .text(what)
                .build();    //Message content
        try {
            telegramClient.execute(sm);         //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }
}
