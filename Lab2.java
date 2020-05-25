
package com.company;

import com.google.gson.Gson;

import java.util.Objects;

public class Lab2 {

    public static void main(String[] args) {
        Person person = new Person(2,"Artem","Hradiuk");
        System.out.println("Before serialization: "+person.toString());
        Gson gson= new Gson();
        String json = gson.toJson(person);
        Person read = gson.fromJson(json, Person.class);
        System.out.println("After serialization: "+read.toString());
        System.out.println("The same?"+ person.equals(read));
    }
}
class Person{
    private int id;
    private String firstName;
    private String lastName;

    public Person(int id, String firstName, String lastName) {
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
    }


    @Override
    public boolean equals(Object obj) {
        if( obj==this){
            return true;
        }
        if(obj==null||obj.getClass()!=this.getClass()){
            return false;
        }
        Person g=(Person)obj;
        return id==g.id &&(Objects.equals(firstName, g.firstName))
                &&(Objects.equals(lastName, g.lastName));
    }
    @Override
    public String toString(){
        return "Person{"+"id="+id+", firstName="+firstName+", lastName="+lastName+'}';
    }
}