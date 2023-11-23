package ru.otus.demo;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.crm.service.DbServiceClientWithCacheImpl;

public class DbServiceCacheDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceCacheDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        long timeWorkingWithoutCache = getTimeWorkingWithoutCache(configuration);
        long timeWorkingWithCache = getTimeWorkingWithCache(configuration);

        log.info("Повторное обращение к тем же сущностям без кэша заняло {}", timeWorkingWithoutCache);
        log.info("Повторное обращение к тем же сущностям с использованием кэша заняло {}", timeWorkingWithCache);

    }


    private static long getTimeWorkingWithoutCache(Configuration configuration) {
        var sessionFactory = HibernateUtils.buildSessionFactory(configuration,
                Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);

        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
        dbServiceClient.saveClient(new Client("1"));
        dbServiceClient.saveClient(new Client("2"));
        dbServiceClient.saveClient(new Client("3"));
        dbServiceClient.saveClient(new Client("4"));
        dbServiceClient.saveClient(new Client("5"));

        dbServiceClient.getClient(1);
        dbServiceClient.getClient(2);
        dbServiceClient.getClient(3);
        dbServiceClient.getClient(4);
        dbServiceClient.getClient(5);

        long start = System.currentTimeMillis();
        dbServiceClient.getClient(1);
        dbServiceClient.getClient(2);
        dbServiceClient.getClient(3);
        dbServiceClient.getClient(4);
        dbServiceClient.getClient(5);
        return System.currentTimeMillis() - start;
    }

    private static long getTimeWorkingWithCache(Configuration configuration) {
        var sessionFactory = HibernateUtils.buildSessionFactory(configuration,
                Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);

        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        var dbServiceClient = new DbServiceClientWithCacheImpl(transactionManager, clientTemplate);
        dbServiceClient.saveClient(new Client("1"));
        dbServiceClient.saveClient(new Client("2"));
        dbServiceClient.saveClient(new Client("3"));
        dbServiceClient.saveClient(new Client("4"));
        dbServiceClient.saveClient(new Client("5"));

        dbServiceClient.getClient(1);
        dbServiceClient.getClient(2);
        dbServiceClient.getClient(3);
        dbServiceClient.getClient(4);
        dbServiceClient.getClient(5);

        long start = System.currentTimeMillis();
        dbServiceClient.getClient(1);
        dbServiceClient.getClient(2);
        dbServiceClient.getClient(3);
        dbServiceClient.getClient(4);
        dbServiceClient.getClient(5);
        return System.currentTimeMillis() - start;
    }
}
