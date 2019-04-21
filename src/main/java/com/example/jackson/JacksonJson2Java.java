package com.example.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonJson2Java {
    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        String json = "{" +
                "    \"person\": {" +
                "        \"firstName\": \"John\"," +
                "        \"lastName\": \"Doe\"," +
                "        \"address\": \"NewYork\"," +
                "        \"pets\": [" +
                "            {\"type\": \"Dog\", \"name\": \"Jolly\"}," +
                "            {\"type\": \"Cat\", \"name\": \"Grizabella\"}," +
                "            {\"type\": \"Fish\", \"name\": \"Nimo\"}" +
                "        ]" +
                "    }" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        Model model = mapper.readValue(json, Model.class);

        System.out.println("firstName:" + model.person.firstName);
        System.out.println("lastName:" + model.person.lastName);
        System.out.println("address:" + model.person.address);
        System.out.println("1st pet:" + model.person.pets.get(0).name);
    }
}
