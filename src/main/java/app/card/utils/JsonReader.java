package app.card.utils;
import org.json.*;

import java.util.ArrayList;
import java.util.List;

public final class JsonReader {

    private JsonReader(){ }

    public static List<JSONObject> readJson(final String json) {
        List<JSONObject> myList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                myList.add(object);
            }
            return myList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        
    }
}

