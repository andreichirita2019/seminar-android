package eu.ase.chirita_andrei.proiect.zocdocclone.database.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import eu.ase.chirita_andrei.proiect.zocdocclone.models.Appointment;
import eu.ase.chirita_andrei.proiect.zocdocclone.util.DateConverter;

//pentru CRUD avem nevoie de adnotare (DAO)

@Database(entities = {Appointment.class}, exportSchema = false, version = 1) //version ne ajuta la rollback
//tabelele nu le definim noi printr-un tool
//daca vrem sa cream tabele, cream o clasa java care va reprezenta acea tabela
@TypeConverters({DateConverter.class})
//o sa gasesti niste tipuri de date incompatibile
//metodele adnotate cu TypeConverter le pot folosi
public abstract class DatabaseManager extends RoomDatabase {
    //clasa prin care definim 3 lucruri
    //1. proiectarea bazei de date
    //2. proiectarea tabelelor
    //3. cum sa obtinem o conexiune la BD catre o TABELA (users)

    //deschidem o singura conexiune - trebuie sa avem un singur obiect de tip DatabaseManager
    //conexiuni multiple = memory leaks, spatiu de memorie ineficient etc
    //conexiunea se poate lansa din mai multe activitati
    //SINGLETON - Design Pattern
    //el asigura crearea unei singure instante dintr-o clasa pe toata durata procesului

    private static DatabaseManager databaseManager; //nu putem apela constructorul clasei deoarece e abstract
    //room-ul ne creeaza automat si le vom folosi

    public static DatabaseManager getInstance(Context context){
      //singura metoda prin care putem lua o instanta de tip DatabaseManager
      if(databaseManager == null){
          //sincronizeaza apelurile
          //sincronizam cat timp cream o instanta de tip DatabaseManager si numai e null
          //trebuie sa il punem inafara if-ului
          synchronized (DatabaseManager.class){
              if(databaseManager == null){
                  //putem crea instanta de tip DM
                  //proiectarea bazei de date fara tabele
                  //fallback - motiv: sunt situatii cand ne razgandim asupra structurii unei tabele - va aplica modificari pentru noi
                  //insert-urile de dinainte : Room-ul va declansa la nivelul lui SQLite o migrare - nu va merge (int la string)
                  //Room va declansa fallback(fie definit de noi, mai complex, etc)
                  //cand nu reuseste sa faca migrarea, va sterge inregistrea respectiva
                  databaseManager = Room.databaseBuilder(context,DatabaseManager.class,"APPOINTMENTS_DB")
                          .fallbackToDestructiveMigration()
                          //.allowMainThreadQueries() // sa nu folosim niciodata - doar pt TESTARE
                          //operatiile cu bd trebuie sa fie executate pe fire paralele de executie - procesari la fel ca la Retea - deschidem un nou thread
                          .build();
              }
          }
      }
      return databaseManager;
    }

    //am acces la acea tabela
    public abstract AppointmentDao getAppointmentDao();
}
