package net.studios.anchovy.shapeclickergame.model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class JSONParser {

    private Gson gson;
    private static JSONParser instance;

    private JSONParser() {
        this.gson = new Gson();
    }

    public static JSONParser getInstance() {
        if(instance == null){
            instance = new JSONParser();
        }
        return instance;
    }

    public String parseUserToJson(ArrayList toBeParsed) {
        try {
            return this.gson.toJson(toBeParsed);
        }catch(Exception e){
            Log.d("Parsing error", "parseUserToJson: save user data");
            return "";
        }
    }

    public ArrayList<User> getUserListFromJson(String json){
        User[] arrayOfUser = this.gson.fromJson(json, User[].class);
        return new ArrayList<User>(Arrays.asList(arrayOfUser));
    }

    public String parsePlayStateToJson(PlayState toBeParsed) {
        try{
            return this.gson.toJson(toBeParsed);
        }catch(Exception e){
            Log.d("Parsing error", "parsePlayStateToJson: save state data");
            return "";
        }
    }

    public PlayState getPlayStateFromJson(String json){
        return this.gson.fromJson(json, PlayState.class);
    }
}
