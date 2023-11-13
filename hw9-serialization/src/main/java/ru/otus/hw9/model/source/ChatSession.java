package ru.otus.hw9.model.source;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ChatSession {
    @JsonAlias("chat_id")
    private int chatId;
    @JsonAlias("chat_identifier")
    private String chatIdentifier;
    @JsonAlias("display_name")
    private String displayName;
    @JsonAlias("is_deleted")
    private int isDeleted;
    private List<Member> members;
    private List<Message> messages;
}