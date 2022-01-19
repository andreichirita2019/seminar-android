package eu.ase.chirita_andrei.proiect.zocdocclone.util;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private static final SimpleDateFormat formatterCovidJSON = new SimpleDateFormat("dd/MM",Locale.UK);


    @TypeConverter
    public static Date fromString(String value) {
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    @TypeConverter
    public static String fromDate(Date value) {
        if (value == null) {
            return null;
        }
        return formatter.format(value);
    }

   public static Date fromDateJSON(String value){
        try{
            return formatterCovidJSON.parse(value);
        }catch(ParseException e){
            return null;
        }
   }
}
