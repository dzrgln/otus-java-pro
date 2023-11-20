package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {

        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                T entity = null;

                if (rs.next()) {

                    entity = entityClassMetaData.getConstructor().newInstance();

                    for (Field field : entityClassMetaData.getAllFields()) {
                        field.setAccessible(true);
                        field.set(entity, rs.getObject(field.getName()));
                        field.setAccessible(false);
                    }
                }
                return entity;
            } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {

        Optional<List<T>> listOptional = dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            try {
                List<T> entityList = new ArrayList<>();

                while (rs.next()) {

                    T entity = entityClassMetaData.getConstructor().newInstance();

                    for (Field field : entityClassMetaData.getAllFields()) {
                        field.setAccessible(true);
                        field.set(entity, rs.getObject(field.getName()));
                        field.setAccessible(false);
                    }

                    entityList.add(entity);
                }

                return entityList;

            } catch (SQLException e) {
                throw new DataTemplateException(e);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        return listOptional.orElse(Collections.emptyList());
    }

    @Override
    public long insert(Connection connection, T object) {
        try {

            List<Object> params = new ArrayList<>();
            for (Field field : entityClassMetaData.getFieldsWithoutId()) {
                field.setAccessible(true);
                params.add(field.get(object));
                field.setAccessible(false);
            }
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);

        } catch (Exception exception) {
            throw new DataTemplateException(exception);
        }
    }

    @Override
    public void update(Connection connection, T object) {

        try {
            List<Object> params = new ArrayList<>();
            for (Field field : entityClassMetaData.getFieldsWithoutId()) {
                field.setAccessible(true);
                params.add(field.get(object));
                field.setAccessible(false);
            }

            Field idField = entityClassMetaData.getIdField();
            idField.setAccessible(true);
            params.add(idField.get(object));
            idField.setAccessible(false);

            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);

        } catch (Exception exception) {
            throw new DataTemplateException(exception);
        }
    }
}
