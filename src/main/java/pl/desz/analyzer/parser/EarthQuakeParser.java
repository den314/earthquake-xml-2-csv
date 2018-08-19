package pl.desz.analyzer.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.desz.analyzer.model.Location;
import pl.desz.analyzer.model.QuakeEntry;
import pl.desz.analyzer.util.QuakeEntryExtractor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EarthQuakeParser {

    public List<QuakeEntry> read(URL url) {
        Document doc = fromUrl(url);
        Elements entries = doc.getElementsByTag("entry");

        if (entries.isEmpty()) {
            return Collections.emptyList();
        }

        return toQuakeEntries(entries);
    }

    /**
     * @param file source file with quake entries
     * @return list of parsed quake entries. Will be empty if no entries are found.
     */
    public List<QuakeEntry> read(File file) {

        Document doc = parseFile(file);
        Elements entries = doc.getElementsByTag("entry");

        if (entries.isEmpty()) {
            return Collections.emptyList();
        }

        return toQuakeEntries(entries);
    }

    private List<QuakeEntry> toQuakeEntries(Elements entries) {

        ArrayList<QuakeEntry> quakeEntryList = new ArrayList<>();
        for (Element e : entries) {

            Location location = QuakeEntryExtractor.getLocation(e);
            ZonedDateTime dateTime = QuakeEntryExtractor.getDateTime(e);
            double magnitude = QuakeEntryExtractor.getMagnitude(e);

            quakeEntryList.add(new QuakeEntry(magnitude, location, dateTime));
        }

        return quakeEntryList;
    }

    private Document parseFile(File file) {
        Document doc;
        try {
            doc = Jsoup.parse(file, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            throw new IllegalStateException("File cannot be processed.", e);
        }
        return doc;
    }

    private Document fromUrl(URL url) {
        Document doc;
        try {
            doc = Jsoup.parse(url, 5000);
        } catch (IOException e) {
            throw new IllegalStateException("File cannot be processed.", e);
        }
        return doc;
    }
}
