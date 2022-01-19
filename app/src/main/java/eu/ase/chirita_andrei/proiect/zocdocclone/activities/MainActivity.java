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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.chirita_andrei.proiect.zocdocclone.R;
import eu.ase.chirita_andrei.proiect.zocdocclone.adapters.AppointmentAdapter;
import eu.ase.chirita_andrei.proiect.zocdocclone.database.room.AppointmentService;
import eu.ase.chirita_andrei.proiect.zocdocclone.models.Appointment;
import eu.ase.chirita_andrei.proiect.zocdocclone.network.AsyncTaskRunner;
import eu.ase.chirita_andrei.proiect.zocdocclone.network.Callback;
import eu.ase.chirita_andrei.proiect.zocdocclone.network.HttpManager;
import eu.ase.chirita_andrei.proiect.zocdocclone.util.AppointmentJsonParser;

public class MainActivity extends AppCompatActivity {

    //private ScrollView scrollView;
    private AsyncTaskRunner asyncTaskRunner;

    //o instanta concreta care imi va intoarce un vector de appointments
    private final static String APPOINTMENT_URL = "https://jsonkeeper.com/b/UDN6";
    //private final static String APPOINTMENT_URL = "https://jsonkeeper.com/b/WZOH";
    private final static String COVID_19_URL_ROMANIA = "https://www.graphs.ro/json_apify.php";

    private ListView lvAppointmentsJson;
    private List<Appointment> appointmentsJson = new ArrayList<>();

    private FloatingActionButton fabAddAppointment;
    private ListView lvAppointments;
    private List<Appointment> appointments = new ArrayList<>();

    private ActivityResultLauncher<Intent> addAppointmentLauncher;
    //creez un nou obiect de tip Launcher pentru a face diferenta in memoria Java, ce fel de informatii transmit (fie ca inserez in database, fie ca fac update sau delete)
    private ActivityResultLauncher<Intent> updateAppointmentLauncher;

    //COVID 19 TRACKER
    public static TextView tvTotalCases;
    public static TextView tvTotalTested;
    public static TextView tvTotalRecovered;
    public static TextView tvTotalDeceased;

    private AppointmentService appointmentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        addAppointmentLauncher = addAppointmentActivityResultLauncher();
        updateAppointmentLauncher = updateAppointmentActivityResultLauncher();

        //incarcare date JSON (Appointments) in Listview (Adapter personalizat)
        //loadAppointmentsFromHttp();
        //loadAppointmentsFromHttp2();

        appointmentService = new AppointmentService(getApplicationContext());
        //preluare lista din baza de date
        appointmentService.getAll(getAllAppointmentsCallback());
    }


    //-------------------------------------                   ----------------------------------------------------------
    //                                      CALLBACK FORMULAR (INSERT + UPDATE)
    //-------------------------------------                   ----------------------------------------------------------

    //----------------------------------------  INSERT  ---------------------------------------
    private ActivityResultCallback<ActivityResult> getAddAppointmentActivityResultCallback() {
        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result != null && result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Appointment appointment = result.getData().getParcelableExtra(AppointmentActivity.ADD_APPOINTMENT_KEY);
                    //inserare in baza de date
                    appointmentService.insert(appointment,insertAppointmentCallback());
                }
            }
        };
    }

    private ActivityResultLauncher<Intent> addAppointmentActivityResultLauncher() {
        //definire CALLBACK pentru INSERT
        ActivityResultCallback<ActivityResult> callback = getAddAppointmentActivityResultCallback();
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
    }


    //----------------------------------------  UPDATE  --------------------------------------------

    private ActivityResultCallback<ActivityResult> getUpdateAppointmentActivityResultCallback() {
        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result != null && result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Appointment appointment = result.getData().getParcelableExtra(AppointmentActivity.ADD_APPOINTMENT_KEY);
                     //update operation
                    //actualizare informatii in BD (update)
                    appointmentService.update(appointment,updateAppointmentCallback());
                }
            }
        };
    }

    private ActivityResultLauncher<Intent> updateAppointmentActivityResultLauncher(){
        //definim callback pentru UPDATE
        ActivityResultCallback<ActivityResult> callback = getUpdateAppointmentActivityResultCallback();
        //la update ne intoarcem cu alt scop
        //facem launchere si callback diferit
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),callback);
    }


    //-------------------------------------- ADAPTERS + NOTIFY ------------------------------------------------
    private void addLvAppointmentsAdapter() {
        AppointmentAdapter adapter = new AppointmentAdapter(getApplicationContext(),
                R.layout.lv_row_view_appointments_custom_adapter,appointments,getLayoutInflater());
        lvAppointments.setAdapter(adapter);
    }
    private void addLvAppointmentsJsonAdapter() {
        AppointmentAdapter adapter = new AppointmentAdapter(getApplicationContext(),
                R.layout.lv_row_view_appointments_custom_adapter, appointmentsJson,getLayoutInflater());
        lvAppointmentsJson.setAdapter(adapter);
    }

    private void notifyLvAppointmentJsonAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) lvAppointmentsJson.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void notifyLvAppointmentAdapter(){
        ArrayAdapter adapter = (ArrayAdapter) lvAppointments.getAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return true;
    }

    //------------------------------------------ CLICK LISTENER ------------------------------------------------
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

    private AdapterView.OnItemLongClickListener getitemLongClickEvent() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //stergere din Baza de Date
                appointmentService.delete(appointments.get(position),deleteAppointmentCallback(position));
                return true;
            }
        };
    }

    private AdapterView.OnItemClickListener getItemClickEvent() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),AppointmentActivity.class);
                //trimitem obiectul Appointment catre AppointmentActivity
                intent.putExtra(AppointmentActivity.ADD_APPOINTMENT_KEY,appointments.get(position));
                updateAppointmentLauncher.launch(intent);
            }
        };
    }

    private void initComponents() {
        tvTotalCases = findViewById(R.id.tv_card_covid19_confirmed_cases);
        tvTotalTested = findViewById(R.id.tv_card_covid19_total_tested);
        tvTotalRecovered = findViewById(R.id.tv_card_covid19_total_recovered);
        tvTotalDeceased = findViewById(R.id.tv_card_covid19_total_death);

        fabAddAppointment = findViewById(R.id.fab_main_appointment);
        fabAddAppointment.setOnClickListener(getAddAppointmentClickListener());

        lvAppointments = findViewById(R.id.lv_main_list);
        lvAppointmentsJson = findViewById(R.id.lv_main_list_json);

        addLvAppointmentsAdapter();
        addLvAppointmentsJsonAdapter();

        lvAppointments.setOnItemClickListener(getItemClickEvent());
        lvAppointments.setOnItemLongClickListener(getitemLongClickEvent());
    }

    //------------------------------------------ SQLite -----------------------------------------------------------//

    private Callback<List<Appointment>> getAllAppointmentsCallback() {
        return new Callback<List<Appointment>>() {
            @Override
            public void runResultOnUiThead(List<Appointment> result) {
                if(result !=null){
                    appointments.clear();
                    appointments.addAll(result);
                    notifyLvAppointmentAdapter();
                }
            }
        };
    }

    private Callback<Appointment> insertAppointmentCallback() {
        //locul in care vom fi notificati ca insert-ul a functionat sau nu
        return new Callback<Appointment>() {
            @Override
            public void runResultOnUiThead(Appointment appointment) {
                //in mom in care ne va raspunde, ne va trimite acelasi obiect ca cel din ActivityResult (insert) , dar mai contine si id-ul (dovada ca a functionat inserare in BD)
                //realizam ceea ce facem in activityResult
                //voi adauga in listview doar dupa ce primesc confirmarea ca adaugarea in BD a functionat
                //copy paste din ActivityResultCallback
                if (appointment != null) {
                    appointments.add(appointment);
                    notifyLvAppointmentAdapter();
                }
            }
        };
    }

    private Callback<Appointment> updateAppointmentCallback() {
        return new Callback<Appointment>() {
            @Override
            public void runResultOnUiThead(Appointment result) {
                if(result!=null){
                    //actualizare listview
                    //ca sa facem actualizarea trebuie sa identificam obiectul
                    for(Appointment appointment : appointments){
                        if(appointment.getId() == result.getId()){
                            //modificare date
                            //result este ceea ce vine din baza de date
                            //appointment este ceea ce avem noi in Adapter
                            appointment.setMedicalCategory(result.getMedicalCategory());
                            appointment.setPatientLocation(result.getPatientLocation());
                            appointment.setDoctorName(result.getDoctorName());
                            appointment.setDateOfAppointment(result.getDateOfAppointment());
                            appointment.setHourOfAppointment(result.getHourOfAppointment());
                            break;
                        }
                    }
                    notifyLvAppointmentAdapter();
                }
            }
        };
    }

    private Callback<Boolean> deleteAppointmentCallback(int position) {
        return new Callback<Boolean>() {
            @Override
            public void runResultOnUiThead(Boolean result) {
                if(result){
                    //stergere obiect din Appointments + actualizare listview
                    appointments.remove(position);
                    notifyLvAppointmentAdapter();
                }
            }
        };
    }


    //---------------------------------------- HTTP + ASYNCTASK -----------------------------------------//

    private Callback<String> getMainThreadOperation() {
        return new Callback<String>() {
            @Override
            public void runResultOnUiThead(String result) {
                //ma aflu in activitatea principala
                //asta este referinta pe care i-o dau
                //callback-ul trebuie facut la nivelul activitatii la care fac procesarea asincrona
                //la callback nu se recomanda crearea unei clase separate ptca callback este mult mai variat si nu are sens
                //callback apartine mai tot timpul de o activitate si trebuie scris acolo
                //le-am luat din mainThreadGetAppointmentsFromHttpCallback()
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                appointmentsJson.addAll(AppointmentJsonParser.fromJson(result));
                //cand modificam sursa unui adapter, trebuie sa facem notify pentru a se redesena
                notifyLvAppointmentJsonAdapter();
            }
        };
    }

    private void loadAppointmentsFromHttp(){
        //aici este momentul in care eu voi inlocui R cu String
        //callable intoarce un rezultat de tip String
        //callback priemste un parametru de intrare de tip String
        Callable<String> asyncOperation = new HttpManager(APPOINTMENT_URL);
        Callback<String> mainThreadOperation = getMainThreadOperation();
        asyncTaskRunner.executeAsync(asyncOperation, mainThreadOperation);

        //executeAsync este metoda Launch
        //asyncOperation este intent-ul - locul unde procesez asincron
        //mainThreadOperation este OnActivityResultCallback
    }


        private void loadAppointmentsFromHttp2() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                HttpManager manager = new HttpManager(APPOINTMENT_URL);
                String result = manager.process();
                //sa observe si sa ajute alte thread-uri sa impinga rezultatele lor spre thread-ul principal
                //MainLooper (referinta catre activitatea care ruleaza pe ecran (nu neaparat principala))
                //la nivel de post accepta un obiect de tipul Runnable
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    //abia aici ne aflam in MAIN THREAD (MAIN ACTVIITY)
                    //putem apela orice metoda din app-ul nostru
                    public void run() {
                        mainThreadGetAppointmentsFromHttpCallback(result);
                    }
                });
            }
        };
        thread.start();
    }

    private void mainThreadGetAppointmentsFromHttpCallback(String result) {
        appointments.addAll(AppointmentJsonParser.fromJson(result));
    }

}