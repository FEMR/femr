package femr.business.services.system;

import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.InventoryExportItem;
import femr.common.models.MedicationItem;
import femr.data.daos.core.IMedicationRepository;
import femr.data.models.core.IMedication;
import femr.data.models.core.IMedicationInventory;
import io.ebean.Transaction;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class InventoryImportTest {
    @Rule public TemporaryFolder temporary = new TemporaryFolder();
    private IMedicationRepository repository;
    private InventoryService service;
    private IMedicationInventory first;
    private IMedicationInventory second;
    private Transaction transaction;

    @Before
    public void setup() {
        repository = mock(IMedicationRepository.class);
        IItemModelMapper mapper = mock(IItemModelMapper.class);
        service = new InventoryService(repository, null, null, mapper, null, null, () -> transaction);
        first = inventory(1, "One", mapper);
        second = inventory(2, "Two", mapper);
        doReturn(Arrays.asList(first, second)).when(repository).retrieveMedicationInventoriesByTripId(10, false);
        when(repository.saveMedicationInventory(any())).thenAnswer(call -> call.getArgument(0));
        transaction = mock(Transaction.class);
    }

    @Test
    public void repeatedImportSetsTotalAndPreservesOtherFieldsAndOmittedRows() throws Exception {
        File file = file("1,One,100,999,changed,changed\n");
        assertFalse(service.importCSV(10, file, null).hasErrors());
        assertFalse(service.importCSV(10, file, null).hasErrors());
        verify(first, times(2)).setQuantityCurrent(100);
        verify(first, never()).setQuantityInitial(anyInt());
        verify(first, never()).setCreatedBy(any());
        verify(first, never()).setTimeAdded(any());
        verify(second, never()).setQuantityCurrent(anyInt());
        verify(transaction, times(2)).commit();
    }

    @Test
    public void zeroClearsCurrentStock() throws Exception {
        assertFalse(service.importCSV(10, file("1,One,0,50,,"), null).hasErrors());
        verify(first).setQuantityCurrent(0);
    }

    @Test
    public void invalidLaterRowCausesNoWrites() throws Exception {
        ServiceResponse<String> result = service.importCSV(10, file("1,One,10,50,,\n2,Two,-1,50,,"), null);
        assertTrue(result.hasErrors());
        verify(repository, never()).saveMedicationInventory(any());
    }

    @Test
    public void unknownIdOrMismatchedNameCausesNoWrites() throws Exception {
        assertTrue(service.importCSV(10, file("1,One,10,50,,\n3,Three,20,50,,"), null).hasErrors());
        assertTrue(service.importCSV(10, file("1,On,10,50,,"), null).hasErrors());
        verify(repository, never()).saveMedicationInventory(any());
        verify(transaction, never()).commit();
        verify(transaction, times(2)).close();
    }

    @Test
    public void laterSaveFailureClosesTransactionWithoutCommit() throws Exception {
        when(repository.saveMedicationInventory(second)).thenThrow(new IllegalStateException("save failed"));
        assertTrue(service.importCSV(10, file("1,One,10,50,,\n2,Two,20,50,,"), null).hasErrors());
        verify(repository).saveMedicationInventory(first);
        verify(transaction, never()).commit();
        verify(transaction).close();
    }

    private IMedicationInventory inventory(int id, String name, IItemModelMapper mapper) {
        IMedication medication = mock(IMedication.class);
        when(medication.getId()).thenReturn(id);
        IMedicationInventory inventory = mock(IMedicationInventory.class);
        when(inventory.getMedication()).thenReturn(medication);
        when(inventory.getQuantityCurrent()).thenReturn(50);
        MedicationItem item = new MedicationItem();
        item.setFullName(name);
        when(mapper.createMedicationItem(medication, 0, 0, null, null, null)).thenReturn(item);
        return inventory;
    }

    private File file(String rows) throws Exception {
        File file = temporary.newFile();
        Files.write(file.toPath(), (String.join(",", InventoryExportItem.getFieldOrder()) + "\n" + rows)
                .getBytes(StandardCharsets.UTF_8));
        return file;
    }
}
