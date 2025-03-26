import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

import Lab.Model.Store;
import Lab.Repository.StoreRepository;
import Lab.Service.StoreService;

@RunWith(MockitoJUnitRunner.class)
public class StoreServiceTest {
    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    @Before
    public void setUp() {
        storeService = new StoreService(storeRepository);
    }

    @Test
    public void testPersistStore() {
        Store testStore = new Store("test", "test");

        when(storeRepository.save(any(Store.class))).thenReturn(testStore);

        Store actualStore = storeService.persistStore(testStore);

        Assert.assertEquals(testStore, actualStore);
        verify(storeRepository, times(1)).save(any(Store.class));
        verifyNoMoreInteractions(storeRepository);
    }

    @Test
    public void testGetAllStores() {
        when(storeRepository.findAll()).thenReturn(List.of(new Store(), new Store()));

        int actualSize = storeService.getAllStores().size();

        Assert.assertEquals(2, actualSize);
        verify(storeRepository, times(1)).findAll();
        verifyNoMoreInteractions(storeRepository);
    }

    @Test
    public void testGetStoreByID() {
        Store testStore = new Store(1L, "test", "test");

        when(storeRepository.findById(1L)).thenReturn(Optional.of(testStore));

        Store actualStore = storeService.getStoreById(1L);

        Assert.assertEquals(testStore, actualStore);
        verify(storeRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(storeRepository);
    }

    @Test
    public void testDeleteStore() {
        doNothing().when(storeRepository).deleteById(anyLong());

        storeService.deleteStore(anyLong());
        verify(storeRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(storeRepository);
    }

    @Test
    public void testUpdateStore() {
        Store testStore = new Store(1L, "test", "test");

        when(storeRepository.findById(1L)).thenReturn(Optional.of(testStore));
        when(storeRepository.save(any(Store.class))).thenReturn(testStore);

        Store actualStore = storeService.updateStore(1L, testStore);

        Assert.assertEquals(testStore, actualStore);
        verify(storeRepository, times(1)).findById(anyLong());
        verify(storeRepository, times(1)).save(any(Store.class));
        verifyNoMoreInteractions(storeRepository);
    }
}
