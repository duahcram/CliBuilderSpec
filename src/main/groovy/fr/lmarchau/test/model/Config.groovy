package fr.lmarchau.test.model

import groovy.transform.Canonical

@Canonical
class Config {

    String version
    List<Repository> repositories

}
