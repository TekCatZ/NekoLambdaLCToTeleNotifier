package com.tekcatz.nekolctelebot.configuration;

import com.tekcatz.nekolctelebot.service.ILeetcodeService;
import com.tekcatz.nekolctelebot.service.ITelegramClientService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
@AllArgsConstructor
public class FunctionConfiguration {
    private ILeetcodeService leetcodeService;
    private ITelegramClientService telegramClientService;

    @Bean
    public Function<String, String> sendDailyLeetcodeNotification() {
        return input -> {
            var dailyData = leetcodeService.getDaily();
            dailyData.ifPresent((data) -> {
                telegramClientService.sendNotification(data);
            });
            return "OK!";
        };
    }
}
