package eu.ase.chirita_andrei.proiect.zocdocclone;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";

    private Button btnCreateAccount;
    private TextInputEditText tietEmail;
    private TextInputEditText tietPassword;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
        viewDataFromSignUp();
    }

    private void viewDataFromSignUp(){
        intent = getIntent();
        String email = intent.getStringExtra(EMAIL);
        String password = intent.getStringExtra(PASSWORD);
        tietEmail.setText(email);
        tietPassword.setText(password);
    }

    private View.OnClickListener getCreateAccountClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        };
    }

    private void initComponents(){
        btnCreateAccount = findViewById(R.id.btn_main_create_an_account);
        tietEmail = findViewById(R.id.tie_main_signin_email_address);
        tietPassword = findViewById(R.id.tie_main_signin_password);
        btnCreateAccount.setOnClickListener(getCreateAccountClickListener());
    }
}