package fr.lmarchau.test

import com.stehno.ersatz.Decoders
import com.stehno.ersatz.DecodingContext
import com.stehno.ersatz.Encoders
import com.stehno.ersatz.ErsatzServer
import fr.lmarchau.test.model.Tag
import spock.lang.AutoCleanup
import spock.lang.Specification

import static com.stehno.ersatz.ContentType.APPLICATION_JSON
import static groovy.json.JsonOutput.toJson

class TagServiceSpec extends Specification {

    @AutoCleanup('stop')
    private final ErsatzServer server = new ErsatzServer({
        encoder APPLICATION_JSON, Tag, Encoders.json
        encoder(APPLICATION_JSON, List) { input ->
            "[${input.collect { i -> toJson(i) }.join(', ')}]"
        }

        decoder(APPLICATION_JSON) { byte[] bytes, DecodingContext dc ->
            Decoders.parseJson.apply(bytes, dc) as Tag
        }
    })

    def 'exists'() {
        setup:
        Tag tag = new Tag();
        tag.displayId = 'refs/tags/release-2.0.0'
        tag.hash = '8d51122def5632836d1cb1026e879069e10a1e13'
        tag.id = 'alpha'
        tag.latestChangeset = '8d351a10fb428c0c1239530256e21cf24f136e73'
        tag.latestCommit = '8d351a10fb428c0c1239530256e21cf24f136e73'
        tag.type = 'TAG'

        server.expectations {
            get('/rest/api/1.0/projects/mine/repos/doc/tags/alpha').called(1).responder {
                code 200
                content tag, APPLICATION_JSON
            }
        }

        TagService tagService = new TagService(server.httpUrl, 'user', 'pwd')

        when:
        def result = tagService.exists('mine', 'doc', 'alpha')

        then:
        result == true

        and:
        server.verify()



    }

}
