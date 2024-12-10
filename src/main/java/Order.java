import java.time.LocalDate;
import java.util.Set;

public class Order {
    private final Long id;
    private final LocalDate orderDate;
    private final LocalDate deliveryDate;
    private final String status;
    private final Set<Product> products;

    public Order(Long id, LocalDate orderDate, LocalDate deliveryDate, String status, Set<Product> products) {
        this.id = id;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.products = products;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public Set<Product> getProducts() {
        return products;
    }
}
