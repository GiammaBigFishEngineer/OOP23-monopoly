package app.card.utils;

import java.net.URL;

/**
 * This class is a utility class for load resources.
 */
public final class UseGetResource {

    private static final String ROOT = "app/card/";

    private UseGetResource() { }

    /**
     * @param name is the file name
     * @return the url of file found
     */
    public static URL loadResource(final String name) {
        final var url = ClassLoader.getSystemResource(ROOT + name);
        if (url == null) {
            throw new IllegalArgumentException("resource not find with name: " + ROOT + name);
        }
        return url;
    }
}
