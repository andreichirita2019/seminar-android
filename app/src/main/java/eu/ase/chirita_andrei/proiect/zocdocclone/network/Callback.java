package eu.ase.chirita_andrei.proiect.zocdocclone.network;

import org.json.JSONException;

import java.text.ParseException;

public interface Callback<R> {

    //implementare GENERICA
    //template
    //putem procesa pe absolut orice tip de clasa (String, Integer, Object etc)
    //avantaj: evitam sa scriem codul de foarte multe ori
    void runResultOnUiThead(R result) throws JSONException, ParseException;
}
