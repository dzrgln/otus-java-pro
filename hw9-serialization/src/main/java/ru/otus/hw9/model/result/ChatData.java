package ru.otus.hw9.model.result;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ChatData {
    private final Map<String, ChatInfo> chatDataMap = new HashMap<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, ChatInfo> entry: chatDataMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append(" - ");
            sb.append(entry.getValue().toString());
            sb.append("\n");
        }
        return sb.substring(0, sb.toString().length() - 1);
    }
}
