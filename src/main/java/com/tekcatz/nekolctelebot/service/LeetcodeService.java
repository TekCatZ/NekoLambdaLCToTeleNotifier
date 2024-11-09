package com.tekcatz.nekolctelebot.service;

import com.tekcatz.nekolctelebot.models.leetcode.LeetcodeDailyData;
import com.tekcatz.nekolctelebot.models.ResponseWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class LeetcodeService implements ILeetcodeService {
    private static final String query = """
            query questionOfToday {
            	activeDailyCodingChallengeQuestion {
            		date
            		userStatus
            		link
            		question {
            			acRate
            			difficulty
            			freqBar
            			frontendQuestionId: questionFrontendId
            			isFavor
            			paidOnly: isPaidOnly
            			status
            			title
            			titleSlug
            			hasVideoSolution
            			hasSolution
            			topicTags {
            				name
            				id
            				slug
            			}
            		}
            	}
            }
            """;
    private static final String url = "https://leetcode.com/graphql";

    private WebClient webClient;

    @Override
    public Optional<LeetcodeDailyData> getDaily() {
        return getData(new ParameterizedTypeReference<>() {});
    }

    private Optional<LeetcodeDailyData> getData(ParameterizedTypeReference<ResponseWrapper<LeetcodeDailyData>> typeRef) {
        try {
            var entity = webClient
                    .post()
                    .uri(url)
                    .header("Content-Type", "application/json")
                    .bodyValue(Collections.singletonMap("query", query))
                    .exchangeToMono(response -> response.bodyToMono(typeRef))
                    .block();

            if (entity == null) {
                return Optional.empty();
            }

            var body = entity.getData();
            return body != null ? Optional.of(body) : Optional.empty();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return Optional.empty();
    }
}
