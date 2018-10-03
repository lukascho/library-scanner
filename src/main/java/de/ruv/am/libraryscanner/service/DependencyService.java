package de.ruv.am.libraryscanner.service;

import de.ruv.am.libraryscanner.domain.api.Artifact;
import de.ruv.am.libraryscanner.domain.api.Dependency;
import de.ruv.am.libraryscanner.repository.ArtifactRepository;
import de.ruv.am.libraryscanner.repository.DependencyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DependencyService {

    private final DependencyRepository dependencyRepository;
    private final ArtifactRepository artifactRepository;

    public Dependency addDependency(Dependency dependency) {
        Artifact dbArtifact = artifactRepository.getByGroupIdAndArtifactId(dependency.getArtifact().getGroupId(),
                dependency.getArtifact().getArtifactId());

        if (dbArtifact == null) {
            dbArtifact = artifactRepository.addArtifact(dependency.getArtifact());
        }

        dependency.setArtifact(dbArtifact);
        return this.dependencyRepository.save(dependency);
    }

    public List<Dependency> addDependencies(final List<Dependency> dependencies) {
        return dependencies.stream().map(this::addDependency).collect(Collectors.toList());
    }

    public List<Dependency> getAllDependencies() {
        final List<Dependency> result = new ArrayList<>();
        dependencyRepository.findAll().iterator().forEachRemaining(result::add);
        return result;
    }
}
