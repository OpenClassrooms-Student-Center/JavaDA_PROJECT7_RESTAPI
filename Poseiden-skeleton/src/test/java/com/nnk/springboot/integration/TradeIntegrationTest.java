package com.nnk.springboot.integration;
import com.nnk.springboot.TestApplicationConfig;
import com.nnk.springboot.config.security.JwtTokenUtil;
import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.impl.AuthenticationUserDetailService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplicationConfig.class)
public class TradeIntegrationTest {
    @LocalServerPort
    protected int port;
    @Autowired
    protected TestRestTemplate restTemplate;
    @Autowired
    protected RestTemplateBuilder restBuilder;

    public RestTemplate getUserRestTemplate() throws Exception  {
        final String baseUrl = "http://localhost:"+port+"/authenticate";
        URI uriAuthenticate = new URI(baseUrl);

        User user = new User();
        user.setPassword("test");
        user.setUsername("username");

        String token = this.restTemplate.postForObject(uriAuthenticate, user, String.class);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization" , "Bearer "+ token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
        System.out.println(token);
        return restBuilder
                .additionalInterceptors((ClientHttpRequestInterceptor) (request, body, execution) -> {
                    request.getHeaders().add("Authorization", "Bearer " + token);
                    return execution.execute(request, body);
                }).build();
    }

    @Test
    @Sql({"/schema.sql", "/trade-data.sql"})
    public void testList() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/trade/list/";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = getUserRestTemplate().getForEntity(uri, String.class);
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

        ResponseEntity<String> result = getUserRestTemplate().postForEntity(uri, requestEntity, String.class);

        Assert.assertEquals(302, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getHeaders().get("Location").toString().contains("trade/list"));

    }

    @Test
    @Sql({"/schema.sql", "/trade-data.sql"})
    public void testGetUpdateRule() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/trade/update/1";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = getUserRestTemplate().getForEntity(uri, String.class);

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

        ResponseEntity<String> result = getUserRestTemplate().postForEntity(uri, requestEntity, String.class);

        Assert.assertEquals(302, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getHeaders().get("Location").toString().contains("trade/list"));
    }

    @Test
    @Sql({"/schema.sql", "/trade-data.sql"})
    public void testDeleteRule() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/trade/delete/1";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = getUserRestTemplate().getForEntity(uri, String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
    }
}
