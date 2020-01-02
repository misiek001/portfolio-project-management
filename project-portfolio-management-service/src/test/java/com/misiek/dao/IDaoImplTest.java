package com.misiek.dao;

import com.misiek.domain.Project;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class IDaoImplTest<T> implements DaoTest {

    protected Class<T> clazz;

    @Override
    @Test
    public void find_ThenSuccess() {
        assertTrue(getDao().find(1L).isPresent());
    }

    @Override
    @Test
    public void findAllProjects_ThenSuccess() {
        List<Project> lists = getDao().findAll();
        assertEquals(3, lists.size());
    }

    @Override
    @Test
    public void delete_ThenSuccess() {
        getDao().delete(1L);
        assertEquals(2, getDao().findAll().size());
    }

    @Override
    @Test
    public void save_ThenSuccess() {
        T t = createNewEntity();
        assertTrue(getDao().save(t).isPresent());
    }

    abstract T createNewEntity();

    abstract IDao getDao();

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

}