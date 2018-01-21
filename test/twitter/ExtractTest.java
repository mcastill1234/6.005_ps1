/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */


    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("1968-02-17T08:00:00Z"); // Time before Epoch
    private static final Instant d4 = Instant.parse("2016-02-17T15:00:00Z");

    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about @rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "@rivest talk in 30 minutes @hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "bbitdiddle", "rivest@gmail.com talk in 30 minutes #hype", d3);
    private static final Tweet tweet4 = new Tweet(4, "bbitdiddle", "rivest talk in 30 minutes #hype", d4);
    private static final Tweet tweet5 = new Tweet(5, "bbitdiddle", "@ri%$#vest talk in 30 minutes #hype", d4);
    private static final Tweet tweet6 = new Tweet(6, "bbitdiddle", "@riv-est talk in 30 minutes @Riv-EsT", d4);

    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }


    /*
    Test for getTimespan method:
    Partitions:
        1. Empty tweet
        2. Tweet with equal timestamps (tested with TwoTweets)
        3. Tweet with more tweets and time before the Epoch (sign change for instant)
     */

    @Test
    public void testGetTimespanEmptyTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList());

        assertEquals("expected start = end", timespan.getStart(), timespan.getEnd());
    }

    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }

    @Test
    public void testGetTimespanMoreTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4));

        assertEquals("expected start", d3, timespan.getStart());
        assertEquals("expected end", d4, timespan.getEnd());
    }

    /*
    Test for getMentionedUsers
    Partitions on tweets:
        1. Tweet with email or no mentioned user (use tweet1 or tweet3)
        2. Tweet with 1 mentioned user
        3. Tweet with more mentioned users
        4. Empty tweet list
        5. Tweet with invalid username
        6. Tweet with same user lower and upper case
     */
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    @Test
    public void testGetMentionedUsersOneUser() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2, tweet3, tweet4));

        assertThat(mentionedUsers, hasItem("@rivest"));
    }

    @Test
    public void testGetMentionedUsersMoreUsers() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet6));

        assertThat(mentionedUsers, hasItems("@rivest", "@hype", "@riv-est"));
    }

    @Test
    public void testGetMentionedUsersInvalidName() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));

        assertThat(mentionedUsers, not(hasItem("@ri%$#vest")));
    }

    @Test
    public void testGetMentionedUsersUpLowCase() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6));

        assertEquals("Lenght 1 array", mentionedUsers.size(), 1);
    }
    

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
