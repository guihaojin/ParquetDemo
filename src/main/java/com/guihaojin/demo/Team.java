package com.guihaojin.demo;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String id;
    private List<Person> members = new ArrayList<>();

    public Team() {
    }

    public Team(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addMember(Person person) {
        members.add(person);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Team, id: " + id);
        for (Person person : members) {
            stringBuilder.append("\n");
            stringBuilder.append(person);
        }
        return stringBuilder.toString();
    }

}
