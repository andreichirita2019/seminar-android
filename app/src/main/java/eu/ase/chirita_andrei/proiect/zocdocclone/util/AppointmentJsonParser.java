package eu.ase.chirita_andrei.proiect.zocdocclone.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.ase.chirita_andrei.proiect.zocdocclone.models.Appointment;

public class AppointmentJsonParser {

    public static final String MEDICAL_CATEGORY = "medicalCategory";
    public static final String PATIENT_LOCATION = "patientLocation";
    public static final String DOCTOR_NAME = "doctorName";
    public static final String DATE_OF_APPOINTMENT = "dateOfAppointment";
    public static final String HOUR_OF_APPOINTMENT = "hourOfAppointment";

    public static List<Appointment> fromJson(String json) {
        try {
            JSONArray array = new JSONArray(json);
            return readAppointments(array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static List<Appointment> readAppointments(JSONArray array) throws JSONException {
        List<Appointment> results = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Appointment appointment = readAppointmentObject(array.getJSONObject(i));
            results.add(appointment);
        }
        return results;
    }

    private static Appointment readAppointmentObject(JSONObject object) throws JSONException {
        String medicalCategory = object.getString(MEDICAL_CATEGORY);
        String patientLocation = object.getString(PATIENT_LOCATION);
        String doctorName = object.getString(DOCTOR_NAME);
        String dateOfAppointment = object.getString(DATE_OF_APPOINTMENT);
        String hourOfAppointment = object.getString(HOUR_OF_APPOINTMENT);
        return new Appointment(medicalCategory, patientLocation, doctorName, dateOfAppointment, hourOfAppointment);
    }
}
