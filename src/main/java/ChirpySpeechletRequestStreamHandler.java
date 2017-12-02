import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class ChirpySpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedAppIds = new HashSet<String>();

    static {
        supportedAppIds.add("amzn1.ask.skill.38abff0b-4a96-4494-aaba-58ea6db6d9c4");
    }

    public ChirpySpeechletRequestStreamHandler() {
        super(new ChirpySpeechlet(), supportedAppIds);
    }
}
