package de.ruv.am.libraryscanner.service.impl;

import de.ruv.am.libraryscanner.service.GitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GitServiceTest {

    private static String REPO_PATH = "git@github.com:mojohaus/build-helper-maven-plugin.git";

    @Autowired
    private GitService gitService;

    @Test
    public void test_cloneRepo() {
        boolean isSuccessful = gitService.cloneRepo(REPO_PATH);
        assertTrue(isSuccessful);
    }
}