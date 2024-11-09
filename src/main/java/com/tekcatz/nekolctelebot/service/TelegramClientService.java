package com.tekcatz.nekolctelebot.service;

import com.tekcatz.nekolctelebot.models.leetcode.LeetcodeDailyData;
import com.tekcatz.nekolctelebot.models.leetcode.Question;
import com.tekcatz.nekolctelebot.models.leetcode.Tag;
import com.tekcatz.nekolctelebot.models.telegram.SendMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TelegramClientService implements ITelegramClientService {
    private static final String URL = "https://api.telegram.org/bot%s/sendMessage";
    private static final String PATTERN = "yyyy/MM/dd";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(PATTERN);
    private static final String LEETCODE_URL = "https://leetcode.com%s";
    private static final String TELEGRAM_BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");
    public static final String TELEGRAM_CHAT_ID = System.getenv("TELEGRAM_CHAT_ID");

    private WebClient webClient;


    public void sendNotification(LeetcodeDailyData data) {
        var body = createSendMessage(data);

        webClient
                .post()
                .uri(URL.formatted(TELEGRAM_BOT_TOKEN))
                .bodyValue(body)
                .exchangeToMono(res -> Mono.empty())
                .block();
    }

    private SendMessage createSendMessage(LeetcodeDailyData data) {
        var text = createMsg(data.getActiveDailyCodingChallengeQuestion());

        return SendMessage.builder()
                .protectContent(true)
                .parseMode("MarkdownV2")
                .chatId(TELEGRAM_CHAT_ID)
                .text(text)
                .build();
    }

    private String createMsg(Question data) {
        var today = ZonedDateTime.now(ZoneId.of("VST", ZoneId.SHORT_IDS));
        var title = "*Daily Leetcode %s*".formatted(today.format(DATE_TIME_FORMATTER));

        var question = data.getQuestion();
        var url = "*Link:* [%s](%s)".formatted(question.getTitle(), LEETCODE_URL.formatted(data.getLink()));
        var diff = "*Difficulty:* _%s_".formatted(question.getDifficulty());
        var tags = question.getTopicTags().stream()
                .map(Tag::getName)
                .distinct()
                .collect(Collectors.joining(", "));
        var tagDesc = "Tags: || %s ||".formatted(tags);

        return """
                %s
                %s
                %s
                %s
                """.formatted(title, url, diff, tagDesc);
    }
}
