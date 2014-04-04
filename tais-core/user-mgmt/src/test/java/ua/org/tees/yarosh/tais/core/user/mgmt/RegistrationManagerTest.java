package ua.org.tees.yarosh.tais.core.user.mgmt;

/**
 * @author Timur Yarosh
 *         Date: 09.03.14
 *         Time: 15:23
 */
public class RegistrationManagerTest {

//    private static final String NAME = "name";
//    private static final String SURNAME = "surname";
//    private static final String PATRONYMIC = "patronymic";
//    private static final String PASSWORD = "password";
//    private static final String LOGIN = "login";
//    @Mock
//    private RegistrantRepository registrantRepositoryMock;
//
//    @InjectMocks
//    private RegistrationManager registrantService;
//
//    @BeforeClass
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//
//        doReturn(true).when(registrantRepositoryMock).exists(LOGIN);
//        doReturn(registrantProvider()[0][0]).when(registrantRepositoryMock).findOne(LOGIN);
//    }
//
//    @DataProvider
//    public Object[][] registrantProvider() {
//        Registrant registrant = new Registrant();
//        registrant.setName(NAME);
//        registrant.setSurname(SURNAME);
//        registrant.setPatronymic(PATRONYMIC);
//        registrant.setPassword(PASSWORD);
//        registrant.setLogin(LOGIN);
//        registrant.setRole(Roles.ADMIN);
//        StudentGroup group = new StudentGroup();
//        group.setStudents(Arrays.asList(registrant));
//        group.setId("012");
//        registrant.setGroup(group);
//        return new Object[][]{
//                {registrant}
//        };
//    }
//
//    @Test(expectedExceptions = NullPointerException.class)
//    public void testCreateNullRegistration() throws IllegalArgumentException {
//        registrantService.createRegistration(null);
//    }
//
//    @Test(dataProvider = "registrantProvider")
//    public void testCreateRegistration(Registrant registrant) {
//        Registrant registration = registrantService.createRegistration(registrant);
//        assertEquals(registration, registrant);
//        verify(registrantRepositoryMock, times(1)).save(any(Registrant.class));
//    }
//
//    @Test(dataProvider = "registrantProvider")
//    public void testGetRegistration(Registrant registrant) throws RegistrantNotFoundException {
//        registrantService.getRegistration(registrant.getLogin());
//        verify(registrantRepositoryMock, times(1)).findOne(registrant.getLogin());
//    }
//
//    @Test(dataProvider = "registrantProvider")
//    public void testUpdateRegistration(Registrant registrant) throws RegistrantNotFoundException {
//        registrantService.updateRegistration(registrant);
//        verify(registrantRepositoryMock, times(1)).save(any(Registrant.class));
//    }
//
//    @Test(dataProvider = "registrantProvider")
//    public void testDeleteRegistration(Registrant registrant) {
//        registrantService.deleteRegistration(registrant.getLogin());
//        verify(registrantRepositoryMock, times(1)).delete(registrant.getLogin());
//    }
}
