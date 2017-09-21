package com.dev.ramon.sqlitecrud.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ramon Dev on 21/09/2017.
 */

public class Course implements Parcelable {
    private String nome;
    private String descricao;
    private int classHours;

    public Course() {
    }

    protected Course(Parcel in) {
        nome = in.readString();
        descricao = in.readString();
        classHours = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
        parcel.writeString(descricao);
        parcel.writeInt(classHours);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getClassHours() {
        return classHours;
    }

    public void setClassHours(int classHours) {
        this.classHours = classHours;
    }
}
