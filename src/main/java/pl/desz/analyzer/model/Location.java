package pl.desz.analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public final class Location {

    private double lon;
    private double lat;
    private String country;

    public Location(String lon, String lat, String country) {
        this(Double.parseDouble(lon), Double.parseDouble(lat), country);
    }
}
