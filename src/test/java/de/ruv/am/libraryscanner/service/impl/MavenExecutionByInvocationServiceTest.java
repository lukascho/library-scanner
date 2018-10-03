package de.ruv.am.libraryscanner.service.impl;

import de.ruv.am.libraryscanner.domain.mvn.MvnVersionsResult;
import de.ruv.am.libraryscanner.service.MavenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;


@SpringBootTest
@RunWith(SpringRunner.class)
public class MavenExecutionByInvocationServiceTest {

    @Autowired
    private MavenService mavenService;

    @Test
    public void test_fetchMavenDependencyUpdateInformation() {
        URL projectBaseDirectory = this.getClass().getResource("/repo");
        MvnVersionsResult report = mavenService.executeMvnVersionsGoal(new File(projectBaseDirectory.getPath()));
        assertEquals(7, report.getDependencies().size());
        assertEquals(3, report.getDependencyManagement().size());
    }
}