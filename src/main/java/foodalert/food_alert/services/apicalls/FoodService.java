package foodalert.food_alert.services.apicalls;

import com.fasterxml.jackson.jr.ob.JSON;
import com.google.common.base.Optional;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import foodalert.food_alert.model.entities.Cip13;
import foodalert.food_alert.model.entities.FoodItem;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.google.common.collect.Lists.newArrayList;

public final class FoodService {

    private final OkHttpClient httpClient = new OkHttpClient();

    public Optional<FoodItem> apply(Cip13 cip13) {
        String url = "http://fr.openfoodfacts.org/api/v0/produit/" + cip13.code() + ".json";
        try {
            Optional<String> json = call(url);
            if (json.isPresent()) {
                return foodItem(json.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.absent();
    }

    private Optional<String> call(String url) throws IOException {
        Request request = new Request.Builder().get().url(url).build();
        Response response = httpClient.newCall(request).execute();
        return response.code() == 200 ? Optional.of(response.body().string()) : Optional.<String>absent();
    }

    private Optional<FoodItem> foodItem(String json) throws Exception {
        final Map<String, Object> map = JSON.std.mapFrom(json);
        if (map.containsValue("product not found")) {
            return Optional.absent();
        }
        final Map<String, String> product = (Map<String, String>) map.get("product");
        final String cip13 = cip(product);
        final String imageUrl = imageUrl(product);
        final List<String> allergens = allergens(product);
        return Optional.of(new FoodItem(cip13, imageUrl, allergens));
    }

    private static String cip(Map<String, String> product) {
        return product.getOrDefault("_id", "");
    }

    private static String imageUrl(Map<String, String> product) {
        return product.getOrDefault("image_url", "");
    }

    private static List<String> allergens(Map<String, String> product) {
        final List<String> allergens = newArrayList();
        final Scanner allergensScanner = new Scanner(product.getOrDefault("allergens", ""));
        allergensScanner.useDelimiter(", ");
        while (allergensScanner.hasNext()) {
            allergens.add(allergensScanner.next());
        }
        return allergens;
    }

}
