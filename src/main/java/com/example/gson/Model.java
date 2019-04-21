
package com.example.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("person")
    @Expose
    public Person person;

}
