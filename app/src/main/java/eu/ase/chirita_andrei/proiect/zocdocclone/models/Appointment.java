package eu.ase.chirita_andrei.proiect.zocdocclone.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "appointments")
public class Appointment implements Parcelable {

    //ERROR: Cannot figure out how to save this field into database. You can consider adding a type converter for it. (Date si GenderType)
    //trebuie sa ne definim metode public static - 2 metode care sa converteasca de la A la B si apoi de la B la A
    //public ca sa aiba acces Room-ul
    //static ca sa nu creez instante
    //A este tipul de data JAVA
    //B este tipul de data SQL

    //in clasele unde avem metodele, trebuie adnotate cu TypeConverter


    //in mom cand facem SELECT, room va trb sa converteasca informatiile din tabela USERS la ob de tipul USER
    //are nevoie de constructor - fie cu toti param
    //fie fara param si ii lasam getteri si setteri
    //daca avem mai mult constructori Room nu va stii pe care sa ii apeleze
    //pentru toti constructorii pe care nu ii vrem sa ii foloseasca room le punem adnotarea IGNORE

    @PrimaryKey(autoGenerate = true)//realizeaza acea bucata de script addConstraintPK
    //mai mult decat atat, pt SQLite - autogenerate = true ->
    //id-ul trebuie sa fie unic si nu o sa ii dam value
    //face o secventa in memorie, pleaca cu numaratoarea de la 1, asigura popularea campului id
    @ColumnInfo(name="id")
    private long id; //key - va trb sa fie o coloana;

    @ColumnInfo(name="medical_category")
    private String medicalCategory;
    @ColumnInfo(name="patient_location")
    private String patientLocation;
    @ColumnInfo(name="doctor_name")
    private String doctorName;
    @ColumnInfo(name="date_of_appointment")
    private String dateOfAppointment;
    @ColumnInfo(name="hour_of_appointment")
    private String hourOfAppointment;
//    private String insurancePatient;

    //lipsa acestui id ne va afecta la UPDATE
    //in firebase, id-ul este STRING
    //private String idFirebase;

//    @Ignore
//    public Appointment(String idFirebase, String medicalCategory, String patientLocation, String doctorName, String dateOfAppointment, String hourOfAppointment) {
//        this.idFirebase = idFirebase;
//        this.medicalCategory = medicalCategory;
//        this.patientLocation = patientLocation;
//        this.doctorName = doctorName;
//        this.dateOfAppointment = dateOfAppointment;
//        this.hourOfAppointment = hourOfAppointment;
//    }

    public Appointment(long id, String medicalCategory, String patientLocation, String doctorName, String dateOfAppointment, String hourOfAppointment) {
        this.id = id;
        this.medicalCategory = medicalCategory;
        this.patientLocation = patientLocation;
        this.doctorName = doctorName;
        this.dateOfAppointment = dateOfAppointment;
        this.hourOfAppointment = hourOfAppointment;
    }

    @Ignore
    public Appointment(String medicalCategory, String patientLocation, String doctorName, String dateOfAppointment, String hourOfAppointment) {
        this.medicalCategory = medicalCategory;
        this.patientLocation = patientLocation;
        this.doctorName = doctorName;
        this.dateOfAppointment = dateOfAppointment;
        this.hourOfAppointment = hourOfAppointment;
    }

    @Ignore
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public String getIdFirebase() {
//        return idFirebase;
//    }
//
//    public void setIdFirebase(String idFirebase) {
//        this.idFirebase = idFirebase;
//    }

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

    private Appointment(Parcel source){
        medicalCategory = source.readString();
        patientLocation = source.readString();
        doctorName = source.readString();
        dateOfAppointment = source.readString();
        hourOfAppointment = source.readString();
    }

    public static Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel source) {
            return new Appointment(source);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(medicalCategory);
        dest.writeString(patientLocation);
        dest.writeString(doctorName);
        dest.writeString(dateOfAppointment);
        dest.writeString(hourOfAppointment);
    }
}
