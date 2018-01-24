/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.*;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("1968-02-17T08:00:00Z"); // Time before Epoch
    private static final Instant d4 = Instant.parse("2016-02-17T15:00:00Z");


    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is a  @rivest so @vane much @juan at the @nadie for", d1);
    private static final Tweet tweet2 = new Tweet(2, "mario", "@rivest talk  a  in 30 minutes @hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "vane", "rivest@gmail.com @talk in 30 @minutes @hype many @times @andres", d3);
    private static final Tweet tweet4 = new Tweet(4, "gaby", "rivest @ri%$#vest talk in 30 minutes #hype", d4);



    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    /*
     * Tests for guessFollowsGraph
     * 1. Empty tweet list
     * 2. Multiple tweets with invalid usernames
     */


    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsGraphMultipleTweets() {
        Map<String, Set<String>> followGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4));

        assertEquals("expected 4 users in alyssa's value", 4,followGraph.get("alyssa").size());
        assertEquals("expected 2 users in marios's value", 2,followGraph.get("mario").size());
        assertEquals("expected 2 users in vane's value", 5,followGraph.get("vane").size());
        assertEquals("expected 0 users in gaby's value", 0,followGraph.get("gaby").size());
    }

    /*
     * Tests for influencers
     * 1. Empty map
     * 2. Multiple tweets with invalid usernames
     */

    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    @Test
    public void testInfluencersMultipleTweets() {

        Map<String, Set<String>> followGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4));
        List<String> influencers = SocialNetwork.influencers(followGraph);

        List<String> expected = Arrays.asList("vane", "alyssa", "mario", "gaby");

        assertEquals(influencers, expected);

        System.out.println(influencers);
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
