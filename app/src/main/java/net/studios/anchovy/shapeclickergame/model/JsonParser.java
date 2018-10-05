package net.studios.anchovy.shapeclickergame.model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

public class JsonParser {

    private Gson gson;
    private static JsonParser instance;
    private PreferenceLoader loader;

    private JsonParser() {
        this.gson = new Gson();
        this.loader = PreferenceLoader.getInstance();
    }

    public static JsonParser getInstance() {
        if(instance == null){
            instance = new JsonParser();
        }
        return instance;
    }

    public String parseUserToJson(ArrayList toBeParsed) {
        try {
            String data = this.gson.toJson(toBeParsed);
            this.loader.saveUserData(data);
            return data;
        }catch(Exception e){
            Log.d("Parsing error", "parseUserToJson: save user data");
            return "";
        }
    }

    public ArrayList<User> getUserListFromJson(){
        String s = this.loader.loadUserData();
        return this.gson.fromJson(s, ArrayList.class);
    }

    public String parsePlayStateToJson(PlayState toBeParsed) {
        try{
            String data = this.gson.toJson(toBeParsed);
            this.loader.saveStateData(data);
            return data;
        }catch(Exception e){
            Log.d("Parsing error", "parsePlayStateToJson: save state data");
            return "";
        }
    }

    public PlayState getPlayStateFromJson(){
        String s = this.loader.loadStateData();
        return this.gson.fromJson(s, PlayState.class);
    }
}
