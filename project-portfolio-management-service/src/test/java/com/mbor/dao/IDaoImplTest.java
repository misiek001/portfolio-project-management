package com.mbor.dao;

import com.mbor.domain.Project;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@Rollback
@ActiveProfiles("test")
public abstract class IDaoImplTest<T> implements DaoTest {

    static int createdEntitiesNumber = 3;

    @Override
    @Test
    public void find_ThenSuccess() {
        assertTrue(getDao().find(1L).isPresent());
    }

    @Override
    @Test
    public void findAllProjects_ThenSuccess() {
        List<Project> lists = getDao().findAll();
        assertEquals(createdEntitiesNumber, lists.size());
    }

    @Override
    @Test
    @Disabled
    //FixMe
    public void delete_ThenSuccess() {
        getDao().delete(1L);
        assertEquals(createdEntitiesNumber - 1, getDao().findAll().size());
    }

    @Override
    @Test
    public void save_ThenSuccess() {
        T t = createNewEntity();
        assertTrue(getDao().save(t).isPresent());
    }

    abstract T createNewEntity();

    abstract IDao getDao();


}
