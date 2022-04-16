package com.anyarusova.client.utility;

import com.anyarusova.client.data.Address;
import com.anyarusova.client.data.Coordinates;
import com.anyarusova.client.data.Organization;
import com.anyarusova.client.data.OrganizationType;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Asks and receives user input data to make a StudyGroup object.
 */
public class OrganizationMaker {
    private static final String ERROR_MESSAGE = "Your enter was not correct type. Try again";
    private final OutputManager outputManager;
    private final UserInputManager userInputManager;
    private final CollectionManager collectionManager;

    public OrganizationMaker(UserInputManager userInputManager, OutputManager outputManager, CollectionManager collectionManager) {
        this.userInputManager = userInputManager;
        this.outputManager = outputManager;
        this.collectionManager = collectionManager;
    }

    public <T> T ask(Predicate<T> predicate,
                     String askMessage,
                     String errorMessage,
                     String wrongValueMessage,
                     Function<String, T> converter,
                     boolean nullable) {
        outputManager.println(askMessage);
        String input;
        T value;
        do {
            try {
                input = userInputManager.nextLine();
                if ("".equals(input) && nullable) {
                    return null;
                }

                value = converter.apply(input);

            } catch (IllegalArgumentException e) {
                outputManager.println(errorMessage);
                continue;
            }
            if (predicate.test(value)) {
                return value;
            } else {
                outputManager.println(wrongValueMessage);
            }
        } while (true);
    }

    public Organization makeOrganization() {
        return askForOrganization();
    }

    private Organization askForOrganization() {
        outputManager.println("Enter organization data");
        String name = this.ask(arg -> (arg).length() > 0, "Enter name (String)",
                ERROR_MESSAGE, "The string must not be empty", x -> x, false);

        Coordinates coordinates = askForCoordinates(); //not null

        long annualTurnover = this.ask(arg -> (arg) > 0, "Enter annualTurnover (long)",
                ERROR_MESSAGE, "Your long must be >0. Try again", Long::parseLong, true); // >0

        Long employeesCount = this.ask(arg -> (arg) > 0, "Enter employeesCount (Long) (not null)",
                ERROR_MESSAGE, "Your Long must be >0. Try again", Long::parseLong, false); // >0, not null

        OrganizationType type = this.ask(arg -> true, "Enter type (GOVERNMENT, TRUST, PRIVATE_LIMITED_COMPANY, OPEN_JOINT_STOCK_COMPANY)",
                ERROR_MESSAGE, ERROR_MESSAGE, OrganizationType::valueOf, false); //not null

        Address postalAddress = askForPostalAddress(); //not null

        return new Organization(name, coordinates, annualTurnover,
                employeesCount, type, postalAddress, collectionManager);
    }

    private Coordinates askForCoordinates() {
        outputManager.println("Enter coordinates data");
        final Integer xLimitation = 858;
        Integer x = this.ask(arg -> (arg) < xLimitation, "Enter x (Integer)",
                ERROR_MESSAGE, "The Integer must be <858. Try again", Integer::parseInt, false); //< 858
        int y = this.ask(arg -> true, "Enter y (int)",
                ERROR_MESSAGE, ERROR_MESSAGE, Integer::parseInt, false);
        return new Coordinates(x, y);
    }

    private Address askForPostalAddress() {
        outputManager.println("Enter postal address data");
        final long streetLengthLimit = 121;
        String street = this.ask(arg -> (arg).length() < streetLengthLimit, "Enter street (String)",
                ERROR_MESSAGE, "The string length must be < 121. Try again", x -> x, false); //length < 121, not null
        String zipCode = this.ask(arg -> true, "Enter zipCode (String)",
                ERROR_MESSAGE, "The string must not be empty. Try again", x -> x, false); //not null
        return new Address(street, zipCode);
    }

}
