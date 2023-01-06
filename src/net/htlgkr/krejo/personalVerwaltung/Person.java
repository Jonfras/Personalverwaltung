package net.htlgkr.krejo.personalVerwaltung;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Optional;

public record Person(String firstName, String lastName, LocalDate birthday, Gender gender, int salary, Adress adress,
                     long telephoneNumber, String email, String jobTitle, String department, String workTime,
                     String description, Optional<String> allFieldsForSearch) {

    private static int instanceCounter = 1;
    private static final String filename = "persons.txt";
    static PrintWriter pw = null;

    static {
        try {
            pw = new PrintWriter(filename);
        } catch (FileNotFoundException ex) {
            System.err.println("File not found...");
        }
    }

    public Person(String firstName, String lastName, LocalDate birthday, Gender gender, int salary, Adress adress, long telephoneNumber, String email, String jobTitle, String department, String workTime, String description, Optional<String> allFieldsForSearch) {
        this.allFieldsForSearch = Optional.of(firstName + lastName + birthday.toString() + gender + salary + adress.toString() + email + telephoneNumber + jobTitle + department + workTime + description);
        synchronized (Person.class) {
            instanceCounter++;
        }
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
        writePersonToFile(this);
    }

    public static void writePersonToFile(Person person) {
        pw.write(instanceCounter + ": " + person.allFieldsForSearch);
        pw.flush();
    }

    public static void deletePeronInFile(int id) {

    }
}
