package femr.util.stringhelpers;

import femr.common.models.InventoryExportItem;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Shared inventory file contract. Only Current Quantity is editable on import. */
public final class InventoryCsv {
    private static final CSVFormat FORMAT = CSVFormat.RFC4180.builder()
            .setDelimiter(',').setIgnoreEmptyLines(false).build();

    private InventoryCsv() { }

    public static String write(List<InventoryExportItem> items) throws IOException {
        StringWriter output = new StringWriter();
        try (CSVPrinter printer = new CSVPrinter(output, FORMAT)) {
            printer.printRecord(InventoryExportItem.getFieldOrder());
            for (InventoryExportItem item : items) {
                printer.printRecord(item.getMedicationId(), item.getName(), item.getQuantityCurrent(),
                        item.getQuantityInitial(), item.getTimeAdded(), item.getCreatedBy());
            }
        }
        return output.toString();
    }

    public static List<Row> read(Reader input) throws IOException {
        try (CSVParser parser = FORMAT.parse(input)) {
            List<CSVRecord> records = parser.getRecords();
            if (records.isEmpty()) throw new IllegalArgumentException("The inventory file is empty.");
            CSVRecord header = records.get(0);
            List<String> expected = InventoryExportItem.getFieldOrder();
            if (header.size() != expected.size()) throw invalidHeader();
            for (int i = 0; i < expected.size(); i++) {
                String value = header.get(i);
                if (i == 0 && value.startsWith("\uFEFF")) value = value.substring(1);
                if (!expected.get(i).equals(value)) throw invalidHeader();
            }
            if (records.size() == 1) throw new IllegalArgumentException("The inventory file contains no medication rows.");
            List<Row> rows = new ArrayList<>();
            Set<Integer> ids = new HashSet<>();
            for (int i = 1; i < records.size(); i++) {
                CSVRecord record = records.get(i);
                String prefix = "Row " + record.getRecordNumber() + ": ";
                if (record.size() != expected.size()) {
                    throw new IllegalArgumentException(prefix + "expected exactly six fields.");
                }
                int id = integer(record.get(0), prefix + "ID", true);
                String name = record.get(1).trim();
                if (name.isEmpty()) throw new IllegalArgumentException(prefix + "Medication is required.");
                int quantity = integer(record.get(2), prefix + "Current Quantity", false);
                if (!ids.add(id)) throw new IllegalArgumentException(prefix + "duplicate medication ID " + id + ".");
                rows.add(new Row(record.getRecordNumber(), id, name, quantity));
            }
            return rows;
        } catch (java.io.UncheckedIOException ex) {
            throw ex.getCause();
        }
    }

    private static IllegalArgumentException invalidHeader() {
        return new IllegalArgumentException("Expected header: " + String.join(",", InventoryExportItem.getFieldOrder()));
    }

    private static int integer(String value, String field, boolean positive) {
        try {
            String trimmed = value.trim();
            if (!trimmed.matches("[0-9]+")) throw new NumberFormatException();
            int number = Integer.parseInt(trimmed);
            if (positive && number == 0) throw new NumberFormatException();
            return number;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(field + " must be a whole number between "
                    + (positive ? "1" : "0") + " and 2147483647.");
        }
    }

    public static final class Row {
        public final long number;
        public final int medicationId;
        public final String medicationName;
        public final int quantity;

        private Row(long number, int medicationId, String medicationName, int quantity) {
            this.number = number;
            this.medicationId = medicationId;
            this.medicationName = medicationName;
            this.quantity = quantity;
        }
    }
}
