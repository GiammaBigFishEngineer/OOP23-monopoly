package app.card.utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for read Jeson.
 */
public final class JsonReader {

    private JsonReader() { }

    /**
     * @param url is the path file .json
     * @return a list of all json object readed
     * @throws IOException
     */
    public static List<JSONObject> readJson(final URL url) throws IOException {
        final List<JSONObject> myList = new ArrayList<>();
        try (InputStream inputStream = url.openStream()) {
            final StringBuilder sb = new StringBuilder();
            int c = 0;
            while (c != -1) {
                c = inputStream.read();
                sb.append((char) c);
            }
            final JSONArray array = new JSONArray(sb.toString());
            for (int i = 0; i < array.length(); i++) {
                final JSONObject object = array.getJSONObject(i);
                myList.add(object);
            }
        } catch (JSONException e) {
            return List.of();
        }
        return myList;
    }
}

