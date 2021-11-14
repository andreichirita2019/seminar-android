package eu.ase.chirita_andrei.proiect.zocdocclone.models;

import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {

    private String medicalCategory;
    private String patientLocation;
    private String doctorName;
    private String dateOfAppointment;
    private String hourOfAppointment;
//    private String insurancePatient;


    public Appointment(String medicalCategory,String patientLocation, String doctorName,String dateOfAppointment, String hourOfAppointment) {
        this.medicalCategory = medicalCategory;
        this.patientLocation = patientLocation;
        this.doctorName = doctorName;
        this.dateOfAppointment = dateOfAppointment;
        this.hourOfAppointment = hourOfAppointment;
    }

    public Appointment(String medicalCategory, String patientLocation, String doctorName, String dateOfAppointment) {
        this.medicalCategory = medicalCategory;
        this.patientLocation = patientLocation;
        this.doctorName = doctorName;
        this.dateOfAppointment = dateOfAppointment;
    }

    public String getMedicalCategory() {
        return medicalCategory;
    }

    public void setMedicalCategory(String medicalCategory) {
        this.medicalCategory = medicalCategory;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientLocation() {
        return patientLocation;
    }

    public void setPatientLocation(String patientLocation) {
        this.patientLocation = patientLocation;
    }

    public String getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(String dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public String getHourOfAppointment() {
        return hourOfAppointment;
    }

    public void setHourOfAppointment(String hourOfAppointment) {
        this.hourOfAppointment = hourOfAppointment;
    }

    @Override
    public String toString() {
        return "APPOINTMENT" +
                "\n" +
                "\nMedical Category: " + medicalCategory +
                "\nLocation: " + patientLocation +
                "\nDoctor: " + doctorName +
                "\nDate of Appointment: " + dateOfAppointment +
                "\nHour of Appointment: " + hourOfAppointment;
    }
}
