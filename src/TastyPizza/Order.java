package TastyPizza;

import TastyPizza.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int maxID = 1;
    private int id;
    private List<OrderProduct> productList = new ArrayList<>();
    private Account client;
    private Boolean delivered;
    private BigDecimal total;
    private LocalDateTime localDateTime;
    private LocalTime estimatedTime;

    public Order() {
        this.id = maxID;
        maxID++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OrderProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<OrderProduct> productList) {
        this.productList = productList;
    }

    public Account getClient() {
        return client;
    }

    public void setClient(Account client) {
        this.client = client;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalTime getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(LocalTime estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
}
