package eu.ase.chirita_andrei.proiect.zocdocclone.network;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class HttpManager implements Callable<String> {

    //clasa URL si obiectul URL ne asigura o serie de validari
    //construire obiect URL (parametru uRL sub forma de string)
    //el in spate verifica daca URL-ul primit este VALID (https://...) sau daca locatia este valida
    private URL url;
    //punte de comunicare intre aplicatia android si resursa (url, fisier, server, baza de date la distanta etc)
    private HttpURLConnection connection;
    //preluare de date
    private InputStream inputStream; //secventa continua de bytes
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    private final String urlAddress; //il facem final pentru a avea instante diferite de url; 1 la 1

    public HttpManager(String urlAddress) {
        this.urlAddress = urlAddress;
    } //facem un constructor pentru a primi acel url


    //continutul pe care il aducem din URL -> scopul: sa intoarcem continutul sub forma de string
    public String process() {
        try {
            return getResultFromHttp();
        } catch (IOException e) {
            e.printStackTrace();
        } finally { //se executa indiferent daca s-a executat TRY-ul sau nu
            //lucrand cu clase de tipul .net ar trebui sa asiguram de fiecare data inchiderea acestora
            //pentru a evita aparitia de memory leak
            closeConnections();
        }
        return null;
    }

    @Override
    public String call() throws Exception {
        //va trebui sa ii spunem ce sa se ruleze cand se va apela
        return process();
    }

    @NonNull
    private String getResultFromHttp() throws IOException {
        url = new URL(urlAddress);
        //deschid conexiunea deoarece am primit URL-ul (app si resursa - canal de comunicare)
        connection = (HttpURLConnection) url.openConnection();
        //procesare informatie
        inputStream = connection.getInputStream();
        inputStreamReader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(inputStreamReader);
        //concatenez fiecare linie (din bufferedReader) in object-ul de tip StringBuilder
        StringBuilder result = new StringBuilder();
        String line;
        //atribuire in-line impreuna cu o validare de null
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    private void closeConnections() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.disconnect();
    }

}
