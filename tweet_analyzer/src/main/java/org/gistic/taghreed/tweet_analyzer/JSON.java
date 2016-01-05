package org.gistic.taghreed.tweet_analyzer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSON {
    public static List<Tweet> scanJSON(JSONArray jsonArray) throws Exception {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Tweet tweet = scanJSON(jsonObject);
            tweets.add(tweet);
            //System.err.println(tweet);
        }

        return tweets;
    }


    public static Tweet scanJSON(JSONObject jsonObject) throws Exception {
        Tweet tweet = new Tweet();
        tweet.tweetId = jsonObject.getLong("tweet_id");
        tweet.text = jsonObject.getString("text");
        tweet.language = jsonObject.getString("lang");
        tweet.latitude = jsonObject.getDouble("lat");
        tweet.longtitude = jsonObject.getDouble("long");
        tweet.retweetCount = jsonObject.getLong("retweet_count");
        tweet.favoriteCount = jsonObject.getLong("favorite_count");
        tweet.createdAt = string2Date(jsonObject.getString("created_at"));
        tweet.userId = jsonObject.getLong("user_id");
        tweet.userFollowersCount = jsonObject.getLong("user_followers_count");
        tweet.screenName = jsonObject.getString("screen_name");
        tweet.imageUrl = jsonObject.getString("image_url");
        return tweet;
    }


    public static JSONObject inputStreamToJSON(InputStream stream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null)
            builder.append(line);

        return new JSONObject(builder.toString());
    }

    
    private final static String dateSeparator = "-";
    private final static String dateTimeSeparator = " ";
    private final static String timeSeparator = ":";
    private static Date string2Date(String dateStr)
    {
        String[] dateTimeElements = dateStr.split(dateTimeSeparator);
        String[] dateElements = dateTimeElements[0].split(dateSeparator);
        String[] timeElements = dateTimeElements[1].split(timeSeparator);

        int year = Integer.parseInt(dateElements[0]);
        int month = Integer.parseInt(dateElements[1]) - 1;
        int day = Integer.parseInt(dateElements[2]);
        int hourOfDay = Integer.parseInt(timeElements[0]);
        int minute = Integer.parseInt(timeElements[1]);
        int second = Integer.parseInt(timeElements[2]);

        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calendar.set(year, month, day, hourOfDay, minute, second);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}
