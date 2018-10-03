package de.ruv.am.libraryscanner.domain.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;


@Getter
public class Project {
    private final String groupId;
    private final String artifactId;
    private final String version;

    public Project(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    @JsonIgnore
    public boolean isValid() {
        return !(groupId.equals("") || artifactId.equals("") || version.equals(""));
    }
}
