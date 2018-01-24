package fr.lmarchau.test

import spock.lang.Specification

class CommandLineSpec extends Specification{

    def 'Command line should succes'() {
        setup:
        CommandLine cli = new CommandLine()

        when:
        def options = cli.parse(["-a", "1", "-b", "2"])


        then:
        options
        options.a == "1"
        options.b == "2"
    }

    def 'Command line should fail'() {
        setup:
        CommandLine cli = new CommandLine()

        when:
        def options = cli.parse(["-b", "2"])


        then:
        options == null
    }

}
