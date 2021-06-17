package eu.epptec.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class Lambda {
    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("Vojtech", "Kulovany", 24),
                new Person("Zbysek", "Pantucek", 53),
                new Person("Tomas", "Holy", 22),
                new Person("Jan", "Novak", 38),
                new Person("Jaromir", "Jagr", 48),
                new Person("Paul", "Simon", 79),
                new Person("Art", "Garfunkel", 79),
                new Person("Tomas", "Masaryk", 87),
                new Person("Zdenek", "Kulovany", 63)
        );

        // --------------------------------------------
        // 1) Count the number of people named "Tomas"
        // --------------------------------------------
        long tomasCount = people
                .stream()
                .filter(person -> person.getFirstName().equals("Tomas"))
                .count();

        System.out.println(tomasCount);
        System.out.println();

        // --------------------------------------------
        // 2) Get an array of unique last names
        // --------------------------------------------
        List<String> uniqueLastNames = people
                .stream()
                .map(Person::getLastName)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(uniqueLastNames);
        System.out.println();

        // --------------------------------------------
        // 3) Count the sum of people's age
        // --------------------------------------------
        int ageSum = people
                .stream()
                .map(Person::getAge)
                .reduce(0, (acc, age) -> acc + age);

        System.out.println(ageSum);
        System.out.println();

        // --------------------------------------------
        // 4) Group people to map by name
        // --------------------------------------------
        Map<String, List<Person>> firstNameMap = people
                .stream()
                .collect(Collectors.groupingBy(Person::getFirstName));

        firstNameMap.forEach((name, personList) -> System.out.println(name + " -> " + personList.toString()));
        System.out.println();

        // --------------------------------------------
        // 5) Group people to Map<String, Map<String, List<Person>>>
        // by first name first and last name second
        // --------------------------------------------

        Map<String, Map<String, List<Person>>> fullNameMap = people
                .stream()
                .collect(Collectors.groupingBy(Person::getFirstName, Collectors.groupingBy(Person::getLastName)));

        fullNameMap.forEach(
                (firstName, lastNameMap) -> {
                    System.out.println(firstName);
                    lastNameMap.forEach((lastName, personList)
                            -> System.out.println(" -> " + lastName + " -> " + personList.toString()));
                });
        System.out.println();

        // 6) Sort the people by last name first, first name second
        Comparator<Person> nameComparator = Comparator.comparing(Person::getLastName).thenComparing(Person::getFirstName);
        List<Person> sortedPeople = people
                .stream()
                .sorted(nameComparator)
                .collect(Collectors.toList());

        System.out.println(sortedPeople);

        
    }
}
