package ua.org.tees.yarosh.tais.test.utils;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import java.io.File;
import java.net.MalformedURLException;

public class DatabaseTester {
    private IDatabaseTester databaseTester;

    public static DatabaseTester createJdbcTester(String driverClass, String jdbcUrl, String username, String password)
            throws ClassNotFoundException {

        return new DatabaseTester(new JdbcDatabaseTester(driverClass, jdbcUrl, username, password));
    }

    private DatabaseTester(IDatabaseTester iDatabaseTester) {
        this.databaseTester = iDatabaseTester;
    }

    public void onSetup() throws Exception {
        databaseTester.onSetup();
    }

    public void setSetUpOperation(DatabaseOperation databaseOperation) {
        databaseTester.setSetUpOperation(databaseOperation);
    }

    public void onTearDown() throws Exception {
        databaseTester.onTearDown();
    }

    public void setTearDownOperation(DatabaseOperation databaseOperation) {
        databaseTester.setTearDownOperation(databaseOperation);
    }

    public void setFlatXmlDataSet(String path) throws MalformedURLException, DataSetException {
        FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().build(new File(path));
        databaseTester.setDataSet(dataSet);
    }

    public IDataSet getDataSet() {
        return databaseTester.getDataSet();
    }
}
