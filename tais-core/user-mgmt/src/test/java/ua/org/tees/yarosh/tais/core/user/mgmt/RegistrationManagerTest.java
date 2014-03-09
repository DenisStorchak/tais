package ua.org.tees.yarosh.tais.core.user.mgmt;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.org.tees.yarosh.tais.core.common.dto.Registrant;
import ua.org.tees.yarosh.tais.core.common.exceptions.RegistrantNotFoundException;
import ua.org.tees.yarosh.tais.core.user.mgmt.api.persistence.RegistrantRepository;
import ua.org.tees.yarosh.tais.core.user.mgmt.converters.RegistrantConverterFacade;
import ua.org.tees.yarosh.tais.core.user.mgmt.models.RegistrantEntity;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 15:23
 */
public class RegistrationManagerTest {

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PATRONYMIC = "patronymic";
    private static final String PASSWORD = "password";
    private static final String LOGIN = "login";
    @Mock
    private RegistrantRepository registrantRepositoryMock;
    @Mock
    private RegistrantConverterFacade registrantConverterFacadeMock;

    @InjectMocks
    private RegistrationManager registrantService;
    private Registrant registrant;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        initTestContainers();
        setupMockBehavior();
    }

    private void setupMockBehavior() {
        RegistrantEntity registrantEntity = new RegistrantEntity();
        registrantEntity.setName(NAME);
        registrantEntity.setSurname(SURNAME);
        registrantEntity.setPatronymic(PATRONYMIC);
        registrantEntity.setPassword(PASSWORD);
        registrantEntity.setLogin(LOGIN);

        doReturn(registrantEntity).when(registrantConverterFacadeMock).convert(registrant, RegistrantEntity.class);

        doReturn(true).when(registrantRepositoryMock).exists(LOGIN);
        doReturn(registrantEntity).when(registrantRepositoryMock).findOne(LOGIN);
    }

    private void initTestContainers() {
        registrant = new Registrant();
        registrant.setName(NAME);
        registrant.setSurname(SURNAME);
        registrant.setPatronymic(PATRONYMIC);
        registrant.setPassword(PASSWORD);
        registrant.setLogin(LOGIN);
    }

    @Test(enabled = true, expectedExceptions = NullPointerException.class)
    public void testCreateNullRegistration() throws IllegalArgumentException {
        registrantService.createRegistration(null);
    }

    @Test
    public void testCreateRegistration() {
        Registrant registration = registrantService.createRegistration(registrant);
        assertEquals(registration, registrant);
        verify(registrantRepositoryMock, times(1)).save(any(RegistrantEntity.class));
    }

    @Test
    public void testGetRegistration() throws RegistrantNotFoundException {
        registrantService.getRegistration(registrant.getLogin());
        verify(registrantRepositoryMock, times(1)).findOne(registrant.getLogin());
    }

    @Test
    public void testUpdateRegistration() throws RegistrantNotFoundException {
        registrantService.updateRegistration(registrant);
        verify(registrantRepositoryMock, times(1)).save(any(RegistrantEntity.class));
    }

    @Test
    public void testDeleteRegistration() {
        registrantService.deleteRegistration(registrant.getLogin());
        verify(registrantRepositoryMock, times(1)).delete(registrant.getLogin());
    }
}
