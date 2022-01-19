package eu.ase.chirita_andrei.proiect.zocdocclone.database.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import eu.ase.chirita_andrei.proiect.zocdocclone.models.User;
import eu.ase.chirita_andrei.proiect.zocdocclone.network.Callback;

    //incercam sa realizam un singur punct de control si o singura instanta de interactiune cu Firebase - SINGLETON
    //pentru fiecare nod principal trebuie sa definim un obiect de tipul DatabaseReference (acest obiect va pointa catre nodul principal)
    //daca am mai multe noduri, de fiecare data trebuie sa definim un obiect de tip DatabaseRefernce, dar si o constanta cu numele nodului respectiv
public class FirebaseService {
    //node
    private static final String USER_REFERENCE = "users";
    //instanta DatabaseReference
    private final DatabaseReference databaseReference;
    //un singur punct de conexiune catre Firebase
    private static FirebaseService firebaseService;

    private FirebaseService(){
        //initializam referinta catre acel nod
        //iti ofera o conexiune directa catre tot spatiul RealTimeDatabase prin intermediul metodei getInstance
        // + getReference (daca nu gaseste nodul, il creeaza el)
        // + node
        databaseReference = FirebaseDatabase.getInstance().getReference(USER_REFERENCE);
    }

    public static FirebaseService getInstance(){
        if(firebaseService == null){
            synchronized (FirebaseService.class){
                if(firebaseService == null){
                    //se realizeaza prin intermediul constructorului
                    firebaseService = new FirebaseService();
                }
            }
        }
        return firebaseService;
    }

    public void insert(User user){
        //la generare voi adauga o cheie (un ID) exact ca la SQLite chiar daca este o baza de date non-relationara
        //sub acea cheie, voi seta informatia pe care o vreau
        if(user == null || user.getIdFirebase() != null && user.getIdFirebase().trim().isEmpty()){
            return;
        }
        //cu push mi se adauga o cheie
        //cheia (id-ul) este String in Firebase
        //sub nodul creat se va genera o cheie, dar fara informatii
        //getKey ne da valoarea
        String id = databaseReference.push().getKey();
        user.setIdFirebase(id);
        //sub cheia pe care am generat-o, voi atasa valoarea obiectului meu prin setValue (in spate, creeaza un HashMap de tipul cheie-valoare)
        databaseReference.child(user.getIdFirebase()).setValue(user);
    }

    public void update(User user) {
        //avem cheia si nu o mai generam (spre deosebire de INSERT)
        if (user == null || user.getIdFirebase() == null || user.getIdFirebase().trim().isEmpty()) {
            return;
        }
        //totul se intampla ASINCRON
        databaseReference.child(user.getIdFirebase()).setValue(user);
    }

    public void delete(User user){
        if(user == null || user.getIdFirebase() == null || user.getIdFirebase().trim().isEmpty()){
            return;
        }
        databaseReference.child(user.getIdFirebase()).removeValue();
    }

    //SELECT nu exista la FIREBASE
    //RealTimeDatabase ne pune la dispozitie niste Evenimente
    //app-ul se inregistreaza la Firebase ca vrea sa asculte toate modificarile care se executa asupra unui nod parinte
    //atunci cand Firebase inregistreaza acel -----, va trimite semnale catre aplicatie
    //de fiecare data cand apar modificari (adaugare nod nou, modificare nod, stergere etc), Firebase va trimite niste semnale catre app-ul nostru
    //se inverseaza modul de comunicare
    //app-ul nostru trebuie sa ataseze un ascultator (DataChangedListener)

    public void attachDataChangedListener(Callback<List<User>> callback){
        //cand atasam evenimentul, Firebase ne va trimite toate informatiile pe care le are in nod -> simulam SELECT-ul
        //add-ul se apeleaza pentru toate (INSERT,DELETE,UPDATE)
        databaseReference.addValueEventListener(new ValueEventListener() {
            //addvalue il setez la nivelul referintei
            //inseamna ca evenimentul va asculta pt toate modificarile pe care le facem asupra nodului parinte
            //indiferent ce nod se modifica, firebase-ul va trimite aici semnale (fie ca adaugam,stergem, modificam etc)
            //responsabilitatea noastra  = convertim informatiile din lista de JSON (cea primita), intr-o lista de obiecte proprii
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //va trebui sa impingem informatiile pe care ni le trimite Firebase catre activitatea principala
                //FirebaseService si MainActivity - pt populare ListView
                List<User> userList = new ArrayList<>();
                for(DataSnapshot data: snapshot.getChildren()){
                    //iau toti copiii
                    //cand ajung la nodul tinta pot obtine un Obiect propriu
                    //getValue - ii dam ce tip de Obiect vrem ca sa construiasca Snapshot-ul pt noi
                    //ii dam clasa - trebuie sa avem ( 1. constructorul fara parametri si 2.getteri si setteri) ->  cu asta lucreaza DataSnapshot
                    User user = data.getValue(User.class);
                    userList.add(user);
                }
                //ruleaza pe un alt thread
                callback.runResultOnUiThead(userList);
            }

            //daca are probleme nu se va apela onDataChanged ci onCancelled
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //daca este vreo problema, se va notifica aici
            }
        });

        //daca vrem sa ascultam la nivelul unui copil, putem atasa un eveniment de ascultare la nivelul copilului
        //databaseReference.child("dadsadasd").addValueEventListener()
    }

}
