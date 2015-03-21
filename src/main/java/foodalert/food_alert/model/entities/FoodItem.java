package foodalert.food_alert.model.entities;

import java.util.Collection;

public final class FoodItem {

    private final String cip13;
    private final String imageUrl;
    private final Collection<String> allergens;

    public FoodItem(String cip13, String imageUrl, Collection<String> allergens) {
        this.cip13 = cip13;
        this.imageUrl = imageUrl;
        this.allergens = allergens;
    }

    public Collection<String> allergens() {
        return allergens;
    }

    public String imageUrl() {
        return imageUrl;
    }

    public String cip13() {
        return cip13;
    }
}
