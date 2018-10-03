package de.ruv.am.libraryscanner.service;

import de.ruv.am.libraryscanner.domain.api.DependencyReport;
import de.ruv.am.libraryscanner.repository.DependencyReportRepository;
import de.ruv.am.libraryscanner.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class DependencyReportService {

    private final DependencyService dependencyService;
    private final DependencyReportRepository dependencyReportRepository;
    private final ProjectRepository projectRepository;

    public DependencyReport save(DependencyReport dependencyReport) {
        dependencyReport.getDependencies().forEach(dependencyService::addDependency);
        projectRepository.save(dependencyReport.getProject());
        return dependencyReportRepository.save(dependencyReport);
    }

    public DependencyReport getById(Long id) {
        return dependencyReportRepository.findById(id).orElse(new DependencyReport());
    }
}
