package kg.talantova.shoppingcart.DTO.product;

import jakarta.validation.constraints.*;

public class ProductUpdateDTO {
    @NotBlank
    @Size(min = 3, max = 255, message = "Name of the product should be between 3 and 255 symbols")
    private String name;
    @NotBlank
    @Size(min = 3, max = 255, message = "Short info of the product should be between 3 and 255 symbols")
    private String shortDescription;
    @NotNull(message = "Enter quantity")
    @Min(value = 1, message = "Min quantity of the new product is 1")
    private Integer quantity;

    private boolean isAvailable;
    @NotNull(message = "Enter rating")
    private Integer rating;
    @NotNull(message = "Enter price")
    @DecimalMin(value = "0.1", message = "Min price of the new product is 0.1 $")
    private Double price;

    public ProductUpdateDTO(String name, String shortDescription, int quantity, boolean isAvailable, int rating, double price) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.quantity = quantity;
        this.isAvailable = isAvailable;
        this.rating = rating;
        this.price = price;
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
