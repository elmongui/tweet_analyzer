package org.gistic.taghreed.tweet_analyzer;

import java.util.Comparator;

public class MostFollowedPeopleComparator implements Comparator<UserData> {

    public int compare(UserData userData0, UserData userData1) {
        Long val0 = userData0.getMaxFollowedCount();
        Long val1 = userData1.getMaxFollowedCount();
        return val1.compareTo(val0); // descending
    }

}
