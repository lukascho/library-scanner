package de.ruv.am.libraryscanner.domain.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Artifact {

    private final String groupId;
    private final String artifactId;
}
