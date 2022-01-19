package eu.ase.chirita_andrei.proiect.zocdocclone.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Covid19 implements Parcelable {

    private int totalInfected;
    private int totalTested;
    private int totalRecovered;
    private int totalDeceased;
    private String lastUpdatedSource;

    public Covid19(){

    }

    public Covid19(int totalInfected, int totalTested, int totalRecovered, int totalDeceased, String lastUpdatedSource) {
        this.totalInfected = totalInfected;
        this.totalTested = totalTested;
        this.totalRecovered = totalRecovered;
        this.totalDeceased = totalDeceased;
        this.lastUpdatedSource = lastUpdatedSource;
    }

    public Covid19(int totalInfected, int totalTested, int totalRecovered, int totalDeceased) {
        this.totalInfected = totalInfected;
        this.totalTested = totalTested;
        this.totalRecovered = totalRecovered;
        this.totalDeceased = totalDeceased;
    }

    public int getTotalInfected() {
        return totalInfected;
    }

    public void setTotalInfected(int totalInfected) {
        this.totalInfected = totalInfected;
    }

    public int getTotalTested() {
        return totalTested;
    }

    public void setTotalTested(int totalTested) {
        this.totalTested = totalTested;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public int getTotalDeceased() {
        return totalDeceased;
    }

    public void setTotalDeceased(int totalDeceased) {
        this.totalDeceased = totalDeceased;
    }

    public String getLastUpdatedSource() {
        return lastUpdatedSource;
    }

    public void setLastUpdatedSource(String lastUpdatedSource) {
        this.lastUpdatedSource = lastUpdatedSource;
    }


    @Override
    public String toString() {
        return "Covid19{" +
                "totalInfected=" + totalInfected +
                ", totalTested=" + totalTested +
                ", totalRecovered=" + totalRecovered +
                ", totalDeceased=" + totalDeceased +
                ", lastUpdatedSource=" + lastUpdatedSource +
                '}';
    }

    private Covid19(Parcel in) {
        totalInfected = in.readInt();
        totalTested = in.readInt();
        totalRecovered = in.readInt();
        totalDeceased = in.readInt();
        //lastUpdatedSource = in.readString();
    }

    public static Creator<Covid19> CREATOR = new Creator<Covid19>() {
        @Override
        public Covid19 createFromParcel(Parcel in) {
            return new Covid19(in);
        }

        @Override
        public Covid19[] newArray(int size) {
            return new Covid19[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalInfected);
        dest.writeInt(totalTested);
        dest.writeInt(totalRecovered);
        dest.writeInt(totalDeceased);
        //dest.writeString(lastUpdatedSource);
    }
}
