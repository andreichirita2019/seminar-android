package eu.ase.chirita_andrei.proiect.zocdocclone.network;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import eu.ase.chirita_andrei.proiect.zocdocclone.R;


public class AsyncTaskRunner {

    //mai bine folosim EXECUTOR (Concurency)
    //EXECUTOR: distribui si porni thread-urile ptca aplicatia sa fie cat mai eficienta
    //executorul primeste ca parametru de intrare o implementare de tipul interfetei Runnable
    //executorul nu ii da start atunci ci il adauga intr-o coada de asteptare, iar el va stii in urma configuratiei (pe care o facem noi) cate thread-uri putem duce in paralel
    //va stii care e capacitatea maxima, isi face coada si va decide (RoundRobin) ce thread sa porneasca
    //Handler va fi puntea intre thread-ul principal si cel secundar deschis prin intermediul lui Executor
    //thread-ul principal incarca toate elementele vizuale pe ecran
    //Callable - call - metoda nu are niciun param, dar returneaza un rezultat (R,T etc) - HTTP MANAGER
    //Callable - operatie asincrona
    //Callback - rezultatul de la Callable (String sa zicem) - JSONParser
    //Callback - operatie pe MAIN Thread

    /*clasa aceasta are nevoie de:
        1. Handler - reprezinta puntea noastra de legature intre un Thread Custom si Thread-ul principal
        2. Executor in loc de Thread
     */

    //care este treaba mea? sa spun ceea ce vreau sa procesez asincron (Callable<R> asyncOperation) si codul in care primesc procesarea (Callback)

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Executor executor = Executors.newCachedThreadPool();
    //newCachedThreadPool - ii spunem ca executorul are la dispozitie un nr de thread-uri = capacitatea maxima a sistemului

    //aici facem procesarea asincrona
    public <R> void executeAsync(Callable<R> asyncOperation, Callback<R> mainThreadOperation){
        //metoda pe care noi o definim ca sa ii facem procesare asincrona
        /*trebuie sa ii zic lu AsyncTaskRunner 2 lucruri:
            1. cine este Callable
            2. cine este Callback - zona unde ii trimit rezultatul
         */
        try{
            //creez un Thread secundar
            //executorul primeste o implementare de tip Runnable
            //runAsyncOperation va trebui sa imi creeze acea instanta de tip Runnable de care are nevoie execute
            executor.execute(runAsyncOperation(asyncOperation,mainThreadOperation));
        }catch(Exception e){
            Log.i("AsyncTaskRunner", "failed");
        }
    }

    private <R> Runnable runAsyncOperation(Callable<R> asyncOperation, Callback<R> mainThreadOperation) {
        return new Runnable() {
            @Override
            public void run() {
                try{
                    //aici ma aflu in thread-ul secundar de unde trebuie sa preiau informatia
                    //aici trebuie sa iau rezultatul pe care mi-l da operatia asincrona
                    R result = asyncOperation.call(); //aceasta era acea operatie greoaie
                    //handler primeste un Runnable
                    handler.post(runMainThreadOperation(result,mainThreadOperation));
                    //rolul celui de al doilea thread: sa ia rezultatul (R result) si sa il trimita lui MainThreadOperation - catre referinta din activitatea principala

                }catch (Exception e){
                    Log.i("AsyncTaskRunner", "failed");
                }
            }
        };
    }

    private <R> Runnable runMainThreadOperation(R result, Callback<R> mainThreadOperation) {
        //care are ca scop sa imi intoarca o implementare de tipul Runnable
        //este pentru ca POST din Handler accepta un Runnable - face un fel de JOIN
        return new Runnable() {
            @Override
            public void run() {
                mainThreadOperation.runResultOnUiThead(result);
            }
        };
    }
}
