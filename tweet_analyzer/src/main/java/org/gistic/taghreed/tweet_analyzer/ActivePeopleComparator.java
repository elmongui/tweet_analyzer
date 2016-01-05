package org.gistic.taghreed.tweet_analyzer;

import java.util.Comparator;

public class ActivePeopleComparator implements Comparator<UserData> {

    public int compare(UserData userData0, UserData userData1) {
        Integer val0 = userData0.getTweetCount();
        Integer val1 = userData1.getTweetCount();
        return val1.compareTo(val0); // descending
    }

}
