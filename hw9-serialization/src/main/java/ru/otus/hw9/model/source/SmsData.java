package ru.otus.hw9.model.source;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class SmsData {
    @JsonAlias("chat_sessions")
    private List<ChatSession> chatSessions;
}
