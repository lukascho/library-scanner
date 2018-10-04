package de.ruv.am.libraryscanner.api;


import de.ruv.am.libraryscanner.domain.api.Dependency;
import de.ruv.am.libraryscanner.service.DependencyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/dependencies")
public class DependencyController {

    private final DependencyService dependencyService;

    @GetMapping(value = "/", produces = "application/json")
    public List<Dependency> getAllDependencies() {
        return dependencyService.getAllDependencies();
    }
}
