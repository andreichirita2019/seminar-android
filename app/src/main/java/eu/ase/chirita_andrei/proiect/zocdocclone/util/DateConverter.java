package eu.ase.chirita_andrei.proiect.zocdocclone.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    public static Date fromString(String value) throws ParseException {
        return formatter.parse(value);
    }

    public static String fromDate(Date value){
        if(value == null){
            return null;
        }
        return formatter.format(value);
    }
}
