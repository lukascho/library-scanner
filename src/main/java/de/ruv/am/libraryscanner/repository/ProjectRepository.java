package de.ruv.am.libraryscanner.repository;

import de.ruv.am.libraryscanner.domain.api.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
}
