package de.ruv.am.libraryscanner.mvn;

import de.ruv.am.libraryscanner.domain.api.Project;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class MvnPomHandler {

    private static final String POM = "pom.xml";
    private static final String MVN_VERSIONS_PLUGIN = "org.codehaus.mojo:versions-maven-plugin";

    private final Model model;

    public MvnPomHandler(final String baseDirectory) {
        this.model = this.readPOM(new File(baseDirectory + File.separator + POM));
    }

    public MvnPomHandler(final File pom) {
        this.model = this.readPOM(pom);
    }

    public Project getProjectInfo() {
        final String groupId = model.getGroupId() != null ? model.getGroupId() : model.getParent().getGroupId();
        return new Project(groupId, model.getArtifactId(), model.getVersion());
    }

    public Model readPOM(final File pom) {
        try {
            final MavenXpp3Reader reader = new MavenXpp3Reader();
            final Model model;
            model = reader.read(new FileInputStream(pom));
            return model;
        } catch (IOException | XmlPullParserException e) {
            log.error("Error reading maven pom", e);
        }

        return null;
    }

    public Plugin getPlugin(final String plugin) {
        return this.model.getBuild().getPluginsAsMap().get(plugin);
    }

    public boolean validateMavenVersionsPlugin() {
        final Plugin plugin = model.getBuild().getPluginsAsMap().get(MVN_VERSIONS_PLUGIN);
        return plugin != null && plugin.getVersion().equals("2.7");
    }

    public void updateMavenVersionPlugin() {
        final Plugin plugin = model.getBuild().getPluginsAsMap().get(MVN_VERSIONS_PLUGIN);
        if (plugin != null) {
            model.getBuild().removePlugin(plugin);
        }
        model.getBuild().addPlugin(createMavenVersionsPlugin());
    }

    public void write(File pom) {
        try {
            final MavenXpp3Writer writer = new MavenXpp3Writer();
            writer.write(new FileOutputStream(pom), model);
        } catch (IOException e) {
            log.error("Error writing pom", e);
        }
    }

    public void write(String baseDirectory) {
        final File pom = new File(baseDirectory + File.separator + POM);
        this.write(pom);
    }

    private Plugin createMavenVersionsPlugin() {
        final Plugin plugin = new Plugin();
        plugin.setArtifactId("versions-maven-plugin");
        plugin.setGroupId("org.codehaus.mojo");
        plugin.setVersion("2.7");
        return plugin;
    }
}
