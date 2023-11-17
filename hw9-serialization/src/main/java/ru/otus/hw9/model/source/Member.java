package ru.otus.hw9.model.source;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Member {
    private String first;
    @JsonAlias("handle_id")
    private int handleId;
    @JsonAlias("image_path")
    private String imagePath;
    private String last;
    private String middle;
    @JsonAlias("phone_number")
    private String phoneNumber;
    private String service;
    @JsonAlias("thumb_path")
    private String thumbPath;
}