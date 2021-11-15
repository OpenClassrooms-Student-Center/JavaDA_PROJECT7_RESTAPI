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
import java.sql.Timestamp;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TradeIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Sql({"/schema.sql", "/trade-data.sql"})
    public void testList() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/trade/list/";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().contains("<td style=\"width: 10%\">name1</td>"));
        Assert.assertEquals(true, result.getBody().contains("<td style=\"width: 10%\">name2</td>"));
    }

    @Test
    @Sql({"/schema.sql", "/trade-data.sql"})
    public void testRuleValidate() throws Exception {

        final String baseUrl = "http://localhost:"+port+"/trade/validate/";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("tradeId", "1");
        params.add("account", "account");
        params.add("type", "type");
        params.add("buyQuantity","2");
        params.add("sellQuantity","3");
        params.add("buyPrice","4");
        params.add("sellPrice","5");
        params.add("benchmark","benchmark");
        params.add("tradeDate","2021-11-01");
        params.add("security","security");
        params.add("status","status");
        params.add("trader","trader");
        params.add("book","book");
        params.add("creationName","creationName");
        params.add("creationDate","2021-11-02");
        params.add("revisionName","revisionName");
        params.add("revisionDate","2021-11-03");
        params.add("dealName","dealName");
        params.add("dealType","dealType");
        params.add("sourceListId","6");
        params.add("side","side");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, requestEntity, String.class);

        Assert.assertEquals(302, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getHeaders().get("Location").toString().contains("trade/list"));

    }

    @Test
    @Sql({"/schema.sql", "/trade-data.sql"})
    public void testGetUpdateRule() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/trade/update/1";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    @Sql({"/schema.sql", "/trade-data.sql"})
    public void testUpdateRule() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/trade/update/1";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("tradeId", "1");
        params.add("account", "account");
        params.add("type", "type");
        params.add("buyQuantity","2");
        params.add("sellQuantity","3");
        params.add("buyPrice","4");
        params.add("sellPrice","5");
        params.add("benchmark","benchmark");
        params.add("tradeDate","2021-11-01");
        params.add("security","security");
        params.add("status","status");
        params.add("trader","trader");
        params.add("book","book");
        params.add("creationName","creationName");
        params.add("creationDate","2021-11-02");
        params.add("revisionName","revisionName");
        params.add("revisionDate","2021-11-03");
        params.add("dealName","dealName");
        params.add("dealType","dealType");
        params.add("sourceListId","6");
        params.add("side","side");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, requestEntity, String.class);

        Assert.assertEquals(302, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getHeaders().get("Location").toString().contains("trade/list"));
    }

    @Test
    @Sql({"/schema.sql", "/trade-data.sql"})
    public void testDeleteRule() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/trade/delete/1";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
    }
}
