package fr.lmarchau.test.model

import groovy.transform.Canonical

@Canonical
class Branch {

    String name
    String startPoint
    String message

    String id
    String displayId
    String type
    String latestComit
    String latestChangeset
    boolean isDefault

}
