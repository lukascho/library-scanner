package de.ruv.am.libraryscanner.service.impl;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import de.ruv.am.libraryscanner.configuration.GitConfig;
import de.ruv.am.libraryscanner.service.GitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.util.FS;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@AllArgsConstructor
@Slf4j
@Service
public class JGitService implements GitService {

    private final GitConfig gitConfig;

    @Override
    public boolean cloneRepo(final String gitUri) {
        try {
            log.debug("Cloning into repository");
            FileUtils.deleteDirectory(new File(gitConfig.getDirectory()));

            Git.cloneRepository()
                .setURI(gitUri)
                .setTransportConfigCallback(
                        transport -> {
                            SshTransport sshTransport = (SshTransport)transport;
                            sshTransport.setSshSessionFactory( createFactory() );
                        }
                )
                .setDirectory(new File(gitConfig.getDirectory()))
                .call();

            return true;

        } catch (Exception e) {
            log.error("Error cloning repository:", e);
        }

        return false;
    }

    public boolean clearDirectory() {
        try {
            FileUtils.deleteDirectory(new File(gitConfig.getDirectory()));
            return true;
        } catch (IOException e) {
            log.error("Clearing git directory");
        }

        return false;
    }

    private SshSessionFactory createFactory() {
        return new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host host, Session session ) {
                session.setConfig("StrictHostKeyChecking", "no");
            }

            @Override
            protected JSch createDefaultJSch(FS fs ) throws JSchException {
                JSch defaultJSch = super.createDefaultJSch( fs );
                defaultJSch.addIdentity( gitConfig.getSshKey() );
                return defaultJSch;
            }
        };
    }
}
