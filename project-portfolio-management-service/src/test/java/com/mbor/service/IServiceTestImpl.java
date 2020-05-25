package com.mbor.service;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
public abstract class IServiceTestImpl<T> implements IServiceTest {

    protected static int createdEntitiesNumber = 3;

    protected static Long firstEntityId;

    protected static List<Long> entityIdList = new LinkedList<>();

    protected static Random random = new Random();

    @Override
    @Test
    public void findThenSuccess() {
        assertNotNull(getService().findInternal(firstEntityId));
    }

    @Override
    @Test
    public void findAllThenSuccess() {
        assertEquals(createdEntitiesNumber, getService().findAllInternal().size());
    }

    @Override
    @Test
    public void deleteThenSuccess() {
        getService().deleteInternal(firstEntityId);
        assertEquals(createdEntitiesNumber - 1, getService().findAllInternal().size());
    }

    @Override
    @Test
    public void saveThenSuccess() {
        System.out.println("Begining of save test");
        T t = createNewEntity();
        assertNotNull(getService().saveInternal(t));
        assertEquals(createdEntitiesNumber + 1, getService().findAllInternal().size());
        System.out.println("End of save test. SHould be rollback");
    }

    protected abstract T createNewEntity();

    protected abstract IInternalService getService();
}
