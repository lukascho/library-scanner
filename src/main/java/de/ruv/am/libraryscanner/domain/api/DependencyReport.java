package de.ruv.am.libraryscanner.domain.api;

import de.ruv.am.libraryscanner.domain.mvn.MvnVersionsSummary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class DependencyReport {

    private String source;
    private Project project;
    private MvnVersionsSummary summary;
    private List<Dependency> dependencies;
    private List<Dependency> dependencyManagement;
}
