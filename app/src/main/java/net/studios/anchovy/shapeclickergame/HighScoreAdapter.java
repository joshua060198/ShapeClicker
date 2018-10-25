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

package net.studios.anchovy.shapeclickergame;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.studios.anchovy.shapeclickergame.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class HighScoreAdapter extends BaseAdapter {

    private ArrayList<User> data;
    private LayoutInflater layoutInflater;
    private Date date;
    private SimpleDateFormat sdf;

    public HighScoreAdapter(LayoutInflater layoutInflater) {
        this.data = new ArrayList<>();
        this.layoutInflater = layoutInflater;
        this.date = new Date();
        this.sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss", Locale.getDefault());
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Object getItem(int position) {
        return this.data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HighScoreViewHolder vh;
        if (convertView == null) {
            View v = this.layoutInflater.inflate(R.layout.highscore_item, null);
            TextView name = v.findViewById(R.id.user_name), score = v.findViewById(R.id.user_score), lastPlayed = v.findViewById(R.id.user_last_played);
            ImageView pic = v.findViewById(R.id.user_pic);
            name.setText(this.data.get(position).getName());
            score.setText(String.format(Locale.getDefault(), "%d", this.data.get(position).getScore()));
            lastPlayed.setText(convertTime(this.data.get(position).getLastPlayed()));
            pic.setImageBitmap(BitmapFactory.decodeFile(this.data.get(position).getPicturePath()));

            vh = new HighScoreViewHolder(pic, name, score, lastPlayed);

            v.setTag(vh);

            return v;
        } else {
            vh = (HighScoreViewHolder) convertView.getTag();
            vh.getPicture().setImageBitmap(BitmapFactory.decodeFile(this.data.get(position).getPicturePath()));
            vh.getName().setText(this.data.get(position).getName());
            vh.getScore().setText(String.format(Locale.getDefault(), "%d", this.data.get(position).getScore()));
            vh.getLastPlayed().setText(convertTime(this.data.get(position).getLastPlayed()));

            return convertView;
        }
    }

    public void addUser (String name, String path, long lastPlayed, int score) {
        User newUser = new User(name, score, lastPlayed, path);
        this.data.add(newUser);
        Collections.sort(this.data);
        checkMaxUser();
        notifyDataSetChanged();
    }

    public ArrayList<User> getData() {
        return data;
    }

    public void loadData(ArrayList<User> data) {
        if (data != null) {
            this.data = data;
            Collections.sort(this.data);
        }
        checkMaxUser();
        notifyDataSetChanged();
    }

    private String convertTime(long time) {
        this.date.setTime(time);
        return this.sdf.format(this.date);
    }

    private void checkMaxUser() {
        while (this.data.size() > 5) {
            this.data.remove(this.data.size()-1);
        }
    }
}
