package de.ruv.am.libraryscanner.domain.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Version {

    @Id
    @GeneratedValue
    private Long id;

    private String number;

    public Version(String number) {
        this.number = number;
    }
}
