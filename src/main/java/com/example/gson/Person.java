
package com.example.gson;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {

    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("pets")
    @Expose
    public List<Pet> pets = null;

}
