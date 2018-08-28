package com.senacor.bankathon2018.connectors.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.senacor.bankathon2018.connectors.model.axway.user.AxwayUserQuery;
import org.junit.Test;


public class AxwayUserQueryTest {

    @Test
    public void serializeAsJson() throws JsonProcessingException {
        AxwayUserQuery axwayUserQuery = new AxwayUserQuery("user");
        ObjectWriter objectMapper = new ObjectMapper().writer();
        String json = objectMapper.writeValueAsString(axwayUserQuery);
        System.out.println(json);
    }

}