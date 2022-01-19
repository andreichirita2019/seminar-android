package eu.ase.chirita_andrei.proiect.zocdocclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.chirita_andrei.proiect.zocdocclone.R;
import eu.ase.chirita_andrei.proiect.zocdocclone.models.User;

public class UserAdapter extends ArrayAdapter<User> {

    private Context context;
    private int resource;
    private List<User> users;
    private LayoutInflater inflater;

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.users = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        User user = users.get(position);
        if (user != null) {
            addEmail(view, user.getEmail());
            addPassword(view, user.getPassword());
        }
        return view;
    }

    private void addEmail(View view, String email) {
        TextView textView = view.findViewById(R.id.tv_row_email);
        populateTextViewContent(textView, email);
    }

    private void addPassword(View view, String password) {
        TextView textView = view.findViewById(R.id.tv_row_password);
        populateTextViewContent(textView, password);
    }

    private void populateTextViewContent(TextView textView, String value) {
        if (value != null && !value.isEmpty()) {
            textView.setText(value);
        } else {
            textView.setText("-");
        }
    }


}
