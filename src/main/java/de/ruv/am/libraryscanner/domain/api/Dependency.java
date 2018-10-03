package de.ruv.am.libraryscanner.domain.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dependency {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Artifact artifact;

    @Column
    private String currentVersion;

    @Column
    private String nextVersion;

    @Column
    private String status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Version> incrementals;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Version> minors;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Version> majors;

    public Dependency() {
    }
}
