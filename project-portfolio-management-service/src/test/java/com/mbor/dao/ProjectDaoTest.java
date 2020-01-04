package com.mbor.dao;

import com.mbor.domain.Project;
import com.mbor.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@Rollback
class ProjectDaoTest extends IDaoImplTest<Project> {

    @Autowired
    public IProjectDao projectDao;

    @BeforeAll
    static void init(@Autowired ProjectDao projectDao){
        Project projectToSave = new Project();
        projectToSave.setProjectName("First Project");
        projectDao.save(projectToSave);
        projectToSave = new Project();
        projectToSave.setProjectName("Second Project");
        projectDao.save(projectToSave);
        projectToSave = new Project();
        projectToSave.setProjectName("Third Project");
        projectDao.save(projectToSave);
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
