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
                .setOAuthConsumerKey("1nQqPv2NLVEb8wOS6mDGPBS4y")
                .setOAuthConsumerSecret("Tc95XZsd6iKZoQTYhJNkbzM1GwgI3E5PJFXsCCNuxcssdhldbL")
                .setOAuthAccessToken("88403815-OH4bD6UoXrXzjieUguflqBxtGChhVs3hYENxgNVJ")
                .setOAuthAccessTokenSecret("MrarnpbqXur1tO0FHloDdePYhUoGk3CzTMlyyJyw0spvW");
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }
}
