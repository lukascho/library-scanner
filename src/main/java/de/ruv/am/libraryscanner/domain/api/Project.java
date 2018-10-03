package de.ruv.am.libraryscanner.domain.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String groupId;

    @Column
    private String artifactId;

    @Column
    private String version;

    public Project(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    @JsonIgnore
    public boolean isValid() {
        return !(groupId.equals("") || artifactId.equals("") || version.equals(""));
    }
}
