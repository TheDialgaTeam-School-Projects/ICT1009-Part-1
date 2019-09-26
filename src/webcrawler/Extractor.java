package webcrawler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public abstract class Extractor<TData> {

    protected void saveIntoCache(String cacheFileLocation, List<TData> data) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(cacheFileLocation))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            bufferedWriter.write(gson.toJson(data));
            bufferedWriter.flush();
        } catch (IOException ignored) {
        }
    }

    public List<TData> getData() {
        return getData(false);
    }

    public abstract List<TData> getData(boolean ignoreCache);

}
