package de.ruv.am.libraryscanner.domain.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.ruv.am.libraryscanner.domain.mvn.MvnVersionsSummary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DependencyReport {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String source;

    @OneToOne
    private Project project;

    @Transient
    private MvnVersionsSummary summary;

    @OneToMany
    private List<Dependency> dependencies;

    @OneToMany
    private List<Dependency> dependencyManagement;
}
