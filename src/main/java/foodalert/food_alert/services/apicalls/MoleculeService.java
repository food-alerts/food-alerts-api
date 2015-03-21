package foodalert.food_alert.services.apicalls;

import com.google.common.base.Function;
import foodalert.food_alert.model.entities.Cip13;
import foodalert.food_alert.model.entities.Molecule;

import java.util.Collection;

/**
 * Created by malk on 21/03/15.
 */
public interface MoleculeService extends Function<Cip13, Collection<Molecule>>{
}
