package de.ruv.am.libraryscanner.repository;

import de.ruv.am.libraryscanner.domain.api.Dependency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DependencyRepository extends CrudRepository<Dependency, Long> {

    default Dependency addDependency(Dependency dependency) {
        return this.save(dependency);
    }
}
