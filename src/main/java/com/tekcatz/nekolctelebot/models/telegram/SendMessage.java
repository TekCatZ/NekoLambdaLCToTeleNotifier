package com.tekcatz.nekolctelebot.models.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendMessage {

    @JsonProperty("chat_id")
    private String chatId;

    @JsonProperty("text")
    private String text;

    @JsonProperty("protect_content")
    private Boolean protectContent;

    @JsonProperty("parse_mode")
    private String parseMode;
}
