package de.ruv.am.libraryscanner.repository;

import de.ruv.am.libraryscanner.domain.api.Artifact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtifactRepository extends CrudRepository<Artifact, Long> {

    default Artifact addArtifact(final Artifact artifact) {
        Artifact dbArtifact = exists(artifact);
        if (dbArtifact == null) {
            dbArtifact = this.save(artifact);
        }
        return dbArtifact;
    }
    default Artifact exists(final Artifact artifact) {
        return getByGroupIdAndArtifactId(artifact.getGroupId(), artifact.getArtifactId());
    }

     Artifact getByGroupIdAndArtifactId(String groupId, final String artifactId);
}
