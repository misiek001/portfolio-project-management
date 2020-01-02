package com.misiek.dao;

import com.misiek.domain.Project;
import com.misiek.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@Rollback
@Transactional
class ProjectDaoTest extends IDaoImplTest<Project> {

    @Autowired
    public ProjectDao projectDao;

    @BeforeAll
    static void init(@Autowired ProjectDao projectDao){
        projectDao.save(new Project());
        projectDao.save(new Project());
        projectDao.save(new Project());
    }



    public ProjectDaoTest() {
        this.clazz = Project.class;
    }

    @Override
    protected Project createNewEntity() {
        return new Project();
    }

    @Override
    protected IDao getDao() {
        return projectDao;
    }

}
