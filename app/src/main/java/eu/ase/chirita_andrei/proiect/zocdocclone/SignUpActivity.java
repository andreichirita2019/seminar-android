package eu.ase.chirita_andrei.proiect.zocdocclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.Date;

import eu.ase.chirita_andrei.proiect.zocdocclone.util.DateConverter;
import eu.ase.chirita_andrei.proiect.zocdocclone.util.GenderType;
import eu.ase.chirita_andrei.proiect.zocdocclone.util.User;

public class SignUpActivity extends AppCompatActivity {

    public static final String ADD_USER_KEY = "ADD_USER_KEY";

    private TextView tvSignIn;
    private TextInputEditText tietEmail;
    private TextInputEditText tietConfirmEmail;
    private TextInputEditText tietPassword;
    private TextInputEditText tietName;
    private TextInputEditText tietDate;
    private RadioGroup rgGenderType;
    private Intent intent;
    private Button btnSave;

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
                try {
                    if(isValid()){
                        /*
                        //   User user = buildUserFromComponents();
                             intent.putExtra(ADD_USER_KEY,user);
                             setResult(RESULT_OK,intent);
                             finish();
                         */
                        intent.putExtra(ADD_USER_KEY, tietEmail.getText().toString());
                        intent.putExtra(ADD_USER_KEY, tietPassword.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private User buildUserFromComponents() throws ParseException {
        String email = tietEmail.getText().toString();
        String confirmEmail = tietEmail.getText().toString();
        String password = tietPassword.getText().toString();
        String name = tietName.getText().toString();
        Date dateBirth = DateConverter.fromString(tietDate.getText().toString().trim());
        GenderType genderType = GenderType.MALE;
        if(rgGenderType.getCheckedRadioButtonId()==R.id.rb_sign_up_activity_sex_female){
            genderType = GenderType.FEMALE;
        }
        return new User(email,confirmEmail,password,name,dateBirth,genderType);
    }

    private boolean isValid() throws ParseException {
        if(tietEmail.getText() == null) {
            Toast.makeText(getApplicationContext(),"Invalid email. Please check your input!", Toast.LENGTH_LONG).show();
            return false;
        }
        if(tietConfirmEmail.getText().toString() != tietEmail.getText().toString() || tietConfirmEmail.getText() == null){
            Toast.makeText(getApplicationContext(), "Confirm your email!", Toast.LENGTH_LONG).show();
            return false;
        }
        if(tietPassword.getText() == null || tietPassword.getText().toString().isEmpty()
                || tietPassword.getText().toString().trim().length() < 5) {
            Toast.makeText(getApplicationContext(), "Invalid password. Minimum 5 characters!", Toast.LENGTH_LONG).show();
            return false;
        }
        if(tietName.getText() == null || tietName.getText().toString().trim().length() < 3) {
            Toast.makeText(getApplicationContext(),"Invalid name. Minimum 3 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        if(tietDate.getText() == null || DateConverter.fromString(tietDate.getText().toString().trim()) == null) {
            Toast.makeText(getApplicationContext(),"Invalid date birth. Accepted format date dd/MM/yyyy", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void initComponents(){
        tvSignIn = findViewById(R.id.tv_sign_up_activity_already_have);
        tietEmail = findViewById(R.id.tiet_sign_up_activity_email);
        tietConfirmEmail = findViewById(R.id.tiet_sign_up_activity_confirm_email);
        tietPassword = findViewById(R.id.tiet_sign_up_activity_password);
        tietName = findViewById(R.id.tiet_sign_up_activity_name);
        tietEmail = findViewById(R.id.tiet_sign_up_activity_date_birth);
        rgGenderType = findViewById(R.id.rg_sign_up_activity_sex);
        btnSave = findViewById(R.id.btn_sign_up_save);
        btnSave.setOnClickListener(getSaveUserClickListener());
    }
}