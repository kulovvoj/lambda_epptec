package eu.epptec.lambda;

import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public class Lambda {
    public static void main(String[] args) {
        Person[] people = {
                new Person("Vojtech", "Kulovany", 24),
                new Person("Zbysek", "Pantucek", 53),
                new Person("Tomas", "Holy", 22),
                new Person("Jan", "Novak", 38),
                new Person("Jaromir", "Jagr", 48),
                new Person("Paul", "Simon", 79),
                new Person("Art", "Garfunkel", 79),
                new Person("Tomas", "Masaryk", 87),
                new Person("Zdenek", "Kulovany", 63)
        };
        // Count the number of people named "Tomas"
        System.out.println(stream(people).filter(person -> person.firstName.equals("Tomas")).count());
        // Get an array of unique last names
        System.out.println(Arrays.toString(stream(people).map(person -> person.lastName).distinct().toArray(String[]::new)));
        // Count the sum of people's age
        System.out.println();
        // Group people to map by name
        System.out.println();
        // Group people to Map<String, Map<String, List,<Person>>> by first name first and last name second
        System.out.println();

    }
}
