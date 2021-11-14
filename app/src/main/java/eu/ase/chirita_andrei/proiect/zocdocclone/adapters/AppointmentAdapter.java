package eu.ase.chirita_andrei.proiect.zocdocclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

import eu.ase.chirita_andrei.proiect.zocdocclone.models.Appointment;

public class AppointmentAdapter extends ArrayAdapter<Appointment> {

    private Context context;
    private int resource;
    private List<Appointment> appointmentList;
    private LayoutInflater layoutInflater;

    //Constructor
    public AppointmentAdapter(@NonNull Context context, int resource, @NonNull List<Appointment> objects, LayoutInflater layoutInflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.appointmentList = objects;
        this.layoutInflater = layoutInflater;
    }

}
