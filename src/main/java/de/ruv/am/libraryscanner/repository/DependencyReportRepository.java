package de.ruv.am.libraryscanner.repository;

import de.ruv.am.libraryscanner.domain.api.DependencyReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DependencyReportRepository extends CrudRepository<DependencyReport, Long> {

}
