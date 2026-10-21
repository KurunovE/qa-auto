package api.factory;

import api.model.Product;

import java.util.List;

public final class ProductFactory {

    private ProductFactory() {
    }

    public static List<Product> firstPageProducts() {
        return List.of(
                createProduct(1, "cerulean", 2000, "#98B2D1", "15-4020"),
                fuchsiaRose(),
                createProduct(3, "true red", 2002, "#BF1932", "19-1664"),
                createProduct(4, "aqua sky", 2003, "#7BC4C4", "14-4811"),
                createProduct(5, "tigerlily", 2004, "#E2583E", "17-1456"),
                createProduct(6, "blue turquoise", 2005, "#53B0AE", "15-5217")
        );
    }

    public static Product fuchsiaRose() {
        return createProduct(2, "fuchsia rose", 2001, "#C74375", "17-2031");
    }

    private static Product createProduct(int id, String name, int year, String color, String pantoneValue) {
        return Product.builder()
                .id(id)
                .name(name)
                .year(year)
                .color(color)
                .pantoneValue(pantoneValue)
                .build();
    }
}
