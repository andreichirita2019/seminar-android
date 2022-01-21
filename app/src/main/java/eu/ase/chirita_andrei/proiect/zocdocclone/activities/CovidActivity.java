package eu.ase.chirita_andrei.proiect.zocdocclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.chirita_andrei.proiect.zocdocclone.R;
import eu.ase.chirita_andrei.proiect.zocdocclone.models.Covid19;
import eu.ase.chirita_andrei.proiect.zocdocclone.network.AsyncTaskRunner;
import eu.ase.chirita_andrei.proiect.zocdocclone.network.Callback;
import eu.ase.chirita_andrei.proiect.zocdocclone.network.HttpManager;
import eu.ase.chirita_andrei.proiect.zocdocclone.util.AppointmentJsonParser;
import eu.ase.chirita_andrei.proiect.zocdocclone.util.Covid19JsonParser;
import eu.ase.chirita_andrei.proiect.zocdocclone.util.DateConverter;

public class CovidActivity extends AppCompatActivity {

    private final static String COVID_19_URL_ROMANIA = "https://www.graphs.ro/json_apify.php";

    private final AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
    private Covid19JsonParser covid19JsonParser;
    private DateConverter dateConverter;

    public static TextView tvTotalCases;
    public static TextView tvTotalTested;
    public static TextView tvTotalRecovered;
    public static TextView tvTotalDeceased;
    public static TextView tvLastUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);
        initComponents();
        loadCovid19InformationsFromHttp();
    }

    private void initComponents(){
        tvTotalCases = findViewById(R.id.tv_card_covid19_total_confirmed_cases);
        tvTotalTested = findViewById(R.id.tv_card_covid19_total_tested);
        tvTotalRecovered = findViewById(R.id.tv_card_covid19_total_recovered);
        tvTotalDeceased = findViewById(R.id.tv_card_covid19_total_death);
        tvLastUpdated = findViewById(R.id.tv_covid_date_last_update);
    }

    private Callback<String> getMainThreadOperation() {
        return new Callback<String>() {
            @Override
            public void runResultOnUiThead(String result) throws JSONException, ParseException {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                JSONObject object = new JSONObject(result);
                String totalCases = String.valueOf(object.getInt("infected"));
                tvTotalCases.setText(totalCases);

                String totalTested = String.valueOf(object.getInt("tested"));
                tvTotalTested.setText(totalTested);

                String totalRecovered = String.valueOf(object.getInt("recovered"));
                tvTotalRecovered.setText(totalRecovered);

                String totalDeceased = String.valueOf(object.getInt("deceased"));
                tvTotalDeceased.setText(totalDeceased);

                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM");
                String dates = String.valueOf(object.getString("lastUpdatedAtSource"));
                Date date = inputFormat.parse(dates);
                String formattedDate = outputFormat.format(date);
                tvLastUpdated.setText(formattedDate);
            }
        };
    }

    private void loadCovid19InformationsFromHttp(){
        Callable<String> asyncOperation = new HttpManager(COVID_19_URL_ROMANIA);
        Callback<String> mainThreadOperation = getMainThreadOperation();
        asyncTaskRunner.executeAsync(asyncOperation, mainThreadOperation);
    }

}