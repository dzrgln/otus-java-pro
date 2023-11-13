package ru.otus.hw9.model.result;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class ChatInfo {
    public ChatInfo() {
    }

    private String chatIdentifier;
    private String lastMember;
    private String belongNumber;
    private List<MessageInfo> messageInfos;

    public String messageInfoAsString() {
        StringBuilder sb = new StringBuilder();
        for(MessageInfo messageInfo: messageInfos) {
            sb.append(messageInfo);
            sb.append("||");
        }
        return sb.toString();
    }
}
