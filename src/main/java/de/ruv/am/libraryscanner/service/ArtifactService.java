package de.ruv.am.libraryscanner.service;

import de.ruv.am.libraryscanner.domain.api.Artifact;
import de.ruv.am.libraryscanner.domain.api.Dependency;
import de.ruv.am.libraryscanner.repository.ArtifactRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ArtifactService {

    @Autowired
    private final ArtifactRepository repository;

    public void addArtifacts(final List<Dependency> dependencies) {
        dependencies.forEach(dependency -> {
            Artifact dbArtifact = repository.addArtifact(dependency.getArtifact());
            dependency.setArtifact(dbArtifact);
        });
    }

    public List<Artifact> getAllArtifacts() {
        final List<Artifact> result = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(result::add);
        return result;
    }
}
