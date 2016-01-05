package org.gistic.taghreed.tweet_analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class UserData {
    Long userId;
    private List<Tweet> tweets;
    private Map<String, Integer> languages;
    private Long maxFollowedCount;


    public UserData(Long userId) {
        this.userId = userId;
        this.tweets = new ArrayList<Tweet>();
        this.languages = new HashMap<String, Integer>();
        this.maxFollowedCount = (long) 0;
    }


    public void add(Tweet tweet) {
        tweets.add(tweet);

        Integer languageCount = languages.get(tweet.language);
        if (languageCount == null) {
            languageCount = 0;
        }
        languages.put(tweet.language, languageCount + 1);

        maxFollowedCount = Math.max(maxFollowedCount, tweet.userFollowersCount);
    }


    public String getScreenName() {
        return (getTweetCount() == 0) ? "N/A" : tweets.get(0).screenName;
    }


    public int getTweetCount() {
        return tweets.size();
    }


    public Long getMaxFollowedCount() {
        return maxFollowedCount;
    }


    public String getMostWrittenLanguage() {
        List<Entry<String, Integer>> languageCountList =
                new ArrayList<Entry<String, Integer>>(languages.entrySet());
        Collections.sort(languageCountList, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        return languageCountList.get(0).getKey();
    }
}
