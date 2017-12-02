import twitter4j.*;

public class TwitterCalls {

    /**
     * Returns the amount of followers of a given user
     * @param screenName The Twitter handle of a user (i.e. "Logic301" or "IUBloomington")
     * @return The total amount of followers
     * @throws TwitterException
     */
    public static int getFollowers(String screenName) throws TwitterException {
        Twitter twitter = TwitterAuth.getTwitterInstance();
        User user = twitter.users().showUser(screenName);
        return user.getFollowersCount();
    }

    /**
     * Return the amount of followers of the client
     * @return The total amount of followers
     * @throws TwitterException
     */
    public static int getFollowers() throws TwitterException {
        Twitter twitter = TwitterAuth.getTwitterInstance();
        AccountSettings ac = twitter.getAccountSettings();
        String myScreenName = ac.getScreenName();
        User user = twitter.users().showUser(myScreenName);
        return user.getFollowersCount();
    }

    /**
     * Returns the amount of users that a given user is following
     * @param screenName The Twitter handle of a user (i.e. "Logic301" or "IUBloomington")
     * @return The amount of users that a given user is following
     * @throws TwitterException
     */
    public static int getFollowing(String screenName) throws TwitterException {
        Twitter twitter = TwitterAuth.getTwitterInstance();
        User user = twitter.users().showUser(screenName);
        return user.getFriendsCount();
    }

    /**
     * Return the amount of users that the client is following
     * @return The amount of users that a given user is following
     * @throws TwitterException
     */
    public static int getFollowing() throws TwitterException {
        Twitter twitter = TwitterAuth.getTwitterInstance();
        AccountSettings ac = twitter.getAccountSettings();
        String myScreenName = ac.getScreenName();
        User user = twitter.users().showUser(myScreenName);
        return user.getFriendsCount();
    }

    /**
     * Uploads a tweet containing the given message via the client's account
     * @param message The contents of the tweet. Character count must be within constraints of present Twitter guidelines.
     * @throws TwitterException
     */
    public static void makeTweet(String message) throws TwitterException {
        Twitter twitter = TwitterAuth.getTwitterInstance();
        twitter.updateStatus(message);
    }

    /**
     * Returns true if a given a given search query is associated with a verified account
     * @param query The name/entity that we are searching (i.e. "Chance the Rapper" or "Bobby Flay")
     * @return A boolean value determing if the query is associated with a verified account or not.
     * @throws TwitterException
     */
    public static String isTwitterUser(String query) throws TwitterException {
        Twitter twitter = TwitterAuth.getTwitterInstance();
        ResponseList<User> users = twitter.searchUsers(query, 1);
        for (User user : users) {
            if (user.isVerified()) {
                return "Yes. " + query + "'s Twitter handle is. At, " + user.getScreenName();
            }
        }
        return "No. " + query + " does not have a Twitter account.";
    }

    /**
     * Returns the top 5 trending topics on Twitter in the United States
     * @return A list of the trending topics in order from most to least popular
     * @throws TwitterException
     */
    public static String getTrendingTopics() throws TwitterException {
        Twitter twitter = TwitterAuth.getTwitterInstance();
        Trend[] trends = twitter.getPlaceTrends(23424977).getTrends();
        String trendingTopics = "";
        for (int i=0; i < 5; i++) {
            String topic = trends[i].getName();
            if (topic.contains("#")) {
                topic = trends[i].getName().substring(1);
            }
            if (i == 3) {
                trendingTopics += topic + ", and ";
            } else {
                trendingTopics += topic + ", ";
            }
        }
        return trendingTopics;
    }
}
