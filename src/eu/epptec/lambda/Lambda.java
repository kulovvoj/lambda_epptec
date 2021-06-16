package eu.epptec.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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
        long tomasCount = stream(people).filter(person -> person.getFirstName().equals("Tomas")).count();
        System.out.println(tomasCount);
        System.out.println();

        // Get an array of unique last names
        String[] uniqueLastNames = stream(people).map(person -> person.getLastName()).distinct().toArray(String[]::new);
        System.out.println(Arrays.toString(uniqueLastNames));System.out.println();

        // Count the sum of people's age
        int ageSum = stream(people).map(person -> person.getAge()).reduce(0, (acc, age) -> acc + age);
        System.out.println(ageSum);
        System.out.println();

        // Group people to map by name
        Map<String, List<Person>> firstNameMap
                = stream(people).collect(
                        Collectors.toMap(Person::getFirstName,
                            person -> Stream.of(person).collect(Collectors.toList()),
                            (existing, replacement)
                                    -> Stream.concat(existing.stream(), replacement.stream()).collect(Collectors.toList())));

        // Printing using nested forEach (wanted to try it)
        // firstNameMap.forEach((name, personList)
        //     -> personList.forEach(person -> System.out.println("[" + name + " -> " + person.toString() + "]")));

        firstNameMap.entrySet().forEach(entry -> System.out.println(entry.getKey() + " -> " + entry.getValue().toString()));
        System.out.println();

        // Group people to Map<String, Map<String, List<Person>>> by first name first and last name second
        System.out.println();

    }
}
