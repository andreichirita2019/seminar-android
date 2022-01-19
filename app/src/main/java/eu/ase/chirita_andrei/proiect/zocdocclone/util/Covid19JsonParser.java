package eu.ase.chirita_andrei.proiect.zocdocclone.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.ase.chirita_andrei.proiect.zocdocclone.models.Covid19;

public class Covid19JsonParser {

    public static final String TOTAL_INFECTED = "TOTAL_INFECTED";
    public static final String TOTAL_TESTED = "TOTAL_TESTED";
    public static final String TOTAL_RECOVERED = "TOTAL_RECOVERED";
    public static final String TOTAL_DECEASED = "TOTAL_DECEASED";
    public static final String LAST_UPDATED_SOURCE = "LAST_UPTATED_SOURCE";

    public static Covid19 fromJson(String json) {
        try {
            JSONObject object = new JSONObject(json);
            return readCovid19Object(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Covid19();
    }

    private static Covid19 readCovid19Object(JSONObject object) throws JSONException {
        int totalInfected = object.getInt(TOTAL_INFECTED);
        int totalTested = object.getInt(TOTAL_TESTED);
        int totalRecovered = object.getInt(TOTAL_RECOVERED);
        int totalDeceased = object.getInt(TOTAL_DECEASED);
        //String lastUpdatedSource = object.getString(LAST_UPDATED_SOURCE);
        //return new Covid19(totalInfected,totalTested,totalRecovered,totalDeceased,lastUpdatedSource);
        return new Covid19(totalInfected,totalTested,totalRecovered,totalDeceased);
    };
}
