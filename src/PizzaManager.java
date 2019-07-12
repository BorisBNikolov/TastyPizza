import model.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PizzaManager {
    private List<Product> productList = new ArrayList<>();
    private List<Order> orderList = new ArrayList<>();
    private List<Account> accountList = new ArrayList<>();

    public Account findAccount(String name, String password) {
        for (Account account : accountList) {
            if (name.equals(account.getUsername()) && password.equals(account.getPassword())) {
                return account;
            }
        }
        return null;
    }


    public Account register(String username, String password, String email, String name, AccountType accountType) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setEmail(email);
        account.setName(name);
        account.setAccountType(accountType);
        accountList.add(account);
        System.out.println("The account has been registered");
        return account;
    }

    public void addProduct(ProductType productType, String name, BigDecimal price, LocalTime estimateTime) {
        Product newProduct = new Product();
        newProduct.setActive(true);
        newProduct.setProductType(productType);
        newProduct.setName(name);
        newProduct.setPrice(price);
        newProduct.setEstimateTime(estimateTime);
        productList.add(newProduct);
        System.out.println("The product has been added");
    }

    public void deactivateProduct(int id) {
        for (Product product : productList) {
            if (product.getId() == id) {
                product.setActive(false);
                System.out.println("The product has been deactivated");
                break;
            }
        }
    }

    public void printActiveProducts() {
        System.out.println("All active products:");
        for (Product product : productList) {
            if (product.getActive()) {
                System.out.println(product.getId() + ". " + product.getName() + " " + product.getPrice() + " lev");
            }
        }
    }

    public void makeOrder(String[] idsAndQuantity, Account client) {
        Order order = new Order();
        order.setClient(client);
        order.setProductCountMap(getProductsWithId(idsAndQuantity));
        order.setTotal(calculateTotal(order.getProductCountMap()));
        order.setLocalDateTime(getToday());
        order.setDelivered(false);
        order.setEstimatedTime(calculateEstimateTime(order));
        orderList.add(order);
        System.out.println("You have made the order");
    }

    public void makeSameOrder(int orderId) {
        Order order = getOrder(orderId);
        Order sameOrder = new Order();
        sameOrder.setClient(order.getClient());
        sameOrder.setProductCountMap(order.getProductCountMap());
        sameOrder.setTotal(order.getTotal());
        sameOrder.setDelivered(false);
        sameOrder.setLocalDateTime(getToday());
        orderList.add(sameOrder);
        System.out.println("You have made the order");
    }

    public void printOrders(Boolean delivered) {
        System.out.println("Orders:");
        for (Order order : orderList) {
            if (order.getDelivered() == delivered) {
                System.out.println(order.getId() + ". " + "Client: " + order.getClient().getName() + "Total price: " + order.getTotal() + " lev");
            }
        }
    }

    public void reference(LocalDateTime after, LocalDateTime before) {
        System.out.println("The orders between " + after.toString() + " and " + before.toString());
        for (Order order : orderList) {
            if (order.getLocalDateTime().isAfter(after) && order.getLocalDateTime().isBefore(before)) {
                System.out.println(order.getId() + ". " + "Client: " + order.getClient().getName() +
                        " The date: " + order.getLocalDateTime().toString() + " Total price: " + order.getTotal() + " lev");
            }
        }
    }

    public void changeOrder(Account account, int id, String[] idsAndQuantity) {
        for (Order order : orderList) {
            if (order.getId() == id) {
                if (account.getAccountType().equals(AccountType.CLIENT)) {
                    if (account.getUsername().equals(order.getClient().getUsername())) {
                        order.setProductCountMap(getProductsWithId(idsAndQuantity));
                        System.out.println("The order has been changed");
                        break;
                    } else {
                        System.out.println("You haven't made this order");
                        break;
                    }
                } else {
                    order.setProductCountMap(getProductsWithId(idsAndQuantity));
                    System.out.println("The order has been changed");
                    break;
                }

            }
        }
    }

    public void deliverOrder(int id) {
        for (Order order : orderList) {
            if (order.getId() == id) {
                order.setDelivered(true);
                System.out.println("The order has been delivered");
                break;
            }
        }
    }

    public void getEstimateTime(int orderId) {
        Order order = getOrder(orderId);
        if (order != null) {
            System.out.println(order.getEstimatedTime().toString());
        } else {
            System.out.println("There isn't order with this id");
        }
    }

    public LocalTime calculateEstimateTime(Order order) {
        Duration duration = Duration.ofMinutes(10);
        LocalTime localTime;
        localTime = order.getLocalDateTime().toLocalTime();
        localTime.plus(duration);
        for (Map.Entry<Product, Integer> entry : order.getProductCountMap().entrySet()) {
            localTime = localTime.plusHours(entry.getKey().getEstimateTime().getHour() * entry.getValue());
            localTime = localTime.plusMinutes(entry.getKey().getEstimateTime().getMinute() * entry.getValue());
            if (entry.getValue() > 3) {
                localTime = localTime.minusMinutes(entry.getKey().getEstimateTime().getMinute());
            }
        }
        return localTime;
    }

    private Order getOrder(int orderId) {
        for (Order order : orderList) {
            if (order.getId() == orderId) {
                return order;
            }
        }
        return null;
    }

    private BigDecimal calculateTotal(Map<Product, Integer> productCountMap) {
        BigDecimal total = new BigDecimal(0);
        for (Map.Entry<Product, Integer> entry : productCountMap.entrySet()) {
            total = total.add(entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue())));
        }
        return total;
    }

    private LocalDateTime getToday() {
        return LocalDateTime.now();
    }

    private Map<Product, Integer> getProductsWithId(String[] idsAndQuantity) {
        Map<Product, Integer> productCountMap = new HashMap<>();
        for (int i = 0; i < idsAndQuantity.length; i += 2) {
            for (Product product : this.productList) {
                if (product.getId() == Integer.parseInt(idsAndQuantity[i])) {
                    productCountMap.put(product, Integer.parseInt(idsAndQuantity[i + 1]));
                }
            }
        }
        return productCountMap;
    }

}
