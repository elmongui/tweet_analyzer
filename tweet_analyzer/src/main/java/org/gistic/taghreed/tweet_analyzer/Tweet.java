package org.gistic.taghreed.tweet_analyzer;

import java.util.Date;


public class Tweet {
    public Long tweetId;
    public String text;
    public String language;
    public Double latitude;
    public Double longtitude;
    public Long retweetCount;
    public Long favoriteCount;
    public Date createdAt;
    public Long userId;
    public Long userFollowersCount;
    public String screenName;
    public String imageUrl;

    public String toString() {
        return text;
        // return userId + "\t" + screenName + "\t" + userFollowersCount + "\t" + createdAt + "\t" +
        // tweetId + "\t" + latitude + "\t" + longtitude + "\t" + language + "\t" + favoriteCount +
        // "\t" + retweetCount + "\t" + text;
    }
}
