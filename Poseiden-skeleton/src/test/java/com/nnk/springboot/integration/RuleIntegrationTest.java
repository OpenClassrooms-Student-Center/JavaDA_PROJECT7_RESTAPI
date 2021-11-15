package com.nnk.springboot.integration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RuleIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Sql({"/schema.sql", "/rule-data.sql"})
    public void testList() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/rule/list/";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().contains("<td style=\"width: 10%\">name1</td>"));
        Assert.assertEquals(true, result.getBody().contains("<td style=\"width: 10%\">name2</td>"));
    }

    @Test
    @Sql({"/schema.sql", "/rule-data.sql"})
    public void testRuleValidate() throws Exception {

        final String baseUrl = "http://localhost:"+port+"/rule/validate/";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("name","rule");
        params.add("description","description");
        params.add("json","json");
        params.add("template","template");
        params.add("sqlStr","sql");
        params.add("sqlPart","sqlPart");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, requestEntity, String.class);

        Assert.assertEquals(302, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getHeaders().get("Location").toString().contains("rule/list"));

    }

    @Test
    @Sql({"/schema.sql", "/rule-data.sql"})
    public void testGetUpdateRule() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/rule/update/1";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    @Sql({"/schema.sql", "/rule-data.sql"})
    public void testUpdateRule() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/rule/update/1";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("name","rule");
        params.add("description","description");
        params.add("json","json");
        params.add("template","template");
        params.add("sqlStr","sql");
        params.add("sqlPart","sqlPart");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, requestEntity, String.class);

        Assert.assertEquals(302, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getHeaders().get("Location").toString().contains("rule/list"));
    }

    @Test
    @Sql({"/schema.sql", "/rule-data.sql"})
    public void testDeleteRule() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/rule/delete/1";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
    }
}
