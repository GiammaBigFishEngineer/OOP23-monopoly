package app.card.utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for read Jeson.
 */
public final class JsonReader {

    private JsonReader() { }

    /**
     * @param json is the path file .json
     * @return a list of all json object readed
     * @throws IOException
     */
    public static List<JSONObject> readJson(final String json) throws IOException {
        final List<JSONObject> myList = new ArrayList<>();
        try {
            final String jj = Files.readString(Path.of(json));
            final JSONArray array = new JSONArray(jj);
            for (int i = 0; i < array.length(); i++) {
                final JSONObject object = array.getJSONObject(i);
                myList.add(object);
            }
            return myList;
        } catch (JSONException e) {
            return List.of();
        }
    }
}

