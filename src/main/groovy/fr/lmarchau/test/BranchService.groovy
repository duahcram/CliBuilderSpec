package fr.lmarchau.test

import fr.lmarchau.test.model.Branch
import groovyx.net.http.ChainedHttpConfig
import groovyx.net.http.FromServer
import groovyx.net.http.HttpBuilder

import static groovyx.net.http.ContentTypes.JSON
import static groovyx.net.http.NativeHandlers.Parsers.json

class BranchService {

    HttpBuilder http

    BranchService(String url, String user, String pwd) {
        http = HttpBuilder.configure {
            request.uri = url
        }
    }

    Branch create(String project, String repo, String commit, String branchName) {
        def path = "/rest/api/1.0/projects/${project}/repos/${repo}/branches"
        Branch branch = new Branch(branchName, commit, "Création de la branche ${branchName}")
        http.post {
            request.uri.path = path
            request.body = branch
            request.contentType = JSON[0]
            response.parser JSON[0], { ChainedHttpConfig config, FromServer fs ->
                json(config, fs) as Branch
            }
        }
    }
}
