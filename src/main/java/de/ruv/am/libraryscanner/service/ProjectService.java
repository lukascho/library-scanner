package de.ruv.am.libraryscanner.service;

import de.ruv.am.libraryscanner.domain.api.Project;
import de.ruv.am.libraryscanner.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(new Project());
    }
}
