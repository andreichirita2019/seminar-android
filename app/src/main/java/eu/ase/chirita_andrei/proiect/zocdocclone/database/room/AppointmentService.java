package eu.ase.chirita_andrei.proiect.zocdocclone.database.room;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.chirita_andrei.proiect.zocdocclone.models.Appointment;
import eu.ase.chirita_andrei.proiect.zocdocclone.network.AsyncTaskRunner;
import eu.ase.chirita_andrei.proiect.zocdocclone.network.Callback;

public class AppointmentService {

    private final AppointmentDao appointmentDao;
    private final AsyncTaskRunner asyncTaskRunner;

    //nu are sens sa le cream de mai multe ori
    //am obligat sa fie create o singura data prin constructor
    public AppointmentService(Context context){
        this.appointmentDao = DatabaseManager.getInstance(context).getAppointmentDao();
        asyncTaskRunner = new AsyncTaskRunner();
    }

    //este acelasi obiect de Appointment, dar imbogatit cu id-ul din baza de date
    //callback - locatia unde primeste raspunsul
    //operatia asincrona - CALLABLE
    //ce scriem la callable, scriem si la callback
    public void insert(Appointment appointment, Callback<Appointment> activityThread){
        Callable<Appointment> insertOperation = new Callable<Appointment>() {
            @Override
            public Appointment call() throws Exception {
                //aici se realizeaza operatia de insert cu baza de date
                //ne aflam pe un alt fir de executie
                if(appointment == null || appointment.getId() > 0){
                    //id > 0 inseamna ca a mai fost inserat si nu putem sa o facem din nou
                    return null;
                }
                long id = appointmentDao.insert(appointment);
                if(id < 0){
                    //daca vine -1 inseamna ca avem probleme la bd, conexiunea s-a interupt etc
                    //sqlite nu arunca exceptii
                    return null;
                }
                appointment.setId(id);
                return appointment;
            }
        };
        asyncTaskRunner.executeAsync(insertOperation,activityThread);
    }

    //PASUL 2 - MERGEM IN SERVICE si definim metoda din Dao (fie ca e select * from fie ca e update, delete (ORICEEE))
    public void getAll(Callback<List<Appointment>> activityThread){
        Callable<List<Appointment>> getAllOperation = new Callable<List<Appointment>>() {
            @Override
            public List<Appointment> call() {
                //preluam lista
                return appointmentDao.getAll();
            }
        };

        //ruleaza-l pe un alt thread
        asyncTaskRunner.executeAsync(getAllOperation,activityThread);
    }

    public void update(Appointment appointment,Callback<Appointment> activityThread){
        Callable<Appointment> updateOperation = new Callable<Appointment>() {
            @Override
            public Appointment call(){
                if(appointment == null || appointment.getId() <= 0){
                    return null;
                }
                int count = appointmentDao.update(appointment);
                if(count <=0){
                    //fie nu a mers update-ul
                    //fie am dat un appointment cu id invalid
                    return null;
                }
                return appointment;
            }
        };

        //pornim procesarea asincrona
        asyncTaskRunner.executeAsync(updateOperation,activityThread);
    }

    public void delete(Appointment appointment, Callback<Boolean> activityThread){
        //tot ce se afla in Callable se executa pe un alt Thread
        Callable<Boolean> deleteOperation = new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                //prevalidari
                //obiectul sa existe si sa aiba un id
                if(appointment == null || appointment.getId() <= 0){
                    return false;
                }
                int count = appointmentDao.delete(appointment);
                return count >0;
            }
        };
        asyncTaskRunner.executeAsync(deleteOperation,activityThread);
    }
}
