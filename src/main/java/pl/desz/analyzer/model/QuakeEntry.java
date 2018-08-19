package pl.desz.analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@AllArgsConstructor
public final class QuakeEntry {

    private double magnitude;
    private Location location;
    private ZonedDateTime datetime;
}
