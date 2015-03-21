package foodalert.food_alert.services.apicalls;

import foodalert.food_alert.model.entities.Cip13;
import foodalert.food_alert.model.entities.Molecule;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.iterable.Extractor;
import org.junit.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class DrugServiceTest {

    @Test
    public void testLactose()  {
        DrugService drugService = new DrugService();
        Cip13 cip13 = new Cip13("3400955473350");
        assertThat(drugService.apply(cip13))
                .extracting(new Extractor<Molecule, String>() {
                    @Override
                    public String extract(Molecule molecule) {
                        return molecule.name();
                    }
                }).contains("Lactose");
    }
}