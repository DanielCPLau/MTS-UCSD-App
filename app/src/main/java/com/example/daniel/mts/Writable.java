package com.example.daniel.mts;

import org.json.JSONObject;

/**
 * Created by Isaac on 10/30/16.
 */

public interface Writable {
    public String getWritePreflix();
    public boolean isFilled();
    public String getId();
    public JSONObject getJSONObject();
    public void fillJSONObject(JSONObject obj);
    public String getOfficalId();
}
