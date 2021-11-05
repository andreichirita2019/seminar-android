package eu.ase.chirita_andrei.proiect.zocdocclone.util;

import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {

    private String medicalCategory;
    private String doctorName;
    private String patientLocation;
    private Date dateOfAppointment;
    private String hourOfAppointment;
//    private String insurancePatient;


    public Appointment(String medicalCategory, String doctorName, String patientLocation, Date dateOfAppointment, String hourOfAppointment) {
        this.medicalCategory = medicalCategory;
        this.doctorName = doctorName;
        this.patientLocation = patientLocation;
        this.dateOfAppointment = dateOfAppointment;
        this.hourOfAppointment = hourOfAppointment;
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

    public Date getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(Date dateOfAppointment) {
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
        return "Appointment{" +
                "medicalCategory='" + medicalCategory + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", patientLocation='" + patientLocation + '\'' +
                ", dateOfAppointment=" + dateOfAppointment +
                ", hourOfAppointment='" + hourOfAppointment + '\'' +
                '}';
    }
}
