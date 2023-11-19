package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private static final Logger log = LoggerFactory.getLogger(EntityClassMetaDataImpl.class);
    private final Class<T> type;

    public EntityClassMetaDataImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public Constructor<T> getConstructor() {
        Constructor<T> res = null;
        try {
            res = type.getConstructor();
        } catch (NoSuchMethodException e) {
            log.error("Не найден конструктор у объекта");
        }
        return res;
    }

    @Override
    public Field getIdField() {
        Field res = null;
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Id.class) != null) {
                res = field;
                break;
            }
            //Нужна проверка, что ID только одно, но не буду в дз углубляться
        }
        return res;
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(type.getDeclaredFields()).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> res = new ArrayList<>(getAllFields());
        res.remove(getIdField());
        return res;
    }
}
