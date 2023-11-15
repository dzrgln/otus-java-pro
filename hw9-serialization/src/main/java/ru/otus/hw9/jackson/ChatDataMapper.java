package ru.otus.hw9.jackson;

import ru.otus.hw9.model.result.ChatData;
import ru.otus.hw9.model.result.ChatInfo;
import ru.otus.hw9.model.result.MessageInfo;
import ru.otus.hw9.model.source.ChatSession;
import ru.otus.hw9.model.source.Message;
import ru.otus.hw9.model.source.SmsData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ChatDataMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    public static ChatData of(SmsData smsData) {
        ChatData chatData = new ChatData();
        Map<String, ChatInfo> chatDataMap = chatData.getChatDataMap();
        for(ChatSession chatSession: smsData.getChatSessions()) {
            ChatInfo.ChatInfoBuilder chatInfoBuilder = ChatInfo.builder()
                    .chatIdentifier(chatSession.getChatIdentifier())
                    .lastMember(chatSession.getMembers().get(0).getLast());
            List<MessageInfo> messageInfos = new ArrayList<>();

            Set<String> belongNumbers = new HashSet<>();
            for(Message message: chatSession.getMessages()) {
               belongNumbers.add(message.getBelongNumber());
            }

            for(String belongNumber: belongNumbers) {
                chatInfoBuilder.belongNumber(belongNumber);
                for (Message message : chatSession.getMessages()) {
                    if (belongNumber.equalsIgnoreCase(message.getBelongNumber())) {
                        LocalDateTime sendDate = LocalDateTime.parse(message.getSendDate(), formatter);
                        MessageInfo messageInfo = new MessageInfo(sendDate, message.getText());
                        messageInfos.add(messageInfo);
                    }
                    List<MessageInfo> sortedMessageInfos = messageInfos.stream()
                            .sorted(Comparator.comparing(MessageInfo::getSendDate))
                            .toList();
                    chatInfoBuilder.messageInfos(sortedMessageInfos);
                    chatDataMap.put("_" + belongNumber.substring(1), chatInfoBuilder.build());
                }
            }
        }
        return chatData;
    }
}
