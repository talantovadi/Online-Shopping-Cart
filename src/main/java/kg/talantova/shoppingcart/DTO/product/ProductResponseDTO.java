package kg.talantova.shoppingcart.DTO.product;


public class ProductResponseDTO {
    private Long id;
    private String name;
    private String shortDescription;
    private int quantity;
    private boolean isAvailable;
    private int rating;
    private double price;

    public ProductResponseDTO(Long id, String name, String shortDescription, int quantity, boolean isAvailable, int rating, double price) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.quantity = quantity;
        this.isAvailable = isAvailable;
        this.rating = rating;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
