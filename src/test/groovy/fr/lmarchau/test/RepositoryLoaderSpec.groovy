package fr.lmarchau.test

import fr.lmarchau.test.model.Config
import spock.lang.Specification

class RepositoryLoaderSpec extends Specification {

    def 'load file'() {
        setup:
        def repositoryLoader = new RepositoryLoader()

        when:
        Config config = repositoryLoader.load('repo.yml')

        then:
        config
        config.repositories
        config.repositories.size == 2
        config.repositories[0].project == 'mine'
        config.repositories[0].repository == 'doc'
        config.repositories[1].project == 'mine'
        config.repositories[1].repository == 'versioning'

    }

}
