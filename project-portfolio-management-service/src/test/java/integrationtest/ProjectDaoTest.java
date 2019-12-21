package integrationtest;

import com.misiek.dao.IDao;
import com.misiek.dao.ProjectDao;
import com.misiek.domain.Project;
import com.misiek.spring.ServiceConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = ServiceConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class ProjectDaoTest extends IDaoImplTest<Project> {

    @Autowired
    public ProjectDao projectDao;

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
