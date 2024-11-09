package com.tekcatz.nekolctelebot.service;

import com.tekcatz.nekolctelebot.models.leetcode.LeetcodeDailyData;

import java.util.Optional;

public interface ILeetcodeService {
    Optional<LeetcodeDailyData> getDaily();
}
