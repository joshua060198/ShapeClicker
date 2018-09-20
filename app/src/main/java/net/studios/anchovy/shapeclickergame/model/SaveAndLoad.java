package net.studios.anchovy.shapeclickergame.model;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveAndLoad implements SaverAndLoader<String> {

    private static final String HEADER = "picture,name,score,last_played";
    private File file;
    private FileOutputStream writer;
    private FileInputStream reader;

    public SaveAndLoad(Context context) throws IOException {
        this.file = new File(context.getExternalFilesDir(null).getAbsolutePath() + File.separator + "pembelian.csv");
        this.file.createNewFile();
    }

    @Override
    public void save(String data) throws IOException {
        this.writer = new FileOutputStream(this.file);
        this.writer.write(HEADER.getBytes());
        this.writer.write(data.getBytes());
        this.writer.flush();
        this.writer.close();
    }

    @Override
    public String load() throws IOException {
        StringBuilder res = new StringBuilder("");
        byte[]buffer = new byte[1024];
        this.reader = new FileInputStream(this.file);
        int n;

        while ((n = this.reader.read(buffer)) != -1) {
            res.append(new String(buffer, 0, n));
        }

        return res.toString();
    }
}
