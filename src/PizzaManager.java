import TastyPizza.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    public Account registerClient(String username, String password, String email, String name) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setEmail(email);
        account.setName(name);
        account.setAccountType(AccountType.CLIENT);
        accountList.add(account);
        System.out.println("The account has been registered");
        return account;
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
        order.setProductList(getProductsWithId(idsAndQuantity));
        order.setTotal(calculateTotal(order.getProductList()));
        order.setLocalDateTime(getToday());
        order.setDelivered(false);
        orderList.add(order);
        System.out.println("You have made the order");
    }

    public void makeSameOrder(int orderId) {
        Order order = getOrder(orderId);
        Order sameOrder = new Order();
        sameOrder.setClient(order.getClient());
        sameOrder.setProductList(order.getProductList());
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
                System.out.println(order.getId() + ". " + "Client: " + order.getClient().getName() + " Total price: " + order.getTotal() + " lev");
            }
        }
    }

    public void getEstimateTime(int orderId) {
        Order order = getOrder(orderId);
        Duration duration = Duration.ofMinutes(10);
        LocalTime localTime = null;
        if (order != null) {
            localTime = order.getLocalDateTime().toLocalTime();

            localTime.plus(duration);
            for (OrderProduct orderProduct : order.getProductList()) {
                localTime = localTime.plusHours(orderProduct.getProduct().getEstimateTime().getHour() * orderProduct.getQuantity());
                localTime = localTime.plusMinutes(orderProduct.getProduct().getEstimateTime().getMinute() * orderProduct.getQuantity());
                if (orderProduct.getQuantity() > 3) {
                    localTime = localTime.minusMinutes(orderProduct.getProduct().getEstimateTime().getMinute());
                }
            }
            System.out.println(localTime.toString());
        } else {
            System.out.println("There isn't order with this id");
        }
    }

    private Order getOrder(int orderId) {
        for (Order order : orderList) {
            if (order.getId() == orderId) {
                return order;
            }
        }
        return null;
    }

    private BigDecimal calculateTotal(List<OrderProduct> products) {
        BigDecimal total = new BigDecimal(0);
        for (OrderProduct product : products) {
            total = total.add(product.getProduct().getPrice().multiply(new BigDecimal(product.getQuantity())));
        }
        return total;
    }

    private LocalDateTime getToday() {
        return LocalDateTime.now();
    }

    private List<OrderProduct> getProductsWithId(String[] idsAndQuantity) {
        List<OrderProduct> productList = new ArrayList<>();
        for (int i = 0; i < idsAndQuantity.length; i += 2) {
            for (Product product : this.productList) {
                if (product.getId() == Integer.parseInt(idsAndQuantity[i])) {
                    productList.add(new OrderProduct(product, Integer.parseInt(idsAndQuantity[i + 1])));
                }
            }
        }
        return productList;
    }

}
