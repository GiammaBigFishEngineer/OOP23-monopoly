package app.card.view;

import java.io.File;
import java.net.URL;

/**
 * This class is a utility class for load resources.
 */
public final class UseGetResource {

    private static final String SEP = File.separator;
    private static final String ROOT = "app" + SEP + "card" + SEP + "view" + SEP + "image" + SEP;

    private UseGetResource() { }

    /**
     * @param nameImage is the file name
     * @return the url of file found
     */
    public static URL loadResource(final String nameImage) {
        final var url = ClassLoader.getSystemResource(ROOT + nameImage);
        if (url == null) {
            throw new IllegalArgumentException("resource not find");
        }
        return url;
    }
}
