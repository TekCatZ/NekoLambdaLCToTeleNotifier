package com.tekcatz.nekolctelebot.models.leetcode;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionDetail {
    Double acRate;
    String difficulty;
    String frontendQuestionId;
    String title;
    List<Tag> topicTags = new ArrayList<>();
}
