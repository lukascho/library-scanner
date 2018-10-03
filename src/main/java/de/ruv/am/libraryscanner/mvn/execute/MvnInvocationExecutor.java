package de.ruv.am.libraryscanner.mvn.execute;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.shared.invoker.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;

@Slf4j
@Component
public class MvnInvocationExecutor implements MvnExecutor {

    private static final String MVN_VERSIONS_GOAL = "versions:dependency-updates-report -DdependencyUpdatesReportFormats=xml -DprocessDependencyManagement=false";

    public boolean executeVersionsGoal(String path) {
        try {
            final InvocationRequest request = new DefaultInvocationRequest();
            request.setPomFile(new File(path));
            request.setGoals(Collections.singletonList(MVN_VERSIONS_GOAL));

            Invoker invoker = new DefaultInvoker();
            invoker.setOutputHandler(null);
            InvocationResult result = invoker.execute(request);

            return result.getExitCode() == 0;
        } catch (MavenInvocationException e) {
            log.error("Maven build failed");
        }

        return true;
    }
}
