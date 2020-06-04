package com.mbor.dao;

import com.mbor.entityFactory.TestEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public abstract class IDaoImplTest<T> implements IDaoTest {

    protected static Random random = new Random();

    protected static List<Long> entityIdList = new LinkedList<>();

    protected static final int CREATED_ENTITIES_NUMBER = 3;

    @Autowired
    protected  TestEntityFactory testEntityFactory;

    @Override
    @Test
    public void find_ThenSuccess() {
        assertTrue(getDao().find(getElementIndex(0)).isPresent());
    }

    @Override
    @Test
    public void findAll_ThenSuccess() {
        List<T> lists = getDao().findAll();
        assertEquals(CREATED_ENTITIES_NUMBER, lists.size());
    }

    @Override
    @Test
    public void delete_ThenSuccess() {
        getDao().delete(getElementIndex(0));
        assertEquals(CREATED_ENTITIES_NUMBER - 1, getDao().findAll().size());
    }

    @Override
    @Test
    public void save_ThenSuccess() {
        T t = createNewEntity();
        assertTrue(getDao().save(t).isPresent());
    }

    protected static Long getElementIndex(int index){
        return entityIdList.get(index);
    }

    protected abstract T createNewEntity();

    protected abstract IDao getDao();
}
