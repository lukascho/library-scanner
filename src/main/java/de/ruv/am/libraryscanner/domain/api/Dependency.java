package de.ruv.am.libraryscanner.domain.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dependency {

    private Artifact artifact;
    private String currentVersion;
    private String nextVersion;
    private String status;

    private List<String> incrementals;
    private List<String> minors;
    private List<String> majors;
}
