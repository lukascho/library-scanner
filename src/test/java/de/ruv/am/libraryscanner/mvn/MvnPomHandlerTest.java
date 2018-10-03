package de.ruv.am.libraryscanner.mvn;

import de.ruv.am.libraryscanner.domain.api.Project;
import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class MvnPomHandlerTest {

    private static final String POM = "pom.xml";

    private MvnPomHandler mvnPomHandler;

    @Before
    public void setup() {
        final String baseDirectory = this.getRepoPath();
        mvnPomHandler = new MvnPomHandler(baseDirectory);
    }

    @Test
    public void readPOM() {
        final String pomPath = this.getRepoPath() + File.separator + POM;

        final Model model = mvnPomHandler.readPOM(new File(pomPath));
        final String groupId = model.getGroupId() != null ? model.getGroupId() : model.getParent().getGroupId();

        assertEquals("build-helper-maven-plugin", model.getArtifactId());
        assertEquals("org.codehaus.mojo", groupId);
        assertEquals("3.1.0-SNAPSHOT", model.getVersion());
    }

    @Test
    public void test_getProjectInfo() {
        Project project = mvnPomHandler.getProjectInfo();
        assertEquals("build-helper-maven-plugin", project.getArtifactId());
    }

    @Test
    public void test_updateMavenVersionPlugin() {
        this.mvnPomHandler.updateMavenVersionPlugin();
        final Plugin plugin = this.mvnPomHandler.getPlugin("org.codehaus.mojo:versions-maven-plugin");
        assertEquals("org.codehaus.mojo:versions-maven-plugin:2.7", plugin.getGroupId() + ":" + plugin.getArtifactId() + ":2.7");
    }

    @Test
    public void test_validateMavenVersionsPlugin() {
        assertFalse(this.mvnPomHandler.validateMavenVersionsPlugin());
    }

    @Test
    public void test_writeWithFile() throws Exception {
        final String baseDirectory = this.getRepoPath();
        final String testFilePath = baseDirectory + File.separator + "/pom_test.xml";
        if (Files.isRegularFile(Paths.get(testFilePath))) {
            FileUtils.forceDelete(new File(testFilePath));
        }
        this.mvnPomHandler.updateMavenVersionPlugin();
        this.mvnPomHandler.write(new File(testFilePath));

        assertTrue(Files.isRegularFile(Paths.get(testFilePath)));
    }

    @Test
    public void test_writeWithBaseDirectory() throws IOException {
        final String baseDirectory = this.getRepoPath();
        final String testFilePath = baseDirectory + File.separator + POM;
        final Plugin plugin = this.mvnPomHandler.getPlugin("org.codehaus.mojo:versions-maven-plugin");

        this.mvnPomHandler.updateMavenVersionPlugin();
        this.mvnPomHandler.write(baseDirectory);

        assertTrue(Files.isRegularFile(Paths.get(testFilePath)));
        assertEquals("org.codehaus.mojo:versions-maven-plugin:2.7", plugin.getGroupId() + ":" + plugin.getArtifactId() + ":2.7");
    }

    private String getRepoPath() {
        return this.getClass().getResource("/repo").getPath();
    }
}