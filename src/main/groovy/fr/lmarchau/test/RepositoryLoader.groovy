package fr.lmarchau.test

import fr.lmarchau.test.model.Config
import fr.lmarchau.test.model.Repository
import org.yaml.snakeyaml.TypeDescription
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

class RepositoryLoader {


    def load(def file) {
        def datas = new File(getClass().getClassLoader().getResource(file).toURI()).text
        Constructor c = new Constructor(Config)
        TypeDescription t = new TypeDescription(Config)
        t.addPropertyParameters('repositories', Repository)
        c.addTypeDescription(t)
        Yaml yaml = new Yaml(c)
        yaml.load(datas)
    }

}
