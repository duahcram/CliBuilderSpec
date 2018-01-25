package fr.lmarchau.test

import com.stehno.ersatz.Decoders
import com.stehno.ersatz.DecodingContext
import com.stehno.ersatz.Encoders
import com.stehno.ersatz.ErsatzServer
import fr.lmarchau.test.model.Branch
import spock.lang.Specification

import static com.stehno.ersatz.ContentType.APPLICATION_JSON
import static groovy.json.JsonOutput.toJson

class BranchServiceSpec extends Specification {

    private final ErsatzServer server = new ErsatzServer({
        encoder APPLICATION_JSON, Branch, Encoders.json
        encoder (APPLICATION_JSON, List) { input ->
            "[${input.collect { i -> toJson(i) }.join(', ')}]"
        }

        decoder(APPLICATION_JSON) { byte[] bytes, DecodingContext dc ->
            Decoders.parseJson.apply(bytes, dc) as Branch
        }

    })

    def 'create'() {
        setup:
        Branch branch = new Branch('V.0.0.x', '123456','Création de la branche V.0.0.x')

        Branch newBranch = new Branch()
        newBranch.id = 'refs/branches/V.0.0.x'
        newBranch.displayId = 'V.0.0.x'
        newBranch.latestChangeset = '123456'
        newBranch.isDefault = false
        newBranch.latestComit = '123456'
        newBranch.type = 'BRANCH'

        server.expectations {
            post('/rest/api/1.0/projects/mine/repos/doc/branches') {
                called 1
                body branch, APPLICATION_JSON
                responder {
                    code 201
                    content newBranch, APPLICATION_JSON
                }
            }
        }

        BranchService branchService = new BranchService(server.httpUrl, 'user', 'pwd')

        when:
        def result = branchService.create('mine', 'doc', branch.startPoint, branch.name)

        then:
        newBranch == result

        and:
        server.verify()

    }

}
