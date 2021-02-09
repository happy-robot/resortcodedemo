package kz.kaps.resort.dataproviders.database.ad;

import kz.kaps.resort.core.domain.Ad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AdDatabaseDataProviderTest {

    @Mock
    private AdEntityRepository adEntityRepository;

    @Mock
    private Ad ad;

    @InjectMocks
    private AdDatabaseDataProvider dataProvider;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void doesAdExist() {
        when(adEntityRepository.existsById(1L)).thenReturn(true);
        assertTrue(dataProvider.doesAdExist(1L));
    }

}
