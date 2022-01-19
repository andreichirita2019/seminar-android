package eu.ase.chirita_andrei.proiect.zocdocclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

import eu.ase.chirita_andrei.proiect.zocdocclone.R;
import eu.ase.chirita_andrei.proiect.zocdocclone.models.Appointment;

public class AppointmentAdapter extends ArrayAdapter<Appointment> {

    private Context context;
    private int resource;
    private List<Appointment> appointmentList;
    private LayoutInflater layoutInflater;

    public AppointmentAdapter(@NonNull Context context, int resource, @NonNull List<Appointment> objects, LayoutInflater layoutInflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.appointmentList = objects;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource,parent,false);
        Appointment appointment = appointmentList.get(position);
        if(appointment == null){
            return view;
        }
        addComponentsFromRowView(view,
                appointment.getMedicalCategory(),
                appointment.getPatientLocation(),
                appointment.getDoctorName(),
                appointment.getDateOfAppointment(),
                appointment.getHourOfAppointment());
        return view;
    }

    private void addComponentsFromRowView(View view, String medicalCategory, String patientLocation,
                                          String doctorName, String dateOfAppointment, String hourOfAppointment){
        TextView tvMedicalCategory = view.findViewById(R.id.tv_row_medical_category);
        populateTextViewContent(tvMedicalCategory,medicalCategory);

        TextView tvPatientLocation = view.findViewById(R.id.tv_row_location);
        populateTextViewContent(tvPatientLocation,patientLocation);

        TextView tvDoctorName = view.findViewById(R.id.tv_row_doctor_name);
        populateTextViewContent(tvDoctorName,doctorName);

        TextView tvDateOfAppointment = view.findViewById(R.id.tv_row_date);
        populateTextViewContent(tvDateOfAppointment,dateOfAppointment);

        TextView tvHourOfAppointment = view.findViewById(R.id.tv_row_hour);
        populateTextViewContent(tvHourOfAppointment,hourOfAppointment);
    }

    private void populateTextViewContent(TextView textView, String value) {
        if (value != null && !value.trim().isEmpty()) {
            textView.setText(value);
        } else {
            textView.setText("-");
        }
    }

}
