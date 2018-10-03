package de.ruv.am.libraryscanner.domain.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Artifact {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String groupId;

    @Column
    private String artifactId;

    @ManyToOne
    private Dependency dependency;

    public Artifact(String groupId, String artifactId) {
        this.groupId = groupId;
        this.artifactId = artifactId;
    }
}
