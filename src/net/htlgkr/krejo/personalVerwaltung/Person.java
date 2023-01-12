package net.htlgkr.krejo.personalVerwaltung;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public record Person(int id, String firstName, String lastName, LocalDate birthday, String gender, int salary, Adress adress,
                     long telephoneNumber, String email, String jobTitle, String department, String workTime,
                     String description, String allFieldsForSearch) {

    private static int instanceCounter = 0;


    public Person(int id, String firstName, String lastName, LocalDate birthday, String gender, int salary,
                  Adress adress, long telephoneNumber, String email, String jobTitle, String department, String workTime,
                  String description, String allFieldsForSearch) {
        this.allFieldsForSearch = firstName + lastName + birthday.toString() + gender + salary + adress.toString() +
                email + telephoneNumber + jobTitle + department + workTime + description;
        synchronized (Person.class) {
            instanceCounter++;
        }
        this.id = instanceCounter;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
        this.salary = salary;
        this.adress = adress;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.jobTitle = jobTitle;
        this.department = department;
        this.workTime = workTime;
        this.description = description;

    }

    public static void syncInstanceCounter(int iC) {
        instanceCounter = iC;
    }

}
