package de.ruv.am.libraryscanner.mvn;

import de.ruv.am.libraryscanner.domain.api.Version;
import de.ruv.am.libraryscanner.domain.mvn.MvnVersionsResult;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MavenXmlMvnVersionsResultParserTest {

    private static final String DEPENDENCY_REPORT_PATH = "/repo/dependency-updates-report.xml";

    private MvnXmlResultParser mvnXmlResultParser;

    @Before
    public void setup() {
        this.mvnXmlResultParser = new MvnXmlResultParser();
    }

    @Test
    public void test_parseMavenDependencyReport() {
        final URL reportURL = this.getClass().getResource(DEPENDENCY_REPORT_PATH);
        final MvnVersionsResult mvnVersionsResult = mvnXmlResultParser.parseMavenDependencyReport(reportURL.getPath());
        final List<Version> incrementals = mvnVersionsResult.getDependencies().get(1).getIncrementals();
        final List<Version> minors = mvnVersionsResult.getDependencies().get(1).getMinors();
        final List<Version> majors = mvnVersionsResult.getDependencies().get(1).getMajors();

        assertEquals(7, mvnVersionsResult.getDependencies().size());
        assertEquals(6, incrementals.size());
        assertEquals(3, minors.size());
        assertEquals(1, majors.size());

        // incrementals
        assertEquals("3.0.1", incrementals.get(0).getNumber());
        assertEquals("3.0.2", incrementals.get(1).getNumber());
        assertEquals("3.0.3", incrementals.get(2).getNumber());
        assertEquals("3.0.4", incrementals.get(3).getNumber());
        assertEquals("3.0.5", incrementals.get(4).getNumber());
        assertEquals("3.1.0-alpha-1", incrementals.get(5).getNumber());

        // minors
        assertEquals("3.1.0", minors.get(0).getNumber());
        assertEquals("3.1.1", minors.get(1).getNumber());
        assertEquals("3.2.1", minors.get(2).getNumber());

        // majors
        assertEquals("4.0.0", majors.get(0).getNumber());

        assertEquals("org.apache.maven", mvnVersionsResult.getDependencies().get(1).getArtifact().getGroupId());
        assertEquals("maven-core", mvnVersionsResult.getDependencies().get(1).getArtifact().getArtifactId());
        assertEquals("3.0", mvnVersionsResult.getDependencies().get(1).getCurrentVersion());
        assertEquals("3.0.1", mvnVersionsResult.getDependencies().get(1).getNextVersion());
    }
}