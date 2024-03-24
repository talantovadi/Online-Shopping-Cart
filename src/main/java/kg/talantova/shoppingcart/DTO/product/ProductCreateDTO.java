package kg.talantova.shoppingcart.DTO.product;

import jakarta.validation.constraints.*;

public class ProductCreateDTO {
    @NotBlank
    @Size(min = 3, max = 255, message = "Name of the product should be between 3 and 255 symbols")
    private String name;
    @NotBlank
    @Size(min = 3, max = 255, message = "Short info of the product should be between 3 and 255 symbols")
    private String shortDescription;

    @NotNull
    @Min(value = 1, message = "Min quantity of the new product is 1")
    private int quantity;

    @NotNull
    @DecimalMin(value = "0.1", message = "Min price of the new product is 0.1 $")
    private double price;

    public ProductCreateDTO(String name, String shortDescription, int quantity, double price) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.quantity = quantity;
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



    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
