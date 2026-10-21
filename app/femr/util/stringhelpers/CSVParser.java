package femr.util.stringhelpers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    /**
     * Parses CSV content whose first line is a header row into a list of records.
     *
     * @param reader the CSV content; closed by the caller
     * @return one record per data row, with values accessible by header name
     * @throws IOException if the content cannot be read or is not valid CSV
     */
    public static List<CSVRecord> parse(Reader reader, String[] expectedHeaders) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build();

        try (org.apache.commons.csv.CSVParser parser = format.parse(reader)) {
            List<String> actual = parser.getHeaderNames();
            for (String expected : expectedHeaders) {
                if (!actual.contains(expected)) {
                    throw new IOException(String.format(
                            "CSV header is missing required column '%s'. Found: %s", expected, actual));
                }
            }
            return new ArrayList<>(parser.getRecords());
        }
    }
}
