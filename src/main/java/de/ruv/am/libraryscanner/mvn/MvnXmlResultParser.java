package de.ruv.am.libraryscanner.mvn;

import de.ruv.am.libraryscanner.domain.api.Artifact;
import de.ruv.am.libraryscanner.domain.api.Dependency;
import de.ruv.am.libraryscanner.domain.api.Version;
import de.ruv.am.libraryscanner.domain.mvn.MvnVersionsResult;
import de.ruv.am.libraryscanner.domain.mvn.MvnVersionsSummary;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MvnXmlResultParser {

    private static final String SUMMARY = "summary";
    private static final String DEPENDENCIES = "dependencies";
    private static final String DEPENDENCYMANAGEMENT = "dependencyManagements";


    /**
     * parse dependency information from given maven dependency report
     *
     * @param reportPath file path to dependency report
     * @return list of dependency information
     */
    public MvnVersionsResult parseMavenDependencyReport(final String reportPath) {

        final MvnVersionsResult mvnVersionsResult = new MvnVersionsResult();
        try {
            final Element reportXML = new SAXBuilder().build(reportPath).getRootElement();
            final Element summaryNode = reportXML.getChild(SUMMARY);
            final MvnVersionsSummary summary = this.parseSummerySection(summaryNode);
            final List<Dependency> dependencies = this.parseDependencies(reportXML, DEPENDENCIES);
            final List<Dependency> dependencyManagement = this.parseDependencies(reportXML, DEPENDENCYMANAGEMENT);

            mvnVersionsResult.setSummary(summary);
            mvnVersionsResult.setDependencies(dependencies);
            mvnVersionsResult.setDependencyManagement(dependencyManagement);

        } catch (Exception e) {
            log.error("Error parsing dependency updates report");
        }

        return mvnVersionsResult;
    }

    private List<Dependency> parseDependencies(final Element element, final String type) {
        return element.getChild(type).getChildren()
                .stream()
                .map(this::parseDependencyReportNode)
                .collect(Collectors.toList());
    }

    private MvnVersionsSummary parseSummerySection(final Element summaryNode) {

        return MvnVersionsSummary.builder()
                .usingLastVersions(getNodeInfo(summaryNode, "usingLastVersion"))
                .nexVersionsAvailable(getNodeInfo(summaryNode, "nextVersionsAvailable"))
                .nextIncrementalAvailable(getNodeInfo(summaryNode, "nextIncremetalAvailable"))
                .nextMinorAvailable(getNodeInfo(summaryNode, "nextMinorAvailable"))
                .nextMajorAvailable(getNodeInfo(summaryNode, "nextMajorAvailable"))
                .build();
    }

    private Dependency parseDependencyReportNode(final Element dependencyNode) {
        final String groupId = this.getNodeInfo(dependencyNode, "groupId");
        final String artifactId = this.getNodeInfo(dependencyNode, "artifactId");
        final String currentVersion = this.getNodeInfo(dependencyNode, "currentVersion");
        final String nextVersion = this.getNodeInfo(dependencyNode, "nextVersion");
        final String status = this.getNodeInfo(dependencyNode, "status");

        final List<Version> incrementals = getVersions(dependencyNode, "incrementals");
        final List<Version> minors = getVersions(dependencyNode, "minors");
        final List<Version> majors = getVersions(dependencyNode, "majors");

        final Dependency dependency = new Dependency();
        dependency.setArtifact(new Artifact(groupId, artifactId));
        dependency.setNextVersion(nextVersion);
        dependency.setCurrentVersion(currentVersion);
        dependency.setStatus(status);
        dependency.setMajors(majors);
        dependency.setMinors(minors);
        dependency.setIncrementals(incrementals);

        return dependency;
    }

    private String getNodeInfo(final Element dependencyNode, final String attribute) {
        return Optional
                .ofNullable(dependencyNode.getChild(attribute))
                .orElse(new Element("empty")).getText();
    }

    private List<Version> getVersions(final Element dependencyNode, final String type) {
        return Optional.ofNullable(dependencyNode.getChild(type)).orElse(new Element("empty"))
                .getChildren()
                .stream()
                .map(element -> new Version(element.getText()))
                .collect(Collectors.toList());
    }
}
