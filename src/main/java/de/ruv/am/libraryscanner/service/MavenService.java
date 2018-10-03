package de.ruv.am.libraryscanner.service;

import de.ruv.am.libraryscanner.domain.mvn.MvnVersionsResult;

import java.io.File;

public interface MavenService {

    /**
     * Create the maven dependency report by execution the maven versions goal
     *
     * @param pom maven file
     *
     * @return list of dependency
     */
    MvnVersionsResult executeMvnVersionsGoal(final File pom);
}
