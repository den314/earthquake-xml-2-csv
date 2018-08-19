package pl.desz.analyzer.parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.desz.analyzer.model.QuakeEntry;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EarthQuakeParserTest {

    private static final File EQ_XML_RESPONSE = new File("src/test/resources/two_entries_eq.xml");
    private static final File EQ_EMPTY_XML_RESPONSE = new File("src/test/resources/empty_eq.xml");

    @BeforeAll
    static void verify() {
        if (! EQ_XML_RESPONSE.exists()) {
            fail("earth quake test file does not exists");
        }

        if (! EQ_EMPTY_XML_RESPONSE.exists()) {
            fail("empty earth quake test file does not exists");
        }
    }

    @Test
    void givenFile_thenParseOk() {
        EarthQuakeParser eqp = new EarthQuakeParser();
        List<QuakeEntry> quakeEntryList = eqp.read(EQ_XML_RESPONSE);

        assertNotNull(quakeEntryList);
        assertEquals(2, quakeEntryList.size());
    }

    @Test
    void givenEmptyFile_thenEmptyList() {
        EarthQuakeParser eqp = new EarthQuakeParser();
        List<QuakeEntry> quakeEntryList = eqp.read(EQ_EMPTY_XML_RESPONSE);

        assertNotNull(quakeEntryList);
        assertTrue(quakeEntryList.isEmpty());
    }
}