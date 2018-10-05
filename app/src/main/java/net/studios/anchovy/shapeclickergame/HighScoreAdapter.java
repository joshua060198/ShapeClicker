package net.studios.anchovy.shapeclickergame;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.studios.anchovy.shapeclickergame.model.JsonParser;
import net.studios.anchovy.shapeclickergame.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
        this.sdf = new SimpleDateFormat("dd/mm/yy hh:mm:ss", Locale.getDefault());
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
        notifyDataSetChanged();
    }

    public String convertUserToString() {
        String res = "";
        for (User u:this.data) {
            res += u.toString();
            res += "\n";
        }

        return res;
    }

    public void loadUser(String listUser) {
        String users[] = listUser.split("\n");
        String temp[];
        for (String user : users) {
            temp = user.split(",");
            this.data.add(new User(temp[1], Integer.parseInt(temp[2]), Long.parseLong(temp[3]), temp[4]));
        }
    }

    private String convertTime(long time) {
        this.date.setTime(time);
        return this.sdf.format(this.date);
    }

    public ArrayList<User> getData() {
        return data;
    }
}
