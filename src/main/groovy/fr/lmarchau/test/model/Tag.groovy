package fr.lmarchau.test.model

import groovy.transform.Canonical

@Canonical
class Tag {

    String id
    String displayId
    String type
    String latestCommit
    String latestChangeset
    String hash

}
