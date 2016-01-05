package org.gistic.taghreed.tweet_analyzer;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

public class AnalyzerHajjImages {

    private static PrintStream out = System.out;
    private static String PATH = "/Users/elmongui/projects/tweet_analyzer/output.txt";
    private static String NEW_REQUEST_HARAM =
            "http://10.10.10.28:3000/taghreed-webservices/query_new?keywords=&bb=21.4164,39.8196,21.4272,39.8304&start=2015-09-22&end=2015-09-27&query_type=Snapshot&pushed_predicates={}&limit=1000&session_id=";
    private static String NEW_REQUEST_MINA =
            "http://10.10.10.28:3000/taghreed-webservices/query_new?keywords=&bb=21.4,39.8772,21.43,39.91&start=2015-09-22&end=2015-09-27&query_type=Snapshot&pushed_predicates={}&limit=1000&session_id=";
    private static String NEW_REQUEST_ARAFAT =
            "http://10.10.10.28:3000/taghreed-webservices/query_new?keywords=&bb=21.33,39.95,21.385,40&start=2015-09-22&end=2015-09-27&query_type=Snapshot&pushed_predicates={}&limit=1000&session_id=";
    private static String NEW_REQUEST_MOZDALEFA =
            "http://10.10.10.28:3000/taghreed-webservices/query_new?keywords=&bb=21.375,39.9,21.4,39.93&start=2015-09-22&end=2015-09-27&query_type=Snapshot&pushed_predicates={}&limit=1000&session_id=";
    private static String NEW_REQUEST_JAMARAT =
            "http://10.10.10.28:3000/taghreed-webservices/query_new?keywords=&bb=21.4182,39.8664,21.4236,39.8772&start=2015-09-22&end=2015-09-27&query_type=Snapshot&pushed_predicates={}&limit=1000&session_id=";
    private static String NEXT_REQUEST =
            "http://10.10.10.28:3000/taghreed-webservices/query_next?limit=1000&session_id=";
    private HashMap<Long, UserData> data = new HashMap<Long, UserData>();

    public void run() throws Exception {
        InputStream input;

        // out = new PrintStream(new FileOutputStream(PATH, true), true, "UTF-8");

        Map<String, String> requests = new HashMap<String, String>();
        requests.put("HARAM", NEW_REQUEST_HARAM);
        requests.put("MINA", NEW_REQUEST_MINA);
        requests.put("ARAFAT", NEW_REQUEST_ARAFAT);
        requests.put("MOZDALEFA", NEW_REQUEST_MOZDALEFA);
        requests.put("JAMARAT", NEW_REQUEST_JAMARAT);
        
        for(Entry<String, String> entry : requests.entrySet())
        {
            String region = entry.getKey();
            String request = entry.getValue();
            int sessionId = new Random().nextInt(100000);

            List<Tweet> tweets = new ArrayList<Tweet>();

            int index = 0;
            while (true) {
                input = new URL(request + sessionId).openStream();
                JSONObject json = JSON.inputStreamToJSON(input);
                JSONArray jsonArray = json.getJSONArray("tweets");
                List<Tweet> response = JSON.scanJSON(jsonArray);
                if(response.size() == 0)
                    break;

                for (Tweet tweet : response) {
                    if(tweet.imageUrl.contains("media"))
                    {
                        tweets.add(tweet);
//                        System.err.println(region + "\t" + tweet.createdAt.getTime() + "\t" + tweet.imageUrl);
                    }
                    //System.err.println(tweet.text);
                }
                //tweets.addAll(response);

                request = NEXT_REQUEST;
            }
            
            Tweet[] tweetArray = tweets.toArray(new Tweet[0]);
            Arrays.sort(tweetArray, new Comparator<Tweet>() {
                public int compare(Tweet tweet0, Tweet tweet1) {
                    return tweet0.createdAt.compareTo(tweet1.createdAt);
                }});
            for (Tweet tweet : tweetArray) {
                System.err.println("curl -o " + tweet.createdAt.getTime() + "_" + region + ".jpg " + tweet.imageUrl);
            }

//            System.out.println(region + " ...... " + tweets.size());

        }   
    }


    private void investigate() {
    }

}
