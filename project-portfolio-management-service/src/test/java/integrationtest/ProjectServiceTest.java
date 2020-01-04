package integrationtest;

import com.mbor.dao.ProjectDao;
import com.mbor.domain.Project;
import com.mbor.service.IProjectService;
import com.mbor.spring.ServiceConfiguration;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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
    IProjectService projectService;

    @Autowired
    SessionFactory sessionFactory;

    @BeforeAll
    static void init(@Autowired ProjectDao projectDao) {
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
    @Disabled
    //FixMe
    void delete_ThenSuccess(){

       projectService.delete(3L);
       assertEquals(2, projectService.findAll().size());

   }

    @Test
    void save_ThenSuccess(){
        Project project4 = new Project();
        project4.setProjectName("Fourth Project");
        assertTrue(projectService.saveInternal(project4).isPresent());
        assertEquals(4, projectService.findAll().size());
    }

}
