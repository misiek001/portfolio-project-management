package com.mbor.dao;

import com.mbor.domain.Project;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@Rollback
@ActiveProfiles("test")
class ProjectDaoTest extends IDaoImplTest<Project> {

    @Autowired
    public IProjectDao projectDao;

    static Random random = new Random();

    @BeforeAll
    static void init(@Autowired IProjectDao projectDao){
        for (int i = 0; i < IDaoImplTest.createdEntitiesNumber; i++) {
            Project project = new Project();
            project.setProjectName("ProjectName" + random.nextLong());
            projectDao.save(project);
        }
    }

    @Override
    protected Project createNewEntity() {
        Project project = new Project();
        project.setProjectName("ProjectName" + ProjectDaoTest.random.nextLong());
        return  project;
    }

    @Override
    protected IDao getDao() {
        return projectDao;
    }

}
