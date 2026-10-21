package femr.util.stringhelpers;

import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CSVParserTest {
    private static final String[] HEADERS = {"Medication", "Quantity"};

    @Test
    public void testParseReadsRowsByHeaderName() throws Exception {
        String csv = "Medication,Quantity\nAspirin,25\nTylenol,10\n";

        List<CSVRecord> records = CSVParser.parse(new StringReader(csv), HEADERS);

        assertEquals(2, records.size());
        assertEquals("Aspirin", records.get(0).get("Medication"));
        assertEquals("25", records.get(0).get("Quantity"));
        assertEquals("Tylenol", records.get(1).get("Medication"));
        assertEquals("10", records.get(1).get("Quantity"));
    }

    @Test
    public void testParseHandlesQuotedCommasAndBlankLines() throws Exception {
        String csv = "Medication,Quantity\n\n\"Tylenol, 500mg\", 7 \n\n";

        List<CSVRecord> records = CSVParser.parse(new StringReader(csv), HEADERS);

        assertEquals(1, records.size());
        assertEquals("Tylenol, 500mg", records.get(0).get("Medication"));
        assertEquals("7", records.get(0).get("Quantity"));
    }

    @Test
    public void testParseHeaderOnlyReturnsNoRecords() throws Exception {
        List<CSVRecord> records = CSVParser.parse(new StringReader("Medication,Quantity\n"), HEADERS);

        assertTrue(records.isEmpty());
    }

    @Test
    public void testParseMissingHeaderThrows() {
        String csv = "Foo,Bar\nAspirin,25\n";

        try {
            CSVParser.parse(new StringReader(csv), HEADERS);
            fail("Expected IOException for missing header");
        } catch (IOException ex) {
            assertEquals("CSV header is missing required column 'Medication'. Found: [Foo, Bar]",
                    ex.getMessage());
        }
    }
}
