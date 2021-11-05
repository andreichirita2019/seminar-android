package eu.ase.chirita_andrei.proiect.zocdocclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

import eu.ase.chirita_andrei.proiect.zocdocclone.util.DateConverter;
import eu.ase.chirita_andrei.proiect.zocdocclone.util.GenderType;
import eu.ase.chirita_andrei.proiect.zocdocclone.util.User;

public class SignUpActivity extends AppCompatActivity {

    public static final String ADD_USER_KEY = "ADD_USER_KEY";
    public static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private TextView tvSignIn; //go to LoginActivity

    private TextInputEditText tietEmail;
    private TextInputEditText tietConfirmEmail;
    private TextInputEditText tietPassword;
    private TextInputEditText tietName;
    private TextInputEditText tietDate;
    private RadioGroup rgGenderType;
    private Button btnSave; //go to LoginActivity with 2 values (email and password)
    private CheckBox cbSignUpPleaseRead;
    private CheckBox cbSignUpHaveAccept;

    private String email;
    private String password;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initComponents();
    }

    private View.OnClickListener getSaveUserClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    sendData();
                    finish();
                }
            }
        };
    }

    private void sendData(){
        String email = tietEmail.getText().toString().trim();
        String password = tietPassword.getText().toString().trim();
        intent = new Intent(getApplicationContext(),LoginActivity.class);
        intent.putExtra(LoginActivity.EMAIL,email);
        intent.putExtra(LoginActivity.PASSWORD,password);
        startActivity(intent);
    }

    private boolean isValid() {
        if (tietEmail.getText() == null
                || tietEmail.getText().toString().trim().isEmpty()
                || !tietEmail.getText().toString().trim().matches(EMAIL_PATTERN)) {
            Toast.makeText(getApplicationContext(),
                   "Invalid email. Try again!",
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        if (tietConfirmEmail.getText() == null
                || tietConfirmEmail.getText().toString().trim().isEmpty()
                || !tietConfirmEmail.getText().toString().trim().equals(tietEmail.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),
                    "Please confirm your email with exact same email address. Try again!",
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        if (tietPassword.getText() == null || tietPassword.getText().toString().trim().isEmpty()
                || Integer.parseInt(tietPassword.getText().toString().trim()) < 8) {
            Toast.makeText(getApplicationContext(),
                    "Invalid password. Minimum 8 characters. Try again!",
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }

        if (tietName.getText() == null || tietName.getText().toString().trim().length() < 3) {
            Toast.makeText(getApplicationContext(),
                    "Invalid name. Minimum 3 characters. Try again!",
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        if (tietDate.getText() == null
                || DateConverter.fromString(tietDate.getText().toString().trim()) == null) {
            Toast.makeText(getApplicationContext(),
                    "Invalid date. Accepted format dd/MM/yyyy. Try again!",
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }

        return true;

    }

    private void initComponents(){
        tietEmail = findViewById(R.id.tiet_sign_up_activity_email);
        tietConfirmEmail = findViewById(R.id.tiet_sign_up_activity_confirm_email);
        tietPassword = findViewById(R.id.tiet_sign_up_activity_password);
        tietName = findViewById(R.id.tiet_sign_up_activity_name);
        tietDate = findViewById(R.id.tiet_sign_up_activity_date_birth);
        rgGenderType = findViewById(R.id.rg_sign_up_activity_sex);
        btnSave = findViewById(R.id.btn_sign_up_save);
        btnSave.setOnClickListener(getSaveUserClickListener());
    }
}