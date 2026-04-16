package fr.gopartner.tregusto.administration.api.dto;

import jakarta.validation.constraints.NotNull;

public class ProductIngredientRequestDTO {

    @NotNull(message = "Ingredient ID is required")
    private Integer ingredientId;

    private String quantity;

    private Integer displayOrder = 0;

    private Boolean isOptional = false;

    public ProductIngredientRequestDTO() {}

    public Integer getIngredientId() { return ingredientId; }
    public void setIngredientId(Integer ingredientId) { this.ingredientId = ingredientId; }
    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    public Boolean getIsOptional() { return isOptional; }
    public void setIsOptional(Boolean isOptional) { this.isOptional = isOptional; }
}
