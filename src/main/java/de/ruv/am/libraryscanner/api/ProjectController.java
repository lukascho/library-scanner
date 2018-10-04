package de.ruv.am.libraryscanner.api;


import de.ruv.am.libraryscanner.domain.api.DependencyReport;
import de.ruv.am.libraryscanner.domain.api.Project;
import de.ruv.am.libraryscanner.service.DependencyReportService;
import de.ruv.am.libraryscanner.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final DependencyReportService dependencyReportService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public Project getProject(@PathVariable("id") String id) {
        return projectService.getProjectById(Long.parseLong(id));
    }


    @GetMapping(value = "/{id}/dependency-report", produces = "application/json")
    public DependencyReport getDependencyReportForProject(@PathVariable("id") String id) {
        Project project = projectService.getProjectById(Long.parseLong(id));
        return dependencyReportService.getById(project.getDependencyReport().getId());
    }
}
