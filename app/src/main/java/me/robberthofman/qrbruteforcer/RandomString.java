package me.robberthofman.qrbruteforcer;

import com.mifmif.common.regex.Generex;
import java.util.UUID;

public abstract class RandomString {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int stringLength) {

        StringBuilder builder = new StringBuilder();

        while (stringLength-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }

        return builder.toString();
    }

    public static String randomBringMe() {
        String regex = "[0-9a-f]{4}-[0-9a-f]{2}-[0-9a-f]{2}-[0-9a-f]{2}-[0-9a-f]{6}";
        Generex generator = new Generex(regex);
        String result = generator.random();
        return result;
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }


}
