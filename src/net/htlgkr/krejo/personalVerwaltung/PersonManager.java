package net.htlgkr.krejo.personalVerwaltung;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PersonManager {
    List<Person> personList = new ArrayList<>();
    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        int input;
        do {
            showMenu();
            input = Integer.parseInt(s.nextLine());

            switch (input) {
                case 1:
                    addPerson();
                    break;
                case 7:
                    return;
                default:
                    System.err.println("Ungültige Funktion!");
            }

        } while (input < 8);
    }

    public static void showMenu() {
        System.out.println("---------PERSONALVERWALTUNG---------");
        System.out.println("1. Neue Person hinzufügen");
        //restliche Funktionen einbinden

        System.out.println("7. Beenden");
    }

    public static void addPerson() {
        boolean validInput = false;
        String firstName;
        String lastName;
        LocalDate birthday;
        Gender gender;
        int salary;
        Adress adress;
        long telephoneNumber;
        String email;
        String jobTitle;
        String department;
        String workTime;
        String description;

        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0:
            }

        }
        Person p1 = new Person("jonas", "Kreuzhuber", LocalDate.of(2006, 6, 1), Gender.MALE, 100, new Adress("Sonnleiten", 34, 4753, "Taiskirchen", "Austria"), 6706087712L, "jonas.kreuzh@gmail.com", "JuniorDeveloper", "Software", "8:00-13:20", "test", Optional.empty());
    }

}
