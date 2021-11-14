package eu.ase.chirita_andrei.proiect.zocdocclone.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import eu.ase.chirita_andrei.proiect.zocdocclone.R;
import eu.ase.chirita_andrei.proiect.zocdocclone.models.Appointment;
import eu.ase.chirita_andrei.proiect.zocdocclone.network.HttpManager;
import eu.ase.chirita_andrei.proiect.zocdocclone.util.AppointmentJsonParser;

public class MainActivity extends AppCompatActivity {

    private final static String APPOINTMENT_URL = "https://jsonkeeper.com/b/MM13";

    private ListView lvAppointmentsJson;
    private List<Appointment> appointmentsJson = new ArrayList<>();

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
        loadAppointmentsFromHttp();
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

    private void loadAppointmentsFromHttp() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                HttpManager manager = new HttpManager(APPOINTMENT_URL);
                String result = manager.process();
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mainThreadGetAppointmentsFromHttpCallback(result);
                    }
                });
            }
        };
        thread.start();
    }

    private void mainThreadGetAppointmentsFromHttpCallback(String result) {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        appointmentsJson.addAll(AppointmentJsonParser.fromJson(result));
        notifyLvAppointmentJsonAdapter();
    }

    private void addLvAppointmentsAdapter() {
        ArrayAdapter<Appointment> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, appointments);
        lvAppointments.setAdapter(adapter);
    }
    private void addLvAppointmentsJsonAdapter() {
        ArrayAdapter<Appointment> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, appointmentsJson);
        lvAppointmentsJson.setAdapter(adapter);
    }

    private void notifyLvAppointmentAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) lvAppointments.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void notifyLvAppointmentJsonAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) lvAppointmentsJson.getAdapter();
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
        lvAppointmentsJson = findViewById(R.id.lv_main_list_json);
        addLvAppointmentsAdapter();
        addLvAppointmentsJsonAdapter();
    }
}