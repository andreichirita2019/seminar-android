package eu.ase.chirita_andrei.proiect.zocdocclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import eu.ase.chirita_andrei.proiect.zocdocclone.R;
import eu.ase.chirita_andrei.proiect.zocdocclone.adapters.UserAdapter;
import eu.ase.chirita_andrei.proiect.zocdocclone.database.firebase.FirebaseService;
import eu.ase.chirita_andrei.proiect.zocdocclone.models.User;
import eu.ase.chirita_andrei.proiect.zocdocclone.network.Callback;

public class LoginActivity extends AppCompatActivity {

    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";

    private Button btnCreateAccount;
    private Button btnLogin;
    private TextInputEditText tietEmail;
    private TextInputEditText tietPassword;

    private ListView lvUsers;
    private List<User> users = new ArrayList<>();
    private int selectedUserIndex = -1;
    private FirebaseService firebaseService;
    private FloatingActionButton fabDelete;
    private FloatingActionButton fabSave;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        firebaseService = FirebaseService.getInstance();
        firebaseService.attachDataChangedListener(dataChangedCallback());
        viewDataFromSignUp();
    }

    private User updateUserFromView(String id) {
        User user = new User();
        user.setIdFirebase(id);
        user.setEmail(tietEmail.getText().toString());
        user.setPassword(tietPassword.getText().toString());
        return user;
    }


    private void viewDataFromSignUp(){
        Intent intent = getIntent();
        String email = intent.getStringExtra(EMAIL);
        String password = intent.getStringExtra(PASSWORD);
        tietEmail.setText(email);
        tietPassword.setText(password);
    }

    private View.OnClickListener getStartMainActivity(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener getCreateAccountClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        };
    }

    private void initComponents(){
        btnCreateAccount = findViewById(R.id.btn_login_create_an_account);
        btnLogin = findViewById(R.id.btn_login_sign_in);
        tietEmail = findViewById(R.id.tie_login_signin_email_address);
        tietPassword = findViewById(R.id.tie_login_signin_password);
        btnCreateAccount.setOnClickListener(getCreateAccountClickListener());
        btnLogin.setOnClickListener(getStartMainActivity());
        fabDelete = findViewById(R.id.main_fab_delete);
        fabSave = findViewById(R.id.main_fab_save);
        lvUsers = findViewById(R.id.main_lv_users);
        addListViewUsersAdapter();
        fabSave.setOnClickListener(getSaveUserClickEvent());
        fabDelete.setOnClickListener(getDeleteUserClickEvent());
        lvUsers.setOnItemClickListener(getSelectStudentClickEvent());
    }

    //----------------------------------- TEST FIREBASE ------------------------------------------------------

    private void addListViewUsersAdapter() {
        UserAdapter adapter = new UserAdapter(getApplicationContext(), R.layout.lv_row_view_users_custom_adapter_firebase,
                users, getLayoutInflater());
        lvUsers.setAdapter(adapter);
    }

    private void notifyListViewStudentAdapter() {
        UserAdapter adapter = (UserAdapter) lvUsers.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private Callback<List<User>> dataChangedCallback() {
        return new Callback<List<User>>() {
            @Override
            public void runResultOnUiThead(List<User> result) {
                if (result != null) {
                    users.clear();
                    users.addAll(result);
                    notifyListViewStudentAdapter();
                }
            }
        };
    }

    private View.OnClickListener getSaveUserClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUserIndex == -1) {
                    User user = updateUserFromView(null);
                    firebaseService.insert(user);
                } else {
                    User user = updateUserFromView(users.get(selectedUserIndex).getIdFirebase());
                    firebaseService.update(user);
                }
            }
        };
    }

    private View.OnClickListener getDeleteUserClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUserIndex != -1) {
                    firebaseService.delete(users.get(selectedUserIndex));
                }
            }
        };
    }

    private AdapterView.OnItemClickListener getSelectStudentClickEvent() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUserIndex = position;
                User user = users.get(position);
                tietEmail.setText(user.getEmail());
                tietPassword.setText(user.getPassword());
            }
        };
    }
}