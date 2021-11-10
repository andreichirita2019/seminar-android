package eu.ase.chirita_andrei.proiect.zocdocclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

import eu.ase.chirita_andrei.proiect.zocdocclone.util.Appointment;
import eu.ase.chirita_andrei.proiect.zocdocclone.util.DateConverter;

public class AppointmentActivity extends AppCompatActivity {

    public static final String ADD_APPOINTMENT_KEY = "ADD_APPOINTMENT_KEY";

    private Spinner spnMedicalCategories;
    private TextInputEditText tietPatientLocation;
    private Spinner spnDoctors;
    private Spinner spnDateofAppointment;

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
        String doctors = spnDoctors.getSelectedItem().toString();
        String datesAppointments = spnDateofAppointment.getSelectedItem().toString();
        return new Appointment(medicalCategory, patientLocation, doctors, datesAppointments);
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

    private void addSpinnerDateOfAppointmentAdapter(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.appointment_date_values,
                android.R.layout.simple_spinner_dropdown_item);
        spnDateofAppointment.setAdapter(adapter);
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
        spnDateofAppointment = findViewById(R.id.spn_appointment_date);
        btnBookAnAppointment = findViewById(R.id.btn_appointment_save);
        addSpinnerMedicalCategoriesAdapter();
        addSpinnerDoctorsAdapter();
        addSpinnerDateOfAppointmentAdapter();
        btnBookAnAppointment.setOnClickListener(getSaveAppointmentClickListener());
    }
}