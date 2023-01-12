package net.htlgkr.krejo.personalVerwaltung;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PersonManager {

    private static List<Person> personList = new ArrayList<>();
    private static Scanner systemScanner = new Scanner(System.in);
    private static Scanner fileScanner = null;

    private static PrintWriter pw = null;

    private static int instanceCounter;

    private static final String filename = "persons.txt";

    private static File file = new File(filename);

    private static final String FIRSTNAME = "FIRSTNAME=";
    private static final String LASTNAME = "LASTNAME=";
    private static final String BIRTHDAY = "BIRTHDAY=";
    private static final String GENDER = "GENDER=";
    private static final String SALARY = "SALARY=";
    private static final String STREET = "STREET=";
    private static final String HOUSENUMBER = "HOUSENUMBER=";
    private static final String ZIPCODE = "ZIPCODE=";
    private static final String CITYNAME = "CITYNAME=";
    private static final String COUNTRYNAME = "COUNTRYNAME=";
    private static final String TELEPHONENUMBER = "TELEPHONENUMBER=";
    private static final String EMAIL = "EMAIL=";
    private static final String JOBTITLE = "JOBTITLE=";
    private static final String DEPARTMENT = "DEPARTMENT=";
    private static final String WORKTIME = "WORKTIME=";
    private static final String DESCRIPTION = "DESCRIPTION=";

    private static final String REGEX = ",";


    public static void main(String[] args) {

        try {
            readPeopleFromFile();
        } catch (Exception e) {
            System.err.println("File");
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            pw = new PrintWriter(fileOutputStream, true);
            //readPeopleFromFile();
        } catch (FileNotFoundException ex) {
            System.err.println("File not found...");
        }



        int input;
        do {
            showMenu();
            input = Integer.parseInt(systemScanner.next());

            switch (input) {
                case 1 -> addPerson();
                //case 2 ->


//TODO: restliche Funktionen einbinden
                case 8 -> System.out.println("BYE!");

                case -1 -> {
                    Person p1 = new Person(0, "jonas", "Kreuzhuber", LocalDate.of(2006, 6,
                            1), "Male", 100, new Adress("Sonnleiten", 34,
                            4753, "Taiskirchen", "Austria"), 6706087712L,
                            "jonas.kreuzh@gmail.com", "JuniorDeveloper", "Software",
                            "8:00-13:20", "test", "");
                    serialize(p1);
                }

                default -> System.err.println("Ungültige Funktion!");
            }

        } while (input < 8);
    }

    private static void editPerson() {
    }

    public static void showMenu() {
        System.out.println("---------PERSONALVERWALTUNG---------");
        System.out.println("1. Add new person");

        //TODO: restliche Funktionen einbinden


        //System.out.println("7. Show every person saved in the file");
        System.out.println("8. End");
        System.out.print(">");
    }

    public static void readPeopleFromFile() throws Exception {
        fileScanner = new Scanner(file);
        String line;
        try {
            line = fileScanner.nextLine();
        } catch (Exception e) {
            System.err.println("File was found but is empty");
            throw new Exception e;
        }

        instanceCounter = Integer.parseInt(line);

        //TODO: Check the loop condition seems a bit too easy
        for (int i = 0; i < instanceCounter; i++) {
            personList.add(deserialize(i, fileScanner.nextLine()));
        }
    }

    public static void writePeopleToFile() {
        emptyFile();
        pw.println(instanceCounter);
        for (Person person : personList) {
            pw.println(serialize(person));
        }
    }

    public static void addPerson() {
        Person p1 = readInputs();
        personList.add(p1);
        instanceCounter++;
        writePeopleToFile();
    }

    private static Person readInputs() {
        while (true) {
            try {
                System.out.println("Enter the value for the given field (link mutiple entries for one field with '_'). Then press Enter");

                System.out.println("Firstname: ");
                String firstName = systemScanner.next();

                System.out.println("Lastname: ");
                String lastName = systemScanner.next();

                System.out.println("Birthday in format: yyyy-MM-dd");
                LocalDate birthday = LocalDate.parse(systemScanner.next());

                System.out.println("Gender: ");
                String gender = systemScanner.next();

                System.out.println("Salary:");
                int salary = Integer.parseInt(systemScanner.next());

                System.out.println("Street name:");
                String street = systemScanner.next();

                System.out.println("House number:");
                int houseNumber = Integer.parseInt(systemScanner.next());

                System.out.println("Zip code:");
                int zipCode = Integer.parseInt(systemScanner.next());

                System.out.println("City name:");
                String cityName = systemScanner.next();

                System.out.println("Country name:");
                String countryName = systemScanner.next();
                Adress adress = new Adress(street, houseNumber, zipCode, cityName, countryName);

                System.out.println("Telephonenumber:");
                long telephoneNumber = Long.parseLong(systemScanner.next());

                System.out.println("Email:");
                String email = systemScanner.next();

                System.out.println("Job Title:");
                String jobTitle = systemScanner.next();

                System.out.println("Department:");
                String department = systemScanner.next();

                System.out.println("Work begin and end in format: hh:mm-hh:mm");
                String workTime = systemScanner.next();

                System.out.println("Description");
                String description = systemScanner.next();
                return new Person(0, firstName, lastName, birthday, gender, salary, adress, telephoneNumber, email,
                        jobTitle, department, workTime, description, "");
            } catch (Exception e) {
                System.err.println("Check your entries!");
            }
        }

    }

    public static String serialize(Person person) {
        String serializedPerson = (person.id() + ": " +
                FIRSTNAME + person.firstName() + REGEX +
                LASTNAME + person.lastName() + REGEX +
                BIRTHDAY + person.birthday().toString() + REGEX +
                SALARY + person.salary() + REGEX +
                STREET + person.adress().houseNumber() + REGEX +
                HOUSENUMBER + person.adress().houseNumber() + REGEX +
                ZIPCODE + person.adress().zipCode() + REGEX +
                CITYNAME + person.adress().cityName() + REGEX +
                COUNTRYNAME + person.adress().countryName() + REGEX +
                TELEPHONENUMBER + person.telephoneNumber() + REGEX +
                EMAIL + person.email() + REGEX +
                JOBTITLE + person.jobTitle() + REGEX +
                DEPARTMENT + person.department() + REGEX +
                WORKTIME + person.workTime() + REGEX +
                DESCRIPTION + person.description()
        );
        System.out.println(serializedPerson);
        return serializedPerson;
    }


    public static Person deserialize(int id, String line) {

        String firstName = null;
        String lastName = null;
        LocalDate birthday = null;
        String gender = null;
        int salary = 0;
        String street = null;
        int houseNumber = 0;
        int zipCode = 0;
        String cityName = null;
        String countryName;
        countryName = null;
        Adress adress;
        long telephoneNumber = 0;
        String email = null;
        String jobTitle = null;
        String department = null;
        String workTime = null;
        String description = null;

        String[] pairs = line.split(REGEX);

        for (int i = 0; i <= 14; i += 2) {

            String[] fieldNameWithValue = pairs[i].split("=");
            try {


                switch (fieldNameWithValue[0]) {
                    case FIRSTNAME -> firstName = fieldNameWithValue[1];
                    case LASTNAME -> lastName = fieldNameWithValue[1];
                    case BIRTHDAY -> birthday = LocalDate.parse(fieldNameWithValue[1]);
                    case GENDER -> gender = fieldNameWithValue[1];
                    case SALARY -> salary = Integer.parseInt(fieldNameWithValue[1]);
                    case STREET -> street = fieldNameWithValue[1];
                    case HOUSENUMBER -> houseNumber = Integer.parseInt(fieldNameWithValue[1]);
                    case ZIPCODE -> zipCode = Integer.parseInt(fieldNameWithValue[1]);
                    case CITYNAME -> cityName = fieldNameWithValue[1];
                    case COUNTRYNAME -> countryName = fieldNameWithValue[1];
                    case TELEPHONENUMBER -> telephoneNumber = Long.parseLong(fieldNameWithValue[1]);
                    case EMAIL -> email = fieldNameWithValue[1];
                    case JOBTITLE -> jobTitle = fieldNameWithValue[1];
                    case DEPARTMENT -> department = fieldNameWithValue[1];
                    case WORKTIME -> workTime = fieldNameWithValue[1];
                    case DESCRIPTION -> description = fieldNameWithValue[1];
                    default -> System.out.println("SOMETHING WASN'T CORRECT IN THE FILE");

                }
            } catch (Exception e) {
                System.err.println("Person " + id + " has an incorrect entry at " + fieldNameWithValue[0]);
            }
        }
        adress = new Adress(street, houseNumber, zipCode, cityName, countryName);
        assert birthday != null;
        return new Person(id, firstName, lastName, birthday, gender, salary, adress, telephoneNumber, email, jobTitle,
                department, workTime, description, "");
    }


    public static void emptyFile() {
        try (PrintWriter printWriter = new PrintWriter(filename);) {
        } catch (FileNotFoundException e) {
            System.out.println("File wasn't found...");
        }
    }

}
//default user if needed: