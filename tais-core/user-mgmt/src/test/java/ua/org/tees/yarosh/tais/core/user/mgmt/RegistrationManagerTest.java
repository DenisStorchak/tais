package ua.org.tees.yarosh.tais.core.user.mgmt;

import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.org.tees.yarosh.tais.test.utils.DatabaseTester;

import static ua.org.tees.yarosh.tais.test.utils.DatabaseTester.createJdbcTester;

@ContextConfiguration(classes = UserMgmtTestConfiguration.class)
public class RegistrationManagerTest {

    private DatabaseTester databaseTester;

    @BeforeMethod
    public void setUp() throws ClassNotFoundException {
        databaseTester = createJdbcTester("org.h2.Driver",
                "jdbc:h2:mem:tais_test", "tais", "tais");
    }

    @Test
    public void testCreateRegistration() {

    }

    @Test
    public void testAddRegistrationListener() {

    }

    @Test
    public void testGetRegistration() {

    }

    @Test
    public void testUpdateRegistration() {

    }

    @Test
    public void testDeleteRegistration() {

    }

    @Test
    public void testFindAllRegistrants() {

    }

    @Test
    public void testFindAllTeachers() {

    }

    @Test
    public void testLoginExists() {

    }

    @Test
    public void testFindAllStudentGroups() {

    }

    @Test
    public void testIsStudentGroupExists() {

    }

    @Test
    public void testCreateStudentGroup() {

    }

    @Test
    public void testFindStudentGroup() {

    }

    @Test
    public void testFindRegistrantsByStudentGroup() {

    }
}