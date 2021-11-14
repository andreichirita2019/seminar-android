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

 public class AppointmentActivity extends AppCompatActivity {

    public static final String ADD_APPOINTMENT_KEY = "ADD_APPOINTMENT_KEY";

    private Spinner spnMedicalCategories;
    private TextInputEditText tietPatientLocation;
    private Spinner spnDoctors;
    private TextInputEditText tietDateofAppointment;
    private Spinner spnHourofAppointment;

    private Button btnBookAnAppointment;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        initComponents();
        intent = getIntent();
    }

    private Appointment buildAppointmentFromComponents() {
        String medicalCategory = spnMedicalCategories.getSelectedItem().toString();
        String patientLocation = tietPatientLocation.getText().toString();
        String doctorName = spnDoctors.getSelectedItem().toString();
        String datOfAppointment = tietDateofAppointment.getText().toString();
        String hourOfAppointment = spnHourofAppointment.getSelectedItem().toString();
        return new Appointment(medicalCategory, patientLocation, doctorName, datOfAppointment, hourOfAppointment);
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

    private View.OnClickListener getSaveAppointmentClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    Appointment appointment = buildAppointmentFromComponents();
                    intent.putExtra(ADD_APPOINTMENT_KEY, appointment);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
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