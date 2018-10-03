package de.ruv.am.libraryscanner.service;

public interface GitService {

    /**
     * clones into a git repo using ssh
     *
     * @param gitUri the uri using ssh protocol
     *
     * @return true if cloning was successful, false otherwise
     */
    boolean cloneRepo(final String gitUri);

    boolean clearDirectory();
}
