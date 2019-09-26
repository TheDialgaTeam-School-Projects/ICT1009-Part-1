package webcrawler.extractor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webcrawler.Extractor;
import webcrawler.data.TrainServiceDisruptionData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TrainServiceDisruptionSheetExtractor extends Extractor<TrainServiceDisruptionData> {

    private final String cacheFileLocation = FileSystems.getDefault().getPath("TrainServiceDisruptionSheet.json").toString();

    @Override
    public List<TrainServiceDisruptionData> getData(boolean ignoreCache) {
        if (!ignoreCache) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            File file = new File(cacheFileLocation);

            if (file.exists()) {
                try (FileReader fileReader = new FileReader(file)) {
                    return gson.fromJson(fileReader, new TypeToken<List<TrainServiceDisruptionData>>() {
                    }.getType());
                } catch (IOException ignored) {
                }
            }
        }

        ArrayList<TrainServiceDisruptionData> trainServiceDisruptionDataArrayList = new ArrayList<>();

        try {
            Document document = Jsoup.connect("https://docs.google.com/spreadsheets/d/1C_UUDUor1vgcpy6V3210GT2PnTh7FVpydM3Y2I-58mw/pub?output=html")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0")
                    .maxBodySize(0)
                    .get();

            Elements tableRows = document.select("div#sheets-viewport > div#0 tr");

            for (Element tableRow : tableRows) {
                Elements tableHeader = tableRow.select("th");

                if (!(tableHeader.attr("id").startsWith("0R") && Integer.parseInt(tableHeader.attr("id").substring(2)) > 0))
                    continue;

                Elements tableColumns = tableRow.select("td");

                try {
                    TrainServiceDisruptionData trainServiceDisruptionData = new TrainServiceDisruptionData();
                    trainServiceDisruptionData.setIncident(tableColumns.get(0).text());
                    trainServiceDisruptionData.setDescription(tableColumns.get(2).text());
                    trainServiceDisruptionData.setStarted(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tableColumns.get(5).text()));
                    trainServiceDisruptionData.setEnded(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tableColumns.get(6).text()));
                    trainServiceDisruptionData.setDirection(tableColumns.get(8).text());
                    trainServiceDisruptionData.setLine(tableColumns.get(9).text());
                    trainServiceDisruptionData.setOperator(tableColumns.get(10).text());
                    trainServiceDisruptionData.setCategory(tableColumns.get(11).text());
                    trainServiceDisruptionData.setDuration(Integer.parseInt(tableColumns.get(17).text()));
                    trainServiceDisruptionData.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(tableColumns.get(18).text()));

                    List<String> urls = new ArrayList<>();

                    for (int i = 19; i <= 25; i++) {
                        Elements link = tableColumns.get(i).select("a");

                        if (link.size() > 0)
                            urls.add(link.text());
                    }

                    trainServiceDisruptionData.setUrls(urls);

                    trainServiceDisruptionDataArrayList.add(trainServiceDisruptionData);
                } catch (ParseException ignored) {
                }

                saveIntoCache(cacheFileLocation, trainServiceDisruptionDataArrayList);
            }
        } catch (IOException ignored) {
        }

        return trainServiceDisruptionDataArrayList;
    }
}
