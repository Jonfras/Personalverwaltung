package net.htlgkr.krejo.personalVerwaltung;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class PersonManager {

    private static List<Person> personList = new ArrayList<>();
    private static Scanner systemScanner = new Scanner(System.in);
    private static Scanner fileScanner = null;


    private static int instanceCounter;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";

    private static final String filename = "persons.txt";

    private static File file = new File(filename);

    private static final String FIRSTNAME = "FIRSTNAME";
    private static final String LASTNAME = "LASTNAME";
    private static final String BIRTHDAY = "BIRTHDAY";
    private static final String GENDER = "GENDER";
    private static final String SALARY = "SALARY";
    private static final String STREET = "STREET";
    private static final String HOUSENUMBER = "HOUSENUMBER";
    private static final String ZIPCODE = "ZIPCODE";
    private static final String CITYNAME = "CITYNAME";
    private static final String COUNTRYNAME = "COUNTRYNAME";
    private static final String TELEPHONENUMBER = "TELEPHONENUMBER";
    private static final String EMAIL = "EMAIL";
    private static final String JOBTITLE = "JOBTITLE";
    private static final String DEPARTMENT = "DEPARTMENT";
    private static final String WORKTIME = "WORKTIME";
    private static final String DESCRIPTION = "DESCRIPTION";

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String EQUALS = "=";

    private static final String REGEX = ",";


    public static void main(String[] args) {

        try {
            readPeopleFromFile();
        } catch (FileNotFoundException e) {
            System.err.println("File not found...");
            return;
        } catch (FileEmptyException e) {
            System.out.println("File is empty");
            instanceCounter = 0;
        }

        int input;
        do {
            showMenu();
            input = Integer.parseInt(systemScanner.next());

            switch (input) {
                case 1 -> {
                    try {
                        addPerson();
                    } catch (Exception e) {
                        System.err.println("Enter correct inputs");
                    }
                }
                case 2 -> {
                    try {
                        editPerson();
                    } catch (NumberFormatException e) {
                        System.err.println("Enter a valid number");
                    }
                }
                case 3 -> {
                    try {
                        deletePerson();
                    } catch (RuntimeException e) {
                        System.err.println("Enter a valid number");
                    }
                }
                case 4 -> {
                    try {
                        searchPerson();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case 5 -> {
                    try {
                        informationAboutPerson();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case 6 -> {
                    try {
                        analysePeople();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }


                //TODO: restliche Funktionen einbinden
                case 7 -> {
                    System.out.println("BYE!");
                    return;
                }
                case -1 -> showPeople();
                default -> System.err.println("Ungültige Funktion!");
            }

        } while (input != 8);
    }


    private static void showPeople() {
        personList.forEach(System.out::println);
//        personList = new ArrayList<>();
//        try {
//            readPeopleFromFile();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (FileEmptyException e) {
//            System.out.println("File is empty");
//        }
    }

    public static void showMenu() {
        System.out.println("---------PERSONALVERWALTUNG---------");
        System.out.println("1. Add new person");
        if (personList.size() < 1) {
            System.out.println(ANSI_BLACK + "2. Edit person");
            System.out.println("3. Delete person");
            System.out.println("4. Search person");
            System.out.println("5. Information about person");
            System.out.println("6. Analyse people" + ANSI_RESET);
        } else {
            System.out.println("2. Edit person");
            System.out.println("3. Delete person");
            System.out.println("4. Search person");
            System.out.println("5. Information about person");
            System.out.println("6. Analyse people");
        }
        //TODO: restliche Funktionen einbinden


        System.out.println("7. End");
        System.out.print(">");
    }

    public static void addPerson() throws Exception {
        Person p1 = readInputs();
        personList.add(p1);
        syncInstanceCounter();
        writePeopleToFile();
    }

    private static void editPerson() throws NumberFormatException {
        int input;
        String newValue = "";
        Adress newAdress = null;
        System.out.println("Enter the Number of the Person you want to edit (1...n):");

        int personNR = Integer.parseInt(systemScanner.next());

        if (personNR <= personList.size() && personNR >= 0) {

            do {
                System.out.println("What do you want to edit?");
                showEditableFields();
                input = Integer.parseInt(systemScanner.next());
            } while (input < 0 || 12 < input);

            if (input == 6) {

                newAdress = getNewAdress();

            } else {

                System.out.println("Enter the new value (Remember Date formatted like this " + DATE_FORMAT + "):");
                newValue = systemScanner.next();
            }


            Person originP = personList.get(personNR - 1);

            Person editedPerson = switch (input) {

                case 1 -> new Person(originP.id(), newValue, originP.lastName(), originP.birthday(),
                        originP.gender(), originP.salary(), originP.adress(), originP.telephoneNumber(), originP.email(),
                        originP.jobTitle(), originP.department(), originP.workTime(), originP.description(), "");


                case 2 -> new Person(originP.id(), originP.firstName(), newValue, originP.birthday(),
                        originP.gender(), originP.salary(), originP.adress(), originP.telephoneNumber(), originP.email(),
                        originP.jobTitle(), originP.department(), originP.workTime(), originP.description(), "");


                case 3 -> new Person(originP.id(), originP.firstName(), originP.lastName(), LocalDate.parse(newValue),
                        originP.gender(), originP.salary(), originP.adress(), originP.telephoneNumber(), originP.email(),
                        originP.jobTitle(), originP.department(), originP.workTime(), originP.description(), "");


                case 4 -> new Person(originP.id(), originP.firstName(), originP.lastName(), originP.birthday(),
                        newValue, originP.salary(), originP.adress(), originP.telephoneNumber(), originP.email(),
                        originP.jobTitle(), originP.department(), originP.workTime(), originP.description(), "");


                case 5 -> new Person(originP.id(), originP.firstName(), originP.lastName(), originP.birthday(),
                        originP.gender(), Integer.parseInt(newValue), originP.adress(), originP.telephoneNumber(), originP.email(),
                        originP.jobTitle(), originP.department(), originP.workTime(), originP.description(), "");


                case 6 -> new Person(originP.id(), originP.firstName(), originP.lastName(), originP.birthday(),
                        originP.gender(), originP.salary(), newAdress, originP.telephoneNumber(), originP.email(),
                        originP.jobTitle(), originP.department(), originP.workTime(), originP.description(), "");


                case 7 -> new Person(originP.id(), originP.firstName(), originP.lastName(), originP.birthday(),
                        originP.gender(), originP.salary(), originP.adress(), Long.parseLong(newValue), originP.email(),
                        originP.jobTitle(), originP.department(), originP.workTime(), originP.description(), "");


                case 8 -> new Person(originP.id(), originP.firstName(), originP.lastName(), originP.birthday(),
                        originP.gender(), originP.salary(), originP.adress(), originP.telephoneNumber(), newValue,
                        originP.jobTitle(), originP.department(), originP.workTime(), originP.description(), "");


                case 9 -> new Person(originP.id(), originP.firstName(), originP.lastName(), originP.birthday(),
                        originP.gender(), originP.salary(), originP.adress(), originP.telephoneNumber(), originP.email(),
                        newValue, originP.department(), originP.workTime(), originP.description(), "");


                case 10 -> new Person(originP.id(), originP.firstName(), originP.lastName(), originP.birthday(),
                        originP.gender(), originP.salary(), originP.adress(), originP.telephoneNumber(), originP.email(),
                        originP.jobTitle(), newValue, originP.workTime(), originP.description(), "");


                case 11 -> new Person(originP.id(), originP.firstName(), originP.lastName(), originP.birthday(),
                        originP.gender(), originP.salary(), originP.adress(), originP.telephoneNumber(), originP.email(),
                        originP.jobTitle(), originP.department(), newValue, originP.description(), "");


                case 12 -> new Person(originP.id(), originP.firstName(), originP.lastName(), originP.birthday(),
                        originP.gender(), originP.salary(), originP.adress(), originP.telephoneNumber(), originP.email(),
                        originP.jobTitle(), originP.department(), originP.workTime(), newValue, "");

                default -> throw new IllegalStateException("Unexpected value: " + newValue);

            };

            personList.set(personNR - 1, editedPerson);

        } else {
            System.out.println("Enter a valid number");
        }
        writePeopleToFile();
    }

    private static void deletePerson() throws RuntimeException {

        showPeople();


        int personNR;
        do {
            System.out.println("Which person do you want to delete (1...n): \n Enter -1 to escape ");

            try {
                personNR = Integer.parseInt(systemScanner.next());
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }

            if (personNR == -1) {
                return;
            }

        } while (personNR >= personList.size() || personNR <= 0);

        personList.remove(personNR - 1);
        syncInstanceCounter();
        writePeopleToFile();
    }

    private static void searchPerson() {
        System.out.println("Enter any Information you know about the person:");
        String info = systemScanner.next();

        List<Person> foundPeople = new ArrayList<>();

        for (Person person : personList) {
            if (person.allFieldsForSearch().contains(info)) {
                foundPeople.add(person);
            }
        }

        foundPeople.forEach(System.out::println);
    }

    private static void informationAboutPerson() {
        int personNR;

        do {
            System.out.println("Enter the Number of the Person you want to get information about (1...n):");
            personNR = Integer.parseInt(systemScanner.next());
        } while (personNR > personList.size() || personNR <= 0);

        System.out.println(personList.get(personNR-1));
    }

    private static void analysePeople() {
        Map<String, Integer> genderMap = new HashMap<>();

        long totalAge = 0;

        int salary = 0;
        int highestSalary = 0;
        int lowestSalary = 0;
        double averageSalary = 0;

        for (Person person : personList) {
            if (!genderMap.containsKey(person.gender().trim())) {
                genderMap.put(person.gender(), 1);
            } else {
                genderMap.put(person.gender(), genderMap.get(person.gender()) + 1);
            }

            Period period = Period.between(person.birthday(), LocalDate.now());
            totalAge += period.toTotalMonths();

            salary = person.salary();
            averageSalary += salary;
            if (highestSalary < salary) {
                highestSalary = salary;
            } else if (salary < lowestSalary) {
                lowestSalary = salary;
            }

        }


        for (String gender : genderMap.keySet()) {
            System.out.println(gender + ": " + genderMap.get(gender) + " people.");
        }

        double averageAge = (double) (totalAge/personList.size()) / 12;

        averageSalary = averageSalary/personList.size();

        System.out.println("Average age: " + averageAge + " years.");
        System.out.println("Average salary: " + averageSalary + " €");


    }


    private static Adress getNewAdress() {
        while (true) {
            try {
                System.out.println("Enter street");
                String street = systemScanner.next();

                System.out.println("Enter housenumber");
                int houseNumber = Integer.parseInt(systemScanner.next());

                System.out.println("Enter zip code");
                int zipCode = Integer.parseInt(systemScanner.next());

                System.out.println("Enter city name");
                String cityName = systemScanner.next();

                System.out.println("Enter country name");
                String countryName = systemScanner.next();

                return new Adress(street, houseNumber, zipCode, cityName, countryName);

            } catch (NumberFormatException e) {
                System.out.println("Enter valid inputs");
            }
        }

    }

    private static void showEditableFields() {
        System.out.println("1. Firstname");
        System.out.println("2. Lastname");
        System.out.println("3. Birthday");
        System.out.println("4. Gender");
        System.out.println("5. Salary");
        System.out.println("6. Address");
        System.out.println("7. Telephone number");
        System.out.println("8. Email");
        System.out.println("9. Job title");
        System.out.println("10. Department");
        System.out.println("11. Work time");
        System.out.println("12. Description");
    }


    public static void readPeopleFromFile() throws FileNotFoundException, FileEmptyException {
        fileScanner = new Scanner(file);
        String line;
        try {
            line = fileScanner.nextLine();
            instanceCounter = Integer.parseInt(line);
        } catch (Exception e) {
            throw new FileEmptyException();
        }

        for (int i = 0; i < instanceCounter; i++) {
            personList.add(deserialize(i, fileScanner.nextLine()));
        }
    }

    public static void writePeopleToFile() {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            System.out.println("PrintWriter failed");
        }
        printWriter.println(instanceCounter);
        printWriter.flush();

        for (int i = 0; i < personList.size(); i++) {
            printWriter.println(serialize(personList.get(i), i + 1));
            printWriter.flush();
        }
    }


    private static Person readInputs() throws Exception {

        System.out.println("Enter the value for the given field (link mutiple entries for one field with '_'). Then press Enter");

        System.out.println("Firstname: ");
        String firstName = systemScanner.next();

        System.out.println("Lastname: ");
        String lastName = systemScanner.next();

        System.out.println("Birthday in format: " + DATE_FORMAT);
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
    }

    public static String serialize(Person person, int id) {
        String serializedPerson = (id + ": " +
                FIRSTNAME + EQUALS + person.firstName() + REGEX +
                LASTNAME + EQUALS + person.lastName() + REGEX +
                BIRTHDAY + EQUALS + person.birthday().toString() + REGEX +
                GENDER + EQUALS + person.gender() + REGEX +
                SALARY + EQUALS + person.salary() + REGEX +
                STREET + EQUALS + person.adress().houseNumber() + REGEX +
                HOUSENUMBER + EQUALS + person.adress().houseNumber() + REGEX +
                ZIPCODE + EQUALS + person.adress().zipCode() + REGEX +
                CITYNAME + EQUALS + person.adress().cityName() + REGEX +
                COUNTRYNAME + EQUALS + person.adress().countryName() + REGEX +
                TELEPHONENUMBER + EQUALS + person.telephoneNumber() + REGEX +
                EMAIL + EQUALS + person.email() + REGEX +
                JOBTITLE + EQUALS + person.jobTitle() + REGEX +
                DEPARTMENT + EQUALS + person.department() + REGEX +
                WORKTIME + EQUALS + person.workTime() + REGEX +
                DESCRIPTION + EQUALS + person.description()
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

        String removedID = line.substring(line.indexOf(":") + 1).trim();

        String[] pairs = removedID.split(REGEX);

        for (int i = 0; i <= 14; i++) {

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

    private static void syncInstanceCounter() {
        instanceCounter = personList.size();
    }
}


//    public static void emptyFile() {
//        try (PrintWriter printWriter = new PrintWriter(filename)) {
//        } catch (FileNotFoundException e) {
//            System.out.println("File wasn't found...");
//        }
//    }
