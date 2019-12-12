package integrationtest;

import com.misiek.dao.AbstractDao;
import com.misiek.dao.ProjectDaoImpl;
import com.misiek.domain.Project;
import com.misiek.spring.AppConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = AppConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class ProjectDaoImplTest extends AbstractDaoImplTest<Project> {

    @Autowired
    private ProjectDaoImpl projectDao;

    public ProjectDaoImplTest() {
        this.clazz = Project.class;
    }

    @Override
    protected Project createNewEntity() {
        return new Project();
    }

    @Override
    protected AbstractDao getDao() {
        return projectDao;
    }


}
