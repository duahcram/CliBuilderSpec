package fr.lmarchau.test

import groovyx.net.http.HttpBuilder

class TagService {


    HttpBuilder http

    TagService(String url, String user, String pwd) {
        http = HttpBuilder.configure {
            request.uri = url
        }
    }

    def exists(String project, String repo, String tagName) {
        def path = "/rest/api/1.0/projects/${project}/repos/${repo}/tags/${tagName}"
        http.get() {
            request.uri.path = path
            response.success { fs ->
                true
            }
            response.failure { fs ->
                false
            }
        }
    }

}
