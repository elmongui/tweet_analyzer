package org.gistic.taghreed.tweet_analyzer;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

public class Analyzer {

    private static PrintStream out = System.out;
    private static String PATH = "/Users/elmongui/projects/tweet_analyzer/output.txt";
    private static String NEW_REQUEST =
            "http://10.10.10.28:3000/taghreed-webservices/query_new?keywords=&bb=16,34,33,56&start=2015-10-01&end=2015-10-31&query_type=Snapshot&pushed_predicates={}&limit=1000&session_id=";
    private static String NEXT_REQUEST =
            "http://10.10.10.28:3000/taghreed-webservices/query_next?limit=1000&session_id=";
    private HashMap<Long, UserData> data = new HashMap<Long, UserData>();

    public void run() throws Exception {
        InputStream input;

        // out = new PrintStream(new FileOutputStream(PATH, true), true, "UTF-8");
        int sessionId = new Random().nextInt(100000);

        int index = 0;
        String request = NEW_REQUEST;
        while (true) {
            input = new URL(request + sessionId).openStream();
            JSONObject json = JSON.inputStreamToJSON(input);
            JSONArray jsonArray = json.getJSONArray("tweets");
            List<Tweet> tweets = JSON.scanJSON(jsonArray);

            for (Tweet tweet : tweets) {
                UserData userData = data.get(tweet.userId);
                if (userData == null) {
                    userData = new UserData(tweet.userId);
                    data.put(userData.userId, userData);
                }
                userData.add(tweet);
            }

            System.err.println(index++);
            if (index > 5)
                break;
            if ((Integer) json.get("tweetsCount") == 0)
                break;

            request = NEXT_REQUEST;
        }

        investigate();

    }


    private void investigate() {
        UserData[] people = data.values().toArray(new UserData[0]);
        out.println(data.size() + " users");
        activePeople(people);
        mostFollowedPeople(people);
    }


    private void activePeople(UserData[] people) {
        out.println("Active People");

        Arrays.sort(people, new ActivePeopleComparator());
        for (int i = 0; i < 8; i++) {
            UserData userData = people[i];
            out.println(userData.userId + "\t" + userData.getScreenName() + "\t"
                    + userData.getTweetCount());
        }
    }


    private void mostFollowedPeople(UserData[] people) {
        out.println("Most Followed People");

        Arrays.sort(people, new MostFollowedPeopleComparator());
        for (int i = 0; i < 8; i++) {
            UserData userData = people[i];
            out.println(userData.userId + "\t" + userData.getScreenName() + "\t"
                    + userData.getMaxFollowedCount());
        }
    }


}
