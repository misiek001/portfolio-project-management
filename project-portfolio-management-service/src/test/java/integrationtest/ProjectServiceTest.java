package integrationtest;

import com.misiek.dao.ProjectDao;
import com.misiek.domain.Project;
import com.misiek.service.ProjectService;
import com.misiek.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@Rollback
@Transactional
public class ProjectServiceTest {

    @Autowired
    ProjectService projectService;

    @BeforeAll
    static void init(@Autowired ProjectDao projectDao) {
        projectDao.save(new Project());
        projectDao.save(new Project());
        projectDao.save(new Project());
    }

    @Test
    void find_ThenSuccess(){
        Optional<Project> result = projectService.find(1l);
        assertTrue(result.isPresent());
    }

    @Test
    void findAllProjects_ThenSuccess() {
        List<Project> lists = projectService.findAll();
        assertEquals(3, lists.size());
    }


    @Test
    void delete_ThenSuccess(){
        projectService.delete(3L);
        assertEquals(2, projectService.findAll().size());
    }

    void save_ThenSuccess(){
        Project project4 = new Project();
        project4.setProjectName("Project4");
        assertTrue(projectService.save(project4).isPresent());
    }
}
