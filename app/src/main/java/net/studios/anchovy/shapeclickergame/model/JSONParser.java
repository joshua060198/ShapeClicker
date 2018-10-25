/*
    Shape Clicker Games v1.0
    Copyright (C) 2018  Anchovy Studios

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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
