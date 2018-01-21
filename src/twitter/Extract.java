/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.*;
import java.time.Instant;
import java.util.regex.*;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     *
     * O(n) for n = size of tweet list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {

        if (tweets.isEmpty()) {
            Instant now = Instant.now();
            return new Timespan(now, now);
        }

        Instant minInstant = Instant.MAX;
        Instant maxInstant = Instant.MIN;

        for (Tweet tweet : tweets) {
            if (tweet.getTimestamp().isBefore(minInstant)) {
                minInstant = tweet.getTimestamp();
            }

            else if (tweet.getTimestamp().isAfter(maxInstant)) {
                maxInstant = tweet.getTimestamp();
            }
        }

        return new Timespan(minInstant, maxInstant);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     *
     *  O(n*l) for n = size of tweet list and l = words in tweet text * O(matcher)
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {

        Set<String> mentionedUsers = new HashSet<>();
        Pattern pattern = Pattern.compile("[^-A-Za-z0-9_]");

        for (Tweet tweet : tweets) {
            String[] words = tweet.getText().split(" ");
            for (String word : words) {
                String subWord = word.substring(1);
                Matcher matcher = pattern.matcher(subWord);
                if (matcher.find()) {
                    continue;
                }
                if (word.charAt(0) == '@') {
                    mentionedUsers.add(word.toLowerCase());
                }
            }
        }

        return mentionedUsers;
    }

}
