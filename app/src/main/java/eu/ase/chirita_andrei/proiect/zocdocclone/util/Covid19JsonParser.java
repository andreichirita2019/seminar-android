package eu.ase.chirita_andrei.proiect.zocdocclone.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.ase.chirita_andrei.proiect.zocdocclone.models.Appointment;
import eu.ase.chirita_andrei.proiect.zocdocclone.models.Covid19;

public class Covid19JsonParser {

    public static final String TOTAL_INFECTED = "infected";
    public static final String TOTAL_TESTED = "tested";
    public static final String TOTAL_RECOVERED = "recovered";
    public static final String TOTAL_DECEASED = "deceased";

    public static Covid19 fromJson(String json) {
        try {
            JSONObject object = new JSONObject(json);
            return readCovid19Object(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Covid19();
    }

    public static Covid19 readCovid19Object(JSONObject object) throws JSONException {
        int totalInfected = object.getInt(TOTAL_INFECTED);
        int totalTested = object.getInt(TOTAL_TESTED);
        int totalRecovered = object.getInt(TOTAL_RECOVERED);
        int totalDeceased = object.getInt(TOTAL_DECEASED);
        return new Covid19(totalInfected,totalTested,totalRecovered,totalDeceased);
    };
}
