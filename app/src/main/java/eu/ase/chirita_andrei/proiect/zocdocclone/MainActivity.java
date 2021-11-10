package eu.ase.chirita_andrei.proiect.zocdocclone;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import eu.ase.chirita_andrei.proiect.zocdocclone.util.Appointment;
import eu.ase.chirita_andrei.proiect.zocdocclone.util.User;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAddAppointment;
    private ListView lvAppointments;
    private List<Appointment> appointments = new ArrayList<>();
    private ActivityResultLauncher<Intent> addAppointmentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        addAppointmentLauncher = registerAddAppointmentActivityResultLauncher();
    }

    private ActivityResultCallback<ActivityResult> getAddAppointmentActivityResultCallback() {
        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Appointment appointment = (Appointment) result.getData().getSerializableExtra(AppointmentActivity.ADD_APPOINTMENT_KEY);
                    if (appointment != null) {
                        appointments.add(appointment);
                        notifyLvAppointmentAdapter();
                    }
                }
            }
        };
    }

    private ActivityResultLauncher<Intent> registerAddAppointmentActivityResultLauncher() {
        ActivityResultCallback<ActivityResult> callback = getAddAppointmentActivityResultCallback();
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
    }

    private void addLvAppointmentsAdapter() {
        ArrayAdapter<Appointment> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, appointments);
        lvAppointments.setAdapter(adapter);
    }

    private void notifyLvAppointmentAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) lvAppointments.getAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return true;
    }

    @NonNull
    private View.OnClickListener getAddAppointmentClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AppointmentActivity.class);
                addAppointmentLauncher.launch(intent);
            }
        };
    }

    private void initComponents() {
        fabAddAppointment = findViewById(R.id.fab_main_appointment);
        fabAddAppointment.setOnClickListener(getAddAppointmentClickListener());
        lvAppointments = findViewById(R.id.lv_main_list);
        addLvAppointmentsAdapter();
    }
}