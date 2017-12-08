import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAuth {

    /**
     * Creates an instance for the client given accurate authentication credentials
     * @return A Twitter instance
     */
    public static Twitter getTwitterInstance() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("[Your Consumer Key Here]")
                .setOAuthConsumerSecret("[Your Consumer Key Secret Here]")
                .setOAuthAccessToken("[Your Access Token Here]")
                .setOAuthAccessTokenSecret("[Your Access Token Secret Here]");
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }
}
