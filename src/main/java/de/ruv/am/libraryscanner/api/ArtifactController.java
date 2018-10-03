package de.ruv.am.libraryscanner.api;

import de.ruv.am.libraryscanner.domain.api.Artifact;
import de.ruv.am.libraryscanner.service.ArtifactService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/artifacts")
public class ArtifactController {

    private final ArtifactService artifactService;

    @GetMapping(value = "/", produces = "application/json")
    public List<Artifact> getAllArtifacts() {
        return artifactService.getAllArtifacts();
    }
}
