package com.example.gson;

import com.google.gson.Gson;

public class GsonJson2Java {
    public static void main(String[] args) {
        String json = 
                "{" +
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

        Gson gson = new Gson();
        Model model = gson.fromJson(json, Model.class);

        System.out.println("firstName:" + model.person.firstName);
        System.out.println("lastName:" + model.person.lastName);
        System.out.println("address:" + model.person.address);
        System.out.println("1st pet:" + model.person.pets.get(0).name);
    }
}
