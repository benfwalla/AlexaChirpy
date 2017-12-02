package HelperFunctions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Simply converts the contents of "interaction-model/TWEETS-custom-slot-type.txt" into a JSON formatted
 * text so that it is readable for the Alexa Skills Kit.
 */
public class jsonMaker {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("interaction-model/TWEETS-custom-slot-type.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNext()) {
            String word = sc.next();
            String json = "{\n\t\"id\": null,\n\t\"name\": {\n\t\t\"value\": \"" + word + "\",\n\t\t\"synonyms\": []\n\t}\n},";
            System.out.println(json);
        }
    }
}
