package de.ruv.am.libraryscanner.api;

import de.ruv.am.libraryscanner.configuration.GitConfig;
import de.ruv.am.libraryscanner.domain.api.DependencyReport;
import de.ruv.am.libraryscanner.domain.mvn.MvnVersionsResult;
import de.ruv.am.libraryscanner.domain.api.Project;
import de.ruv.am.libraryscanner.mvn.MvnPomHandler;
import de.ruv.am.libraryscanner.service.GitService;
import de.ruv.am.libraryscanner.service.MavenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/dependency-report")
public class DependencyReportController {

    private static final String POM_FILE = "pom.xml";

    private final GitConfig gitConfig;
    private final GitService git;
    private final MavenService maven;

    @PostMapping(value = "/", produces = "application/json", consumes = "application/json")
    public ResponseEntity<DependencyReport> createDependencyReport(@RequestBody DependencyReport dependencyReport) {

        if (!git.clearDirectory()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (git.cloneRepo(dependencyReport.getSource())) {
            final String pomPath = gitConfig.getDirectory() + File.separator + POM_FILE;

            if (!Files.exists(Paths.get(pomPath))) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            final File pom = new File(pomPath);
            final MvnPomHandler pomValidator = new MvnPomHandler(pom);
            final Project project = pomValidator.getProjectInfo();

            if (project.isValid()) {

                if (!pomValidator.validateMavenVersionsPlugin()) {
                    pomValidator.updateMavenVersionPlugin();
                    pomValidator.write(gitConfig.getDirectory());
                }

                final MvnVersionsResult report = maven.executeMvnVersionsGoal(pom);

                dependencyReport.setProject(project);
                dependencyReport.setSummary(report.getSummary());
                dependencyReport.setDependencies(report.getDependencies());
                dependencyReport.setDependencyManagement(report.getDependencyManagement());

                return new ResponseEntity<>(dependencyReport, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
