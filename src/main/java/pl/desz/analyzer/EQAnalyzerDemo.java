package pl.desz.analyzer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import pl.desz.analyzer.model.QuakeEntry;
import pl.desz.analyzer.parser.EarthQuakeParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class EQAnalyzerDemo {

    public static void main(String[] args) throws IOException {

        EarthQuakeParser parser = new EarthQuakeParser();

//        List<QuakeEntry> entries = fromFile(parser);
        List<QuakeEntry> entries = fromUrl(parser);

        dumpCsv(entries);
    }

    private static List<QuakeEntry> fromFile(EarthQuakeParser parser) {
        File eqXmlFile = new File("src/main/resources/past30days-significant-eq.xml");
        return parser.read(eqXmlFile);
    }

    private static List<QuakeEntry> fromUrl(EarthQuakeParser parser) throws MalformedURLException {
        URL url = URI.create("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_month.atom").toURL();
        return parser.read(url);
    }

    private static void dumpCsv(List<QuakeEntry> entries) throws IOException {
        try (CSVPrinter printer = new CSVPrinter(
                new FileWriter("src/main/resources/eq.csv"),
                CSVFormat.DEFAULT)) {

            writeHeader(printer);
            writeValues(entries, printer);
        }
    }

    private static void writeHeader(CSVPrinter printer) throws IOException {
        printer.printRecord("magnitude", "country", "lon", "lat", "date");
    }

    private static void writeValues(List<QuakeEntry> entries, CSVPrinter printer) throws IOException {
        for (QuakeEntry e : entries) {
            printer.printRecord(e.getMagnitude(), e.getLocation().getCountry(), e.getLocation().getLon(),
                    e.getLocation().getLat(), e.getDatetime());
        }
    }

}
