package de.ruv.am.libraryscanner.service.impl;

import de.ruv.am.libraryscanner.domain.mvn.MvnVersionsResult;
import de.ruv.am.libraryscanner.mvn.MvnXmlResultParser;
import de.ruv.am.libraryscanner.mvn.execute.MvnExecutor;
import de.ruv.am.libraryscanner.service.MavenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
@AllArgsConstructor
public class MavenByInvocationService implements MavenService {

    private static final String REPORT_PATH = "/target/dependency-updates-report.xml";

    private final MvnXmlResultParser parser;
    private final MvnExecutor mvnExecutor;

    public MvnVersionsResult executeMvnVersionsGoal(final File pom) {
        boolean isSuccessful = mvnExecutor.executeVersionsGoal(pom.getPath());
        final String reportPath = pom.getParent() + File.separator + REPORT_PATH;

        if (isSuccessful) {
            return parser.parseMavenDependencyReport(reportPath);
        }

        return new MvnVersionsResult();
    }
}
