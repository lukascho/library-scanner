package de.ruv.am.libraryscanner.domain.mvn;

import de.ruv.am.libraryscanner.domain.api.Dependency;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public final class MvnVersionsResult {
    MvnVersionsSummary summary;
    List<Dependency> dependencies;
    List<Dependency> dependencyManagement;
}
