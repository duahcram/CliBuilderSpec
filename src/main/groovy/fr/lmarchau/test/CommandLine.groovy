package fr.lmarchau.test

class CommandLine {

    def cli

    CommandLine() {
        cli = new CliBuilder(usage:'Usage')
        cli.a(required:true, args:1, 'Arg A')
        cli.b(args:1, 'Arg B')
    }

    def parse(args) {
        return cli.parse(args)
    }

}
