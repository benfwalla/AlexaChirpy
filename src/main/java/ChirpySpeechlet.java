import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SsmlOutputSpeech;
import twitter4j.TwitterException;

public class ChirpySpeechlet implements SpeechletV2 {


    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> speechletRequestEnvelope) { }

    /**
     * Returns Alexa's output when initially opening the skill.
     * @param speechletRequestEnvelope Client input (i.e. "Alexa, open Chirpy")
     * @return Alexa's literal speech output
     */
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> speechletRequestEnvelope) {
        String welcomeMessage = "Welcome to Alexa Chirpy. What would you like to do on Twitter?";

        String repromptMessage = "With Alexa Chirp, you can send a tweet, ask for your follower or " +
                "following count, get the latest trending topics, or see if a celebrity has a Twitter account." +
                "What would you like to do?";

        return newAskResponse(welcomeMessage, false, repromptMessage, false);
    }

    /**
     * Returns Alexa's output upon various intents that were program into the skill
     * @param speechletRequestEnvelope Intent trigger (i.e. "how many followers do I have" or "does Taylor Swift has a Twitter")
     * @return Alexa's literal speech output
     */
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> speechletRequestEnvelope) {

        String intentName = speechletRequestEnvelope.getRequest().getIntent().getName();

        if ("AMAZON.HelpIntent".equals(intentName)) {
            String textMessage = "With Alexa Chirp, you can send a tweet, ask for your follower or " +
                    "following count, get the latest trending topics, or see if a celebrity has a Twitter account." +
                    "What would you like to do?";

            String repromptMessage = "What would you like to do on Twitter?";

            return newAskResponse(textMessage, false, repromptMessage, false);
        }
        else if ("AMAZON.StopIntent".equals(intentName) || "AMAZON.CancelIntent".equals(intentName)) {
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText("Thank you for using Alexa Chirpy. Goodbye.");

            return SpeechletResponse.newTellResponse(outputSpeech);
        }
        else if ("MyFollowersIntent".equals(intentName)) {
            try {
                int followerCount = TwitterCalls.getFollowers();

                PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
                outputSpeech.setText("You have " + followerCount + " followers.");

                return SpeechletResponse.newTellResponse(outputSpeech);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
        else if ("MyFollowingIntent".equals(intentName)) {
            try {
                int followingCount = TwitterCalls.getFollowing();

                PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
                outputSpeech.setText("You follow " + followingCount + " users.");

                return SpeechletResponse.newTellResponse(outputSpeech);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
        else if ("TweetIntent".equals(intentName)) {
            Slot tweetVariable = speechletRequestEnvelope.getRequest().getIntent().getSlot("tweet_message");
            if (tweetVariable != null) {
                String tweetVariableString = tweetVariable.getValue();
                try {
                    TwitterCalls.makeTweet(tweetVariableString);

                    PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
                    outputSpeech.setText("Okay, I have uploaded your tweet.");

                    return SpeechletResponse.newTellResponse(outputSpeech);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            } else {
                PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
                outputSpeech.setText("I'm sorry, I didn't understand your message.");

                return SpeechletResponse.newTellResponse(outputSpeech);
            }
        }
        else if ("TrendingIntent".equals(intentName)) {
            try {
                String trendingTopics = TwitterCalls.getTrendingTopics();

                PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
                outputSpeech.setText("Today's trending topics are " + trendingTopics);

                return SpeechletResponse.newTellResponse(outputSpeech);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
        else if ("HaveTwitterIntent".equals(intentName)) {
            Slot celebrity = speechletRequestEnvelope.getRequest().getIntent().getSlot("celebrity");
            if (celebrity != null) {
                String celebrityString = celebrity.getValue();
                try {
                    PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
                    outputSpeech.setText(TwitterCalls.isTwitterUser(celebrityString));

                    return SpeechletResponse.newTellResponse(outputSpeech);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            } else {
                PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
                outputSpeech.setText("I'm sorry, I don't recognize that person.");

                return SpeechletResponse.newTellResponse(outputSpeech);
            }
        }
        else {
            String outputSpeech = "Sorry, I didn't get that.";
            String repromptText = "What day do you want events for?";

            return newAskResponse(outputSpeech, true, repromptText, true);
        }
        return null;
    }

    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> speechletRequestEnvelope) { }



    /**
     * Wrapper for creating the Ask response from the input strings.
     *
     * @param stringOutput
     *            the output to be spoken
     * @param isOutputSsml
     *            whether the output text is of type SSML
     * @param repromptText
     *            the reprompt for if the user doesn't reply or is misunderstood.
     * @param isRepromptSsml
     *            whether the reprompt text is of type SSML
     * @return SpeechletResponse Alexa's literal speech output
     */
    private SpeechletResponse newAskResponse(String stringOutput, boolean isOutputSsml,
                                             String repromptText, boolean isRepromptSsml) {
        OutputSpeech outputSpeech, repromptOutputSpeech;
        if (isOutputSsml) {
            outputSpeech = new SsmlOutputSpeech();
            ((SsmlOutputSpeech) outputSpeech).setSsml(stringOutput);
        } else {
            outputSpeech = new PlainTextOutputSpeech();
            ((PlainTextOutputSpeech) outputSpeech).setText(stringOutput);
        }

        if (isRepromptSsml) {
            repromptOutputSpeech = new SsmlOutputSpeech();
            ((SsmlOutputSpeech) repromptOutputSpeech).setSsml(repromptText);
        } else {
            repromptOutputSpeech = new PlainTextOutputSpeech();
            ((PlainTextOutputSpeech) repromptOutputSpeech).setText(repromptText);
        }
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptOutputSpeech);
        return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
    }
}
