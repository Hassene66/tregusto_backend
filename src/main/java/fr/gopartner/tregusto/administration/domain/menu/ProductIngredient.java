package fr.gopartner.tregusto.administration.domain.menu;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "product_ingredients")
public class ProductIngredient implements Serializable {

    @EmbeddedId
    private ProductIngredientId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    @JoinColumn(nullable = false)
    private Ingredient ingredient;

    private String quantity;

    @Column(nullable = false)
    private Integer displayOrder = 0;

    @Column(nullable = false)
    private Boolean isOptional = false;

    @Embeddable
    @Getter
    @Setter
    public static class ProductIngredientId implements Serializable {

        private Integer productId;
        private Integer ingredientId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProductIngredientId that = (ProductIngredientId) o;
            return Objects.equals(productId, that.productId) &&
                    Objects.equals(ingredientId, that.ingredientId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(productId, ingredientId);
        }
    }
}
