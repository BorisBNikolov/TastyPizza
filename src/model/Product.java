package model;

import java.math.BigDecimal;
import java.time.LocalTime;

public class Product {
    private static int maxID = 1;
    private int id;
    private ProductType productType;
    private String name;
    private BigDecimal price;
    private Boolean active;
    private LocalTime estimateTime;

    public Product() {
        this.id = maxID;
        maxID++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalTime getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(LocalTime estimateTime) {
        this.estimateTime = estimateTime;
    }
}
