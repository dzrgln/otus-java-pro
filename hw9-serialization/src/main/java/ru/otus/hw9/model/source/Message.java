package ru.otus.hw9.model.source;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Message {
    @JsonAlias("ROWID")
    private int rowId;
    private String attributedBody;
    @JsonAlias("belong_number")
    private String belongNumber;
    private long date;
    @JsonAlias("date_read")
    private long dateRead;
    private String guid;
    @JsonAlias("handle_id")
    private int handleId;
    @JsonAlias("has_dd_results")
    private int hasDdResults;
    @JsonAlias("is_deleted")
    private int isDeleted;
    @JsonAlias("is_from_me")
    private int isFromMe;
    @JsonAlias("send_date")
    private String sendDate;
    @JsonAlias("send_status")
    private int sendStatus;
    private String service;
    private String text;
}