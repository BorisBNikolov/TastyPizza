import model.Account;
import model.AccountType;
import model.ProductType;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PizzaManager pizzaManager = new PizzaManager();
        //adding data

        pizzaManager.register("admin", "admin", null, "admin", AccountType.ADMIN);
        pizzaManager.register("clerk", "clerk", null, "clerk", AccountType.CLERK);
        pizzaManager.register("client", "client", null, "client", AccountType.CLIENT);
        pizzaManager.addProduct(ProductType.PIZZA, "pizza1", new BigDecimal(10), LocalTime.of(0, 5));
        pizzaManager.addProduct(ProductType.BEVERAGE, "beverage1", new BigDecimal(5), LocalTime.of(0, 2));
        pizzaManager.addProduct(ProductType.SAUCE, "sauce1", new BigDecimal(2), LocalTime.of(0, 1));

        //stop adding data
        Account account = null;
        final Scanner scan = new Scanner(System.in);
        System.out.println("1. Login");
        System.out.println("2. Register");
        String line = scan.nextLine();
        while (account == null) {
            final String[] command = line.split(",");

            switch (Integer.parseInt(command[0])) {
                case 1:
                    if (validateArguments(command, 3)) {
                        account = pizzaManager.findAccount(command[1], command[2]);
                    }

                    break;
                case 2:
                    if (validateArguments(command, 5)) {
                        account = pizzaManager.register(command[1], command[2], command[3], command[4], AccountType.CLIENT);
                    }
                    break;
            }
            if (account == null) {
                System.out.println("Wrong input");
                line = scan.nextLine();
            }

        }
        printCommands(account);
        line = scan.nextLine();
        while (true) {

            final String[] command = line.split("[,]");
            Command thisCommand = findCommand(command[0], account);

            if (thisCommand != null) {
                thisCommand.invoke(pizzaManager, account, command);
            } else {
                System.err.println("Command not found");
                System.exit(1);
            }

            line = scan.nextLine();
        }

    }

    private static Command findCommand(final String labelNumber, Account account) {
        for (Command command : Command.values()) {
            if ((command.getLabelNumber().toString().equals(labelNumber))) {
                for (int i = 0; i < command.getAccess().length; i++) {
                    if (command.getAccess()[i] == account.getAccountType().getAccess()) {
                        return command;
                    }

                }
                System.out.println("You don't have access to this command");
            }
        }
        return null;
    }

    private static boolean validateArguments(final String[] arguments, int n) {
        return arguments != null && arguments.length == n;
    }

    private static void printCommands(Account account) {
        System.out.println("List of the commands:");
        for (Command command : Command.values()) {
            for (int i = 0; i < command.getAccess().length; i++) {
                if (command.getAccess()[i] == account.getAccountType().getAccess()) {
                    System.out.println(command.getLabelNumber() + "." + command.getLabel());
                }

            }
        }

    }
}