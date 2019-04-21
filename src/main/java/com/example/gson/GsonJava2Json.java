package com.example.gson;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonJava2Json {
    public static void main(String[] args) {

        Model model = new Model();
        model.person = new Person();
        model.person.firstName = "ジョン";
        model.person.lastName = "ドゥ";
        model.person.address = "ニューヨーク";
        model.person.pets = new ArrayList<Pet>();
        Pet pet1 = new Pet();
        pet1.type = "犬";
        pet1.name = "ジョリー";
        model.person.pets.add(pet1);
        Pet pet2 = new Pet();
        pet2.type = "猫";
        pet2.name = "グリザベラ";
        model.person.pets.add(pet2);
        Pet pet3 = new Pet();
        pet3.type = "魚";
        pet3.name = "ニモ";
        model.person.pets.add(pet3);
        Gson gson = new Gson();

        String json = gson.toJson(model);
        System.out.println(json);

        // JSONをフォーマット(Pretty Printing)する
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson2.toJson(model);
        System.out.println(prettyJson);

    }
}
