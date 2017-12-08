import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class ChirpySpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedAppIds = new HashSet<String>();

    static {
        supportedAppIds.add("[Your Alexa Skill's App ID here]");
    }

    public ChirpySpeechletRequestStreamHandler() {
        super(new ChirpySpeechlet(), supportedAppIds);
    }
}
