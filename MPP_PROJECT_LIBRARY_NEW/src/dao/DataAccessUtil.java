package dao;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataAccessUtil {

    //parameters dir: the output directory, name: the file name that should contain the object data
    public static void saveObject(String dir, String name, Object objToSave) throws IOException{
        ObjectOutputStream out = null;
        try {
            Path path = FileSystems.getDefault().getPath(dir, name);
            out = new ObjectOutputStream(Files.newOutputStream(path));
            out.writeObject(objToSave);
        } catch (IOException e) {
            throw e;//;.printStackTrace();//it should be handled in different way (return error) message, or for this project it is ok to do this action
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public static <T> List<T> readAllObject(String dir) {
        File folder = new File(dir);
        String[] fileNames = folder.list();
        List<T> list = new ArrayList<T>();
        for (int i = 0; i < fileNames.length; i++) {
            T t = (T) readObject(dir, fileNames[i]);
            list.add(t);
        }
        return list;
    }
    public static <T> void printAllObject(String dir) {
        File folder = new File(dir);
        String[] fileNames = folder.list();
        List<T> list = new ArrayList<T>();
        for (int i = 0; i < fileNames.length; i++) {
            T t = (T) readObject(dir, fileNames[i]);
            list.add(t);
            System.out.println(list.toArray());
        }
       
    } 

    private static Object readObject(String dir, String name) {
        ObjectInputStream in = null;
        Object entity = null;
        try {
            Path path = FileSystems.getDefault().getPath(dir, name);
            in = new ObjectInputStream(Files.newInputStream(path));
            entity = in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return entity;
    }

    public static <T> T readGenericObject(String dir, String name) {
        ObjectInputStream in = null;
        T entity = null;
        try {
            Path path = FileSystems.getDefault().getPath(dir, name);
            in = new ObjectInputStream(Files.newInputStream(path));
            entity = (T) in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return entity;
    }
}
