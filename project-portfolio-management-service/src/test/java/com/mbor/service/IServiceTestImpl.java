package com.mbor.service;

import com.mbor.dao.IDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public abstract class IServiceTestImpl<T> implements IServiceTest {

    protected static int createdEntitiesNumber = 3;

    protected static Long firstEntityId;

    protected static List<Long> entityIdList = new LinkedList<>();

    protected static Random random = new Random();


    @Override
    @Test
    public void findInternalThenSuccess() {
        Optional<T> entityOptional = Optional.of(createNewEntity());

        when(getDao().find(getElementIndex(0))).thenReturn(entityOptional);

        assertNotNull(getService().findInternal(firstEntityId));
    }

    @Override
    @Test
    public void findAllInternalThenSuccess() {
        List<T> list = new ArrayList<>();
        for(int i = 0; i < createdEntitiesNumber; i++){
            list.add(createNewEntity());
        }

        when(getDao().findAll()).thenReturn(list);

        assertEquals(createdEntitiesNumber, getService().findAllInternal().size());
    }

    @Override
    @Test
    public void deleteInternalThenSuccess() {
        getService().deleteInternal(getElementIndex(0));

        verify(getDao(), times(1)).delete(getElementIndex(0));
    }

    @Override
    @Test
    public void saveInternalThenSuccess() {
        T entity = createNewEntity();
        Optional<T> entityOptional = Optional.of(entity);

        when(getDao().save(entity)).thenReturn(entityOptional);

        assertNotNull(getService().saveInternal(entity));
    }

    protected static Long getElementIndex(int index){
        return entityIdList.get(index);
    }

    protected abstract T createNewEntity();

    protected abstract IDao getDao();

    protected abstract IInternalService getService();
}
