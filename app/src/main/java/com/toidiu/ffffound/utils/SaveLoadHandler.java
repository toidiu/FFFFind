package com.toidiu.ffffound.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class SaveLoadHandler<T> {

    private final Type type;
    private java.io.File File;

    public SaveLoadHandler(Type type, File file){
        this.type = type;
        this.File = file;
        //Log.d("------TYPE--------",           this.type.toString());
    }

    public void saveData(T data){
        Gson gson = new Gson();
        String write = gson.toJson(data, this.type);
        Log.d("Write", write);

//Log.d("TEST","WRITING TO FILE: " + file.getAbsolutePath());
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(File, false);

            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(write);
            bw.close();
        } catch (IOException e) {
            Log.e("TEST","EXCEPTION WHEN WRITING FILE");
            e.printStackTrace();
        }
    }

    public T loadData() {
        StringBuilder builder = new StringBuilder("");

        try {
            FileReader fileReader = new FileReader(File);
            BufferedReader br = new BufferedReader(fileReader);

            String line;
            while((line = br.readLine()) != null){
                builder.append(line);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        if (builder == null){
            return null;
        }
        Gson gson = new Gson();
        Log.d("READ", builder.toString());

        return gson.fromJson(builder.toString(), this.type);
    }

}
