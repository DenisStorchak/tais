package ua.org.tees.yarosh.tais.core.common.properties;

import org.springframework.beans.factory.annotation.Value;

public class HibernateProperties {
    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2ddlAuto;
    @Value("${hibernate.show_sql}")
    private String hibernateShowSql;
    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    public String getHibernateHbm2ddlAuto() {
        return hibernateHbm2ddlAuto;
    }

    public void setHibernateHbm2ddlAuto(String hibernateHbm2ddlAuto) {
        this.hibernateHbm2ddlAuto = hibernateHbm2ddlAuto;
    }

    public String getHibernateShowSql() {
        return hibernateShowSql;
    }

    public void setHibernateShowSql(String hibernateShowSql) {
        this.hibernateShowSql = hibernateShowSql;
    }

    public String getHibernateDialect() {
        return hibernateDialect;
    }

    public void setHibernateDialect(String hibernateDialect) {
        this.hibernateDialect = hibernateDialect;
    }
}
