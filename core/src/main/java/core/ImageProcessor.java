package core;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class ImageProcessor {

    private final String path;

    private int lastId = 0;

    public String storeImage(InputStream is, String name){
        int myId;
        synchronized (this){
            myId = lastId;
            lastId++;
        }
        String label = Integer.toString(myId);
        String type;
        if (name.endsWith("jpg"))
            type = "jpg";
        else if (name.endsWith("png"))
            type = "png";
        else if (name.endsWith("bmp"))
            type = "bmp";
        else return null;

        String shortName = label + "." + type;
        String fullPath = path + "/" + shortName;

        File file = new File(fullPath);
        try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileOutputStream fos = new FileOutputStream(file, false)){
            byte[] buffer = new byte[1024];
            int readed = 1024;
            while (readed == buffer.length){
                readed = is.read(buffer);
                fos.write(buffer, 0, readed);
            }

            return shortName;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
