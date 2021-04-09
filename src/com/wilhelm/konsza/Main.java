package com.wilhelm.konsza;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        new Thread(new CodeToRun()).start();

        //Anonymous class
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Printing from Anonymous class Runnable");
            }
        }).start();

        //Lambda -> lambdas help us get rid of the boilerplate code, have argument list, arrow token and a body
        new Thread(() -> System.out.println("Printing from Anonymous class Runnable with Lambda expression")).start();
        new Thread(() -> {
            System.out.print("Multiple statements need ");
            System.out.print("{} curly brackets\n");
        }).start();

        List<Employee> employees = new ArrayList<>();

        Employee john = new Employee("John Doe", 30);
        Employee tim = new Employee("Tim Buchalka", 21);
        Employee vili = new Employee("Konsza Wilhelm", 25);
        Employee snow = new Employee("John Snow", 100);

        employees.add(john);
        employees.add(tim);
        employees.add(vili);
        employees.add(snow);

        Collections.sort(employees, new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        //Comparator is a Functional interface as equals method is inherited from object thus has default impl
        Collections.sort(employees, (o1, o2) -> o1.getName().compareTo(o2.getName()));

        //method reference operator
        Collections.sort(employees, Comparator.comparingInt(Employee::getAge));
        Collections.sort(employees, Comparator.comparing(Employee::getName));

        for (Employee emp : employees)
            System.out.println(emp.getName());

        System.out.println("*********************************************");
        employees.forEach(employee -> System.out.println(employee.getName() + " " + employee.getAge()));

        String sillyString = doStringStuff((s1, s2) ->
                        s1.toUpperCase().concat(s2.toUpperCase())
                , employees.get(0).getName(), employees.get(1).getName());

        System.out.println(sillyString);

        AnotherClass anotherClass = new AnotherClass();
        System.out.println(anotherClass.doSomething());

    }

    public final static String doStringStuff(UpperConcat uc, String s1, String s2) {

        return uc.upperConcat(s1, s2);
    }
}

class CodeToRun implements Runnable {

    @Override
    public void run() {
        System.out.println("Printing from runnable");
    }
}

class Employee {
    private String name;
    private int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}

interface UpperConcat {
    //uppercases two Strings and then concatenates them
    public String upperConcat(String s1, String s2);
}

class AnotherClass {

    //meaningful names I know
    public String doSomething() {
        // variables defined in the enclosing block of the nested block can be accessed within the nested block
        // anonymous classes can only access final variables as local variables don't belong to the anonymous class instance
        // any local variable we want to use from the enclosing block in the lambda expression has to be effectively final

        int count = 0;
        {
            System.out.println(count);
            System.out.println("Original class: " + getClass().getSimpleName());
            return Main.doStringStuff((s1, s2) -> {
                //When using anonymous class we can't get the simple name, however with lambdas we do get it -> lambdas are treated like nested blocks of code
                System.out.println("Lambda class name: " + getClass().getSimpleName());

                System.out.println(count);

                return s1.toUpperCase().concat(s2.toUpperCase());
            }, "String1", "String2");
        }
    }
}
