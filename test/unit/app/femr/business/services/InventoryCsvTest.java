package unit.app.femr.business.services;

import femr.common.models.InventoryExportItem;
import femr.common.models.MedicationItem;
import femr.util.stringhelpers.InventoryCsv;
import org.junit.Test;

import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class InventoryCsvTest {
    private static final String HEADER = String.join(",", InventoryExportItem.getFieldOrder()) + "\n";

    @Test
    public void exportCanBeImportedWithoutLosingSpecialCharacters() throws Exception {
        MedicationItem item = new MedicationItem();
        item.setId(17);
        item.setFullName("Example, with | and \"quotes\"\nand newline");
        item.setQuantityCurrent(100);
        item.setQuantityTotal(200);
        item.setCreatedBy("Last, First");
        String csv = InventoryCsv.write(Collections.singletonList(new InventoryExportItem(item)));
        List<InventoryCsv.Row> rows = InventoryCsv.read(new StringReader(csv));
        assertEquals(1, rows.size());
        assertEquals(17, rows.get(0).medicationId);
        assertEquals(item.getFullName(), rows.get(0).medicationName);
        assertEquals(100, rows.get(0).quantity);
        assertTrue(csv.contains("Last, First"));
    }

    @Test
    public void acceptsBomZeroAndMaximumInteger() throws Exception {
        List<InventoryCsv.Row> rows = InventoryCsv.read(new StringReader("\uFEFF" + HEADER
                + "1,One,0,100,,\r\n2,Two,2147483647,200,,\r\n"));
        assertEquals(0, rows.get(0).quantity);
        assertEquals(Integer.MAX_VALUE, rows.get(1).quantity);
    }

    @Test
    public void rejectsInvalidFilesAndValuesWithUsefulErrors() throws Exception {
        invalid("", "empty");
        invalid(HEADER, "no medication rows");
        invalid("Medication,Quantity\nOne,1", "Expected header");
        invalid(HEADER + "1,One,1", "Row 2");
        invalid(HEADER + "1,One,1,2,,,extra", "six fields");
        invalid(HEADER + "1, ,1,2,,", "Medication is required");
        for (String quantity : new String[]{"-1", "1.5", "many", "", "2147483648"}) {
            invalid(HEADER + "1,One," + quantity + ",2,,", "Current Quantity");
        }
        invalid(HEADER + "0,One,1,2,,", "ID");
        invalid(HEADER + "1,One,1,2,,\n1,One,2,2,,", "duplicate medication ID");
        invalid(HEADER + "\n", "six fields");
    }

    @Test(expected = java.io.IOException.class)
    public void rejectsUnterminatedQuotedField() throws Exception {
        InventoryCsv.read(new StringReader(HEADER + "1,\"Unfinished,10,20,,"));
    }

    private void invalid(String csv, String message) throws Exception {
        try {
            InventoryCsv.read(new StringReader(csv));
            fail("Expected invalid inventory file");
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().contains(message));
        }
    }
}
