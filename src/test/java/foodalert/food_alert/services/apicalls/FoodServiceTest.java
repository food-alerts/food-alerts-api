package foodalert.food_alert.services.apicalls;

import com.google.common.base.Optional;
import foodalert.food_alert.model.entities.Cip13;
import foodalert.food_alert.model.entities.FoodItem;
import org.assertj.core.api.iterable.Extractor;
import org.junit.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public final class FoodServiceTest {

    @Test
    public void hasCip13()  {
        FoodService foodService = new FoodService();
        Cip13 cip13 = new Cip13("3237550029213");
        FoodItem foodItem = foodService.apply(cip13).get();
        assertThat(foodItem.cip13().startsWith("http:"));
    }

    @Test
    public void hasImage()  {
        FoodService foodService = new FoodService();
        Cip13 cip13 = new Cip13("3237550029213");
        FoodItem foodItem = foodService.apply(cip13).get();
        assertThat(foodItem.imageUrl().startsWith("http:"));
    }

    @Test
    public void hasLactoseAllergen()  {
        FoodService foodService = new FoodService();
        Cip13 cip13 = new Cip13("3237550029213");
        FoodItem foodItem = foodService.apply(cip13).get();
        assertThat(foodItem).isNotNull();
        assertThat(foodItem.allergens()).contains("lactose");
    }

    @Test
    public void hasNoLactoseAllergen()  {
        FoodService foodService = new FoodService();
        Cip13 cip13 = new Cip13("3245390063441");
        FoodItem foodItem = foodService.apply(cip13).get();
        assertThat(foodItem).isNotNull();
        assertThat(foodItem.allergens()).doesNotContain("lactose");
    }

    @Test
    public void unknown()  {
        FoodService foodService = new FoodService();
        Cip13 cip13 = new Cip13("XXXXXXXXXXXXX");
        assertThat(foodService.apply(cip13)).isEqualTo(Optional.absent());
    }

}