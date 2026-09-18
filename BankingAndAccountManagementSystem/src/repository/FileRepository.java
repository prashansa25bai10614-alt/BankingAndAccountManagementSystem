package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepository {

    public static <T> void saveData(String filePath, List<T> data) {

        File file = new File(filePath);

        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(new FileOutputStream(file))) {

            outputStream.writeObject(data);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Error while saving data: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> loadData(String filePath) {

        File file = new File(filePath);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream inputStream =
                     new ObjectInputStream(new FileInputStream(file))) {

            return (List<T>) inputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(
                    "Error while loading data: " + e.getMessage(), e);
        }
    }
}