package de.ruv.am.libraryscanner.domain.mvn;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MvnVersionsSummary {

    private String usingLastVersions;
    private String nexVersionsAvailable;
    private String nextIncrementalAvailable;
    private String nextMinorAvailable;
    private String nextMajorAvailable;
}
