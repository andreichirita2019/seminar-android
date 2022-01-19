package eu.ase.chirita_andrei.proiect.zocdocclone.database.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.chirita_andrei.proiect.zocdocclone.models.Appointment;
import eu.ase.chirita_andrei.proiect.zocdocclone.models.User;

@Dao
public interface AppointmentDao {
    @Insert
        //room-ul cand vede insert
        //INSERT into TABLE NAME - ob primit ca parametru (la adnotarea Entity)
        //la values scaneaza fiecare Column Info - pt fiecare pune valoare atribului (getDate, getName etc)
        //Dao trebuie initilizat si obtinut prin intermediul DatabaseManager
    long insert(Appointment appointment);


    @Query("SELECT * FROM appointments")
    List<Appointment> getAll();

    @Update
    //ne returneaza numarul de randuri afectate
    int update(Appointment appointment);

    @Delete
    //acelasi lucru ca la Update
    int delete(Appointment appointment);


}
