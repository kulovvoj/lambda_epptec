package eu.epptec.lambda;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lambda {
    private static class UniqueLastNameCollector implements Collector<Person, Set<String>, List<String>> {

        @Override
        public Supplier<Set<String>> supplier() {
            return HashSet::new;
        }

        @Override
        public BiConsumer<Set<String>, Person> accumulator() {
            return (lastNameList, person) -> lastNameList.add(person.getLastName());
        }

        @Override
        public BinaryOperator<Set<String>> combiner() {
            return (left, right) -> Stream.concat(left.stream(), right.stream()).collect(Collectors.toSet());
        }

        @Override
        public Function<Set<String>, List<String>> finisher() {
            return accList -> accList.stream().toList();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Set.of(Characteristics.UNORDERED);
        }
    }

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
        // 2) Get a list of unique last names
        // --------------------------------------------

        // First solution
        List<String> uniqueLastNames = people
                .stream()
                .map(Person::getLastName)
                .distinct()
                .collect(Collectors.toList());

        // Solution using custom collector
        uniqueLastNames = people
                .stream()
                .collect(new UniqueLastNameCollector());

        System.out.println(uniqueLastNames);
        System.out.println();

        // --------------------------------------------
        // 3) Count the sum of people's age
        // --------------------------------------------

        // First solution

        int ageSum = people
                .stream()
                .map(Person::getAge)
                .reduce(0, (acc, age) -> acc + age);

        // Second solution

        ageSum = people
                .stream()
                .collect(Collectors.summingInt(Person::getAge));

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
