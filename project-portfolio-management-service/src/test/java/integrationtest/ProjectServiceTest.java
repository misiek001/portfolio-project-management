package integrationtest;

import com.misiek.dao.ProjectDao;
import com.misiek.domain.Project;
import com.misiek.service.ProjectService;
import com.misiek.spring.ServiceConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class ProjectServiceTest {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectService projectService;

    @BeforeEach
    void init() {
        Project project1 = new Project();
        project1.setName("Project1");
        projectDao.save(project1);

        Project project2 = new Project();
        project2.setName("Project2");
        projectDao.save(project2);

        Project project3 = new Project();
        project3.setName("Project3");
        projectDao.save(project3);
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
        projectService.delete(1L);
        assertEquals(2, projectService.findAll().size());
    }

    @Test
    void save_ThenSuccess(){
        Project project4 = new Project();
        project4.setName("Project4");
        assertTrue(projectService.save(project4).isPresent());
    }
}
