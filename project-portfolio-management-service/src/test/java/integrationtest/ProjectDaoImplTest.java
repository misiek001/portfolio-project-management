package integrationtest;

import com.misiek.dao.ProjectDaoImpl;
import com.misiek.domain.Project;
import com.misiek.spring.AppConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfiguration.class)
@Transactional
class ProjectDaoImplTest {

    @Autowired
    private ProjectDaoImpl projectDao;

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
    void save_ThenSuccess(){
        Project project3 = new Project();
        project3.setName("Project4");
        System.out.println(projectDao.findAll().size());
        assertTrue(projectDao.save(project3).isPresent());
    }

    @Test
     void findAllProjects_ThenSuccess() {
        List<Project> lists = projectDao.findAll();
        lists.forEach(p -> System.out.println(p.getName()));
        assertEquals(3, lists.size());
    }

    @Test
    void find_ThenSuccess(){
        System.out.println(projectDao.findAll().size());
        assertTrue(projectDao.find(1L).isPresent());
    }

    @Test
    void delete_ThenSuccess(){
        projectDao.delete(1L);
        System.out.println(projectDao.findAll().size());
        assertEquals(2, projectDao.findAll().size());
    }


}
