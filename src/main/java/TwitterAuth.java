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
                .setOAuthConsumerKey("[Consumer Key]")
                .setOAuthConsumerSecret("[Consumer Secret]")
                .setOAuthAccessToken("[Access Token]")
                .setOAuthAccessTokenSecret("[Access Token Secret]");
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }
}
