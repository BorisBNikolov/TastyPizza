import model.Account;
import model.AccountType;
import model.ProductType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public enum Command {
    REGISTER(1, "Register", new int[]{3}) {
        @Override
        public void invoke(PizzaManager pizzaManager, Account account, String[] arguments) {
            if (validateArgumentsOfInvoke(pizzaManager, arguments, 6)) {
                pizzaManager.register(arguments[1], arguments[2], arguments[3], arguments[4], findAccountType(arguments[5]));
            } else {
                System.err.println("Couldn't register");
                System.exit(1);
            }

        }
    },
    ADD_PRODUCT(2, "Add product", new int[]{2, 3}) {
        @Override
        public void invoke(PizzaManager pizzaManager, Account account, String[] arguments) {
            if (validateArgumentsOfInvoke(pizzaManager, arguments, 5)) {
                pizzaManager.addProduct(Command.findProductType(arguments[1]), arguments[2],
                        new BigDecimal(arguments[3]), LocalTime.of(0, Integer.parseInt(arguments[4])));
            } else {
                System.err.println("Couldn't add the product");
                System.exit(1);
            }

        }
    },
    DEACTIVATE_PRODUCT(3, "Deactivate product", new int[]{2, 3}) {
        @Override
        public void invoke(PizzaManager pizzaManager, Account account, String[] arguments) {
            if (validateArgumentsOfInvoke(pizzaManager, arguments, 2)) {
                pizzaManager.deactivateProduct(Integer.parseInt(arguments[1]));
            } else {
                System.err.println("Couldn't deactivate");
                System.exit(1);
            }

        }
    },
    PRODUCTS(4, "Print all products for sale", new int[]{1, 2, 3}) {
        @Override
        public void invoke(PizzaManager pizzaManager, Account account, String[] arguments) {
            if (validateArgumentsOfInvoke(pizzaManager, arguments, 1)) {
                pizzaManager.printActiveProducts();
            } else {
                System.err.println("Couldn't register");
                System.exit(1);
            }

        }
    },
    MAKE_ORDER(5, "Make order", new int[]{1, 2, 3}) {
        @Override
        public void invoke(PizzaManager pizzaManager, Account account, String[] arguments) {
            if (validateArgumentsOfInvoke(pizzaManager, arguments, arguments.length)) {
                pizzaManager.makeOrder(Arrays.copyOfRange(arguments, 1, arguments.length), account);
            } else {
                System.err.println("Couldn't make the order");
                System.exit(1);
            }

        }
    },
    MAKE_THE_SAME_ORDER(6, "Make the same order", new int[]{1, 2, 3}) {
        @Override
        public void invoke(PizzaManager pizzaManager, Account account, String[] arguments) {
            if (validateArgumentsOfInvoke(pizzaManager, arguments, 2)) {
                pizzaManager.makeSameOrder(Integer.parseInt(arguments[1]));
            } else {
                System.err.println("Couldn't deactivate");
                System.exit(1);
            }

        }
    },

    PRINT_ORDERS(7, "PrintOrders", new int[]{2, 3}) {
        @Override
        public void invoke(PizzaManager pizzaManager, Account account, String[] arguments) {
            if (validateArgumentsOfInvoke(pizzaManager, arguments, 2)) {
                if ("delivered".equals(arguments[1])) {
                    pizzaManager.printOrders(true);
                } else if ("non-delivered".equals(arguments[1])) {
                    pizzaManager.printOrders(false);
                }

            } else {
                System.err.println("Couldn't print");
                System.exit(1);
            }

        }
    },
    ESTIMATE_TIME(8, "Estimate order time", new int[]{1, 2, 3}) {
        @Override
        public void invoke(PizzaManager pizzaManager, Account account, String[] arguments) {
            if (validateArgumentsOfInvoke(pizzaManager, arguments, 2)) {
                pizzaManager.getEstimateTime(Integer.parseInt(arguments[1]));
            } else {
                System.err.println("Couldn't do it");
                System.exit(1);
            }

        }
    },
    REFERENCE(9, "Reference with format yyyy-MM-dd HH:mm", new int[]{2, 3}) {
        @Override
        public void invoke(PizzaManager pizzaManager, Account account, String[] arguments) {
            if (validateArgumentsOfInvoke(pizzaManager, arguments, 3)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime after = LocalDateTime.parse(arguments[1], formatter);
                LocalDateTime before = LocalDateTime.parse(arguments[2], formatter);
                pizzaManager.reference(after, before);
            } else {
                System.err.println("Couldn't reference");
                System.exit(1);
            }

        }
    },
    CHANGE_ORDER(10, "Change order", new int[]{1, 2, 3}) {
        @Override
        public void invoke(PizzaManager pizzaManager, Account account, String[] arguments) {
            if (validateArgumentsOfInvoke(pizzaManager, arguments, arguments.length)) {
                pizzaManager.changeOrder(account, Integer.parseInt(arguments[1]), Arrays.copyOfRange(arguments, 2, arguments.length));
            } else {
                System.err.println("Couldn't change");
                System.exit(1);
            }

        }
    },

    DELIVER_ORDER(11, "Deliver order", new int[]{2, 3}) {
        @Override
        public void invoke(PizzaManager pizzaManager, Account account, String[] arguments) {
            if (validateArgumentsOfInvoke(pizzaManager, arguments, 2)) {
                pizzaManager.deliverOrder(Integer.parseInt(arguments[1]));
            } else {
                System.err.println("Couldn't deactivate");
                System.exit(1);
            }

        }
    },


    EXIT(12, "Exit", new int[]{1, 2, 3}) {
        @Override
        public void invoke(PizzaManager pizzaManager, Account account, String[] arguments) {
            if (validateArgumentsOfInvoke(pizzaManager, arguments, 1)) {
                System.exit(0);
            } else {
                System.err.println("Couldn't exit");
                System.exit(1);
            }

        }
    };

    private int[] access;

    private final String label;

    private final Integer labelNumber;

    Command(final int labelNumber, final String label, int[] access) {
        this.label = label;
        this.labelNumber = labelNumber;
        this.access = access;
    }

    public String getLabel() {
        return label;
    }


    public Integer getLabelNumber() {
        return labelNumber;
    }

    public int[] getAccess() {
        return access;
    }

    public abstract void invoke(PizzaManager pizzaManager, Account account, String[] arguments);

    private static boolean validateArgumentsOfInvoke(final PizzaManager pizzaManager, final String[] arguments,
                                                     int n) {
        return pizzaManager != null && arguments != null && arguments.length == n;
    }

    private static AccountType findAccountType(final String accountType) {
        for (AccountType accountType1 : AccountType.values()) {
            if ((accountType1.toString().equals(accountType))) {
                return accountType1;
            }
        }
        return null;
    }

    private static ProductType findProductType(final String productType) {
        for (ProductType productType1 : ProductType.values()) {
            if ((productType1.toString().equals(productType))) {
                return productType1;
            }
        }
        return null;
    }
}
