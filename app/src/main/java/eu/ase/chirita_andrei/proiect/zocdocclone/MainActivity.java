package eu.ase.chirita_andrei.proiect.zocdocclone;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import eu.ase.chirita_andrei.proiect.zocdocclone.util.User;

public class MainActivity extends AppCompatActivity {

    private Button btnCreateAccount;

    private TextInputEditText tietEmail;
    private TextInputEditText tietPassword;

    private ActivityResultLauncher<Intent> addUserLauncher;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        addUserLauncher = registerAddUserLauncher();
    }

    private ActivityResultCallback<ActivityResult> getAddUserActivityResultCallback(){
        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== RESULT_OK && result.getData() !=null){
//                    User user = (User) result.getData().getSerializableExtra(SignUpActivity.ADD_USER_KEY);
//                    if(user!=null){
                        //PROBLEMAAAAAAAAA
//                        String userEmail = getIntent().getStringExtra("ADD_USER_KEY");
//                        String userPassword = getIntent().getStringExtra("ADDD_USER_KEY");
//                        tietEmail.setText(userEmail);
//                        tietPassword.setText(userPassword);
                        if(intent!=null){
                            String data = intent.getStringExtra(String.valueOf(result));
                            tietEmail.setText(data);
                            tietPassword.setText(data);
                        }
                    }
                }
        };
    }

    private ActivityResultLauncher<Intent> registerAddUserLauncher(){
        ActivityResultCallback<ActivityResult> callback = getAddUserActivityResultCallback();
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),callback);
    }

    private View.OnClickListener getCreateAccountClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                addUserLauncher.launch(intent);
            }
        };
    }

    private void initComponents(){
        tietEmail = findViewById(R.id.tie_main_signin_email_address);
        tietPassword = findViewById(R.id.tie_main_signin_password);
        btnCreateAccount = findViewById(R.id.btn_main_create_an_account);
        btnCreateAccount.setOnClickListener(getCreateAccountClickListener());
    }
}