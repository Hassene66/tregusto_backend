package fr.gopartner.tregusto.administration.api.dto;

import jakarta.validation.constraints.NotBlank;

public class ProductImageRequestDTO {

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    private String altText;

    private Integer displayOrder = 0;

    public ProductImageRequestDTO() {}

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getAltText() { return altText; }
    public void setAltText(String altText) { this.altText = altText; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
