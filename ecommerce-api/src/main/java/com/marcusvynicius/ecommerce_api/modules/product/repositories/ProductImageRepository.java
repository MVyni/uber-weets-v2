package com.marcusvynicius.ecommerce_api.modules.product.repositories;

import com.marcusvynicius.ecommerce_api.modules.product.entities.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, UUID> {

    /**
     * Returns all images of a product ordered by sort_order ascending.
     * Useful for rendering image galleries in the correct display order.
     */
    List<ProductImageEntity> findByProductIdOrderBySortOrderAsc(UUID productId);

    /**
     * Returns the main (featured) image of a product, if one is set.
     * Typically used for product listings and thumbnails.
     */
    Optional<ProductImageEntity> findByProductIdAndIsMainImageTrue(UUID productId);

    /**
     * Returns all images that are marked as main image for a product.
     * Should only ever return 0 or 1 result; useful for integrity checks.
     */
    List<ProductImageEntity> findAllByProductIdAndIsMainImageTrue(UUID productId);

    /**
     * Counts how many images a product currently has.
     * Useful for enforcing upload limits.
     */
    long countByProductId(UUID productId);

    /**
     * Checks whether a main image is already assigned to the product.
     */
    boolean existsByProductIdAndIsMainImageTrue(UUID productId);

    /**
     * Checks whether a specific image URL is already registered for a product.
     * Prevents duplicate image entries.
     */
    boolean existsByProductIdAndImageUrl(UUID productId, String imageUrl);

    /**
     * Deletes all images associated with a given product.
     * Used when a product is permanently deleted or when all images need to be replaced.
     */
    @Modifying
    @Query("DELETE FROM product_images pi WHERE pi.product.id = :productId")
    void deleteAllByProductId(@Param("productId") UUID productId);

    /**
     * Clears the main-image flag on all images of a product.
     * Should be called before setting a new main image to ensure only one exists.
     */
    @Modifying
    @Query("UPDATE product_images pi SET pi.is_main_image = false WHERE pi.product.id = :productId")
    void clearMainImageByProductId(@Param("productId") UUID productId);

    /**
     * Returns the next available sort_order value for a product.
     * Useful when appending a new image at the end of the gallery.
     */
    @Query("SELECT COALESCE(MAX(pi.sort_order), -1) + 1 FROM product_images pi WHERE pi.product.id = :productId")
    int findNextSortOrderByProductId(@Param("productId") UUID productId);
}

