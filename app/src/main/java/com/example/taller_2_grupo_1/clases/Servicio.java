package com.example.taller_2_grupo_1.clases;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class Servicio implements Parcelable {
    private long id;
    private String direccion;
    private Calendar fecha;
    private int medida;
    private int tipoServicio;

    public Servicio(String direccion, Calendar fecha, int medida, int tipoServicio) {
        this.direccion = direccion;
        this.fecha = fecha;
        this.medida = medida;
        this.tipoServicio = tipoServicio;
    }

    public Servicio(long id, String direccion, Calendar fecha, int medida, int tipoServicio) {
        this.id = id;
        this.direccion = direccion;
        this.fecha = fecha;
        this.medida = medida;
        this.tipoServicio = tipoServicio;
    }

    protected Servicio(Parcel in) {
        id = in.readLong();
        direccion = in.readString();
        medida = in.readInt();
        tipoServicio = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(direccion);
        dest.writeInt(medida);
        dest.writeInt(tipoServicio);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Servicio> CREATOR = new Creator<Servicio>() {
        @Override
        public Servicio createFromParcel(Parcel in) {
            return new Servicio(in);
        }

        @Override
        public Servicio[] newArray(int size) {
            return new Servicio[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public int getMedida() {
        return medida;
    }

    public void setMedida(int medida) {
        this.medida = medida;
    }

    public int getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(int tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getFechaString() {
        return this.fecha.get(Calendar.DAY_OF_MONTH) + "-" + (this.fecha.get(Calendar.MONTH) + 1) + "-" +
                this.fecha.get(Calendar.YEAR) + " " + this.fecha.get(Calendar.HOUR_OF_DAY) + ":" +
                this.fecha.get(Calendar.MINUTE);
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "id=" + id +
                ", direccion='" + direccion + '\'' +
                ", fecha=" + fecha +
                ", medida=" + medida +
                ", tipoServicio=" + tipoServicio +
                '}';
    }
}
