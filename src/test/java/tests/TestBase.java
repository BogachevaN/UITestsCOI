package tests;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import lombok.val;
import auth.PasswordAuth;

import java.io.File;
import java.io.IOException;

public abstract class TestBase {

    protected static final PasswordAuth COI_AUTH = new PasswordAuth("gavrilov", "gavrilov", "coi", "webapp");

    private static final Configuration JSON_PATH_CONFIG = Configuration.builder()
            .jsonProvider(new JacksonJsonNodeJsonProvider())
            .mappingProvider(new JacksonMappingProvider())
            .build();

    protected DocumentContext getJsonResource(String name) throws IOException {
        val loader = TestBase.class.getClassLoader();
        val file = new File(loader.getResource("json/" + name).getFile());
        return JsonPath.using(JSON_PATH_CONFIG).parse(file);
    }
}

