package com.tekcatz.nekolctelebot.service;

import com.tekcatz.nekolctelebot.models.leetcode.LeetcodeDailyData;

public interface ITelegramClientService {
    void sendNotification(LeetcodeDailyData data);
}
