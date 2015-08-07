package io.pivotal.activities.supports;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

public class ControllerTestBase extends SpringTestBase {
    @Inject private WebApplicationContext webApplicationContext;
    private MockMvc mvc;

    @Before
    public void controllerTestBaseSetup() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected JsonNode get(String url) throws Exception {
        String response = mvc.perform(MockMvcRequestBuilders.get(url)).andReturn().getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(response);
    }
}