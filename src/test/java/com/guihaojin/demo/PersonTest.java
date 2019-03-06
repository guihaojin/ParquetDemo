package com.guihaojin.demo;

import org.junit.Assert;
import org.junit.Test;

public class PersonTest {

    @Test
    public void testPersonDummy() {
        Person person = new Person("John", 20);
        Assert.assertEquals("John", person.getName());
        System.out.println("Person: " + person);
    }
}
