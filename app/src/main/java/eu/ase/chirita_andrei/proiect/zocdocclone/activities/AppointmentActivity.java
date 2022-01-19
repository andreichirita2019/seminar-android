 package eu.ase.chirita_andrei.proiect.zocdocclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import eu.ase.chirita_andrei.proiect.zocdocclone.R;
import eu.ase.chirita_andrei.proiect.zocdocclone.models.Appointment;
import eu.ase.chirita_andrei.proiect.zocdocclone.util.DateConverter;

 public class AppointmentActivity extends AppCompatActivity {

    public static final String ADD_APPOINTMENT_KEY = "ADD_APPOINTMENT_KEY";

    private Spinner spnMedicalCategories;
    private TextInputEditText tietPatientLocation;
    private Spinner spnDoctors;
    private TextInputEditText tietDateofAppointment;
    private Spinner spnHourofAppointment;

    private Button btnBookAnAppointment;
    private Intent intent;

    private Appointment appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        initComponents();
        intent = getIntent();
        if(intent.hasExtra(ADD_APPOINTMENT_KEY)){
            //atunci ne aflam pe operatia de update
            appointment = intent.getParcelableExtra(ADD_APPOINTMENT_KEY);
            //trebuie sa incarc obiectul in componente vizuale
            createViewsFromAppointments();
        }
    }

     private void createViewsFromAppointments() {
        if(appointment == null){
            return;
        }
        spinnerSelectionMedicalCategories();
        tietPatientLocation.setText(appointment.getPatientLocation());
        spinnerSelectionDoctors();
        tietDateofAppointment.setText(appointment.getDateOfAppointment());
        spinnerSelectionHourOfAppointments();
     }

     //selectia dintr-un spinner se face pe baza adapterului
     private void spinnerSelectionHourOfAppointments() {
         ArrayAdapter adapter = (ArrayAdapter) spnHourofAppointment.getAdapter();
         for(int i =0;i<adapter.getCount();i++){
             //parcurgem fiecare element din acel adapter
             String item = (String) adapter.getItem(i);
             if(item.equals(appointment.getHourOfAppointment())){
                 //nu avem setSelection pe Object
                 spnHourofAppointment.setSelection(i);
                 break;
             }
         }
     }

     private void spinnerSelectionDoctors() {
         ArrayAdapter adapter = (ArrayAdapter) spnDoctors.getAdapter();
         for(int i =0;i<adapter.getCount();i++){
             //parcurgem fiecare element din acel adapter
             String item = (String) adapter.getItem(i);
             if(item.equals(appointment.getDoctorName())){
                 spnDoctors.setSelection(i);
                 break;
             }
         }
     }

     private void spinnerSelectionMedicalCategories() {
         ArrayAdapter adapter = (ArrayAdapter) spnMedicalCategories.getAdapter();
         for(int i =0;i<adapter.getCount();i++){
             //parcurgem fiecare element din acel adapter
             String item = (String) adapter.getItem(i);
             if(item.equals(appointment.getMedicalCategory())){
                 spnMedicalCategories.setSelection(i);
                 break;
             }
         }
     }

     private void createFromViews() {
         String medicalCategory = spnMedicalCategories.getSelectedItem().toString();
         String patientLocation = tietPatientLocation.getText().toString();
         String doctorName = spnDoctors.getSelectedItem().toString();
         String datOfAppointment = tietDateofAppointment.getText().toString();
         String hourOfAppointment = spnHourofAppointment.getSelectedItem().toString();
         if(appointment == null){
             //operatie de adaugare (insert)
             //fac obiect de la 0;
             appointment = new Appointment(medicalCategory,patientLocation,doctorName,datOfAppointment,hourOfAppointment);
         } else {
             //operatie de modificare
             //daca facem obiect nou pe UPDATE, pierdem cheia din BD
             appointment.setMedicalCategory(medicalCategory);
             appointment.setPatientLocation(patientLocation);
             appointment.setDoctorName(doctorName);
             appointment.setDateOfAppointment(datOfAppointment);
             appointment.setHourOfAppointment(hourOfAppointment);
         }
     }

    private boolean isValid() {
        if (tietPatientLocation.getText() == null || tietPatientLocation.getText().toString().trim().length() < 3) {
            Toast.makeText(getApplicationContext(),
                  "Invalid name location. Minimum 3 characters. Try again!",
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        return true;
    }

    private View.OnClickListener getSaveAppointmentClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
//                    appointment = buildAppointmentFromComponents();
                    createFromViews();
                    intent.putExtra(ADD_APPOINTMENT_KEY, appointment);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }

     private void addSpinnerMedicalCategoriesAdapter(){
         ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                 R.array.appointment_medical_category_values,
                 android.R.layout.simple_spinner_dropdown_item);
         spnMedicalCategories.setAdapter(adapter);
     }

     private void addSpinnerDoctorsAdapter(){
         ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                 R.array.appointment_doctors_values,
                 android.R.layout.simple_spinner_dropdown_item);
         spnDoctors.setAdapter(adapter);
     }

     private void addSpinnerHourOfAppointmentAdapter(){
         ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                 R.array.appointment_hour_values,
                 android.R.layout.simple_spinner_dropdown_item);
         spnHourofAppointment.setAdapter(adapter);
     }

    private void initComponents() {
        spnMedicalCategories = findViewById(R.id.spn_appointment_medical_category);
        tietPatientLocation = findViewById(R.id.tie_appointment_patient_location);
        spnDoctors = findViewById(R.id.spn_appointment_doctor);
        tietDateofAppointment = findViewById(R.id.tie_appointment_date);
        spnHourofAppointment = findViewById(R.id.spn_appointment_hour);
        btnBookAnAppointment = findViewById(R.id.btn_appointment_save);
        addSpinnerMedicalCategoriesAdapter();
        addSpinnerDoctorsAdapter();
        addSpinnerHourOfAppointmentAdapter();
        btnBookAnAppointment.setOnClickListener(getSaveAppointmentClickListener());
    }

 }