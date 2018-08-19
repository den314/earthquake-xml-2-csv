package pl.desz.analyzer.util;

import org.jsoup.nodes.Element;
import pl.desz.analyzer.model.Location;

import java.time.ZonedDateTime;

public final class QuakeEntryExtractor {

    private QuakeEntryExtractor() {
        // intentional
    }

    public static ZonedDateTime getDateTime(Element e) {
        return ZonedDateTime.parse(e.getElementsByTag("updated").text());
    }

    public static Location getLocation(Element e) {
        String[] loc = e.getElementsByTag("georss:point").text().split(" ");
        return new Location(loc[0], loc[1], getCountry(getTitle(e)));
    }

    public static double getMagnitude(Element e) {
        return Double.parseDouble(getTitle(e)[0].split("\\s+")[1]);
    }

    private static String getCountry(String[] title) {
        return title[title.length - 1].trim();
    }

    private static String[] getTitle(Element e) {
        return e.getElementsByTag("title").text().split(",");
    }
}
