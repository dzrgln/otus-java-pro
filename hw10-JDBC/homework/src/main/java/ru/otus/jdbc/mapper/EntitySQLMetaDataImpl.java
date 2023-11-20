package ru.otus.jdbc.mapper;

import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData<?> entityClassMetaDataClient;

    private final String TABLE_NAME;
    private static final String SELECT_ALL = "SELECT * FROM %s;";
    private static final String SELECT_BY_ID = "SELECT * FROM %s WHERE %s=?;";
    private static final String INSERT = "INSERT INTO %s (%s) values (%s);";
    private static final String UPDATE = "UPDATE %s SET %s = %s WHERE %s=?;";

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaDataClient) {
        this.entityClassMetaDataClient = entityClassMetaDataClient;
        TABLE_NAME = getTableName();
    }

    @Override
    public String getSelectAllSql() {
        return String.format(SELECT_ALL, TABLE_NAME);
    }

    @Override
    public String getSelectByIdSql() {
        Field idField = entityClassMetaDataClient.getIdField();
        return String.format(SELECT_BY_ID, TABLE_NAME, idField.getName());
    }

    @Override
    public String getInsertSql() {
        List<Field> fieldsWithoutId = entityClassMetaDataClient.getFieldsWithoutId();
        StringBuilder sbNames = new StringBuilder();
        StringBuilder sbHolderPlaces = new StringBuilder();
        for (Field field : fieldsWithoutId) {
            String fieldName = getFieldName(field);
            sbNames.append(fieldName).append(", ");
            sbHolderPlaces.append("?, ");
        }
        String columnNames = sbNames.substring(0, sbNames.length() - 2);
        String holderPlaces = sbHolderPlaces.substring(0, sbHolderPlaces.length() - 2);
        return String.format(INSERT, getTableName(), columnNames, holderPlaces);
    }

    @Override
    public String getUpdateSql() {
        List<Field> fieldsWithoutId = entityClassMetaDataClient.getFieldsWithoutId();
        StringBuilder sbNames = new StringBuilder();
        StringBuilder sbHolderPlaces = new StringBuilder();
        for (Field field : fieldsWithoutId) {
            String fieldName = getFieldName(field);
            sbNames.append(fieldName).append(", ");
            sbHolderPlaces.append("?, ");
        }
        String updateFields;
        String holderPlaces;
        if (fieldsWithoutId.size() > 1) {
            updateFields = "(" + sbNames.substring(0, sbNames.length() - 2) + ")";
            holderPlaces = "(" + sbHolderPlaces.substring(0, sbHolderPlaces.length() - 2) + ")";
        } else {
            updateFields = sbNames.substring(0, sbNames.length() - 2);
            holderPlaces = sbHolderPlaces.substring(0, sbHolderPlaces.length() - 2);
        }
        String idField = getFieldName(entityClassMetaDataClient.getIdField());
        return String.format(UPDATE, TABLE_NAME, updateFields, holderPlaces, idField);
    }

    private String getFieldName(Field field) {
        String[] split = field.getName().split("\\.");
        return split[split.length - 1].toLowerCase();
    }

    private String getTableName() {
        String name = entityClassMetaDataClient.getName();
        String[] split = name.split("\\.");
        return split[split.length - 1].toLowerCase();
    }
}
