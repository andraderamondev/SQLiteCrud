package com.dev.ramon.sqlitecrud.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ramon Dev on 21/09/2017.
 */

public class Course implements Parcelable {
    private int id;
    private String name;
    private String description;
    private int classHours;

    public Course() {
    }

    protected Course(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        classHours = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descricao) {
        this.description = descricao;
    }

    public int getClassHours() {
        return classHours;
    }

    public void setClassHours(int classHours) {
        this.classHours = classHours;
    }
}
