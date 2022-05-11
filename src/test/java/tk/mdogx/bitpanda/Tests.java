package tk.mdogx.bitpanda;

import tk.mdogx.bitpanda.controllers.PetsController;
import tk.mdogx.bitpanda.domain.Category;
import tk.mdogx.bitpanda.domain.Pet;
import tk.mdogx.bitpanda.domain.Status;
import tk.mdogx.bitpanda.domain.Tag;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class Tests {
    private static final String PHOTO_URL = "https://static.milwaukeetool.eu/remote.axd/milwaukee-media-images.s3.amazonaws.com/hi/M12_FDGS-422B--Hero_2.jpg";
    PetsController petsController;
    Pet pet = new Pet.Builder()
            .withId(RandomStringUtils.randomNumeric(10))
            .withName("Pet")
            .withPhotoUrls(Collections.singletonList(PHOTO_URL))
            .withStatus(Status.available)
            .withTags(Collections.singletonList(new Tag(1, "golden-retriever")))
            .inCategory(new Category(1, "dogs")).build();

    @BeforeClass
    public void beforeClass() {
        petsController = new PetsController();
    }

    @Test(priority = 0)
    public void addNewPet() {
        Pet petResponse = petsController.addNewPet(pet);
        assertThat(petResponse, is(samePropertyValuesAs(pet)));
    }

    @Test(priority = 1)
    public void verifyNewPet() {
        Pet petResponse = petsController.findPet(pet);
        assertThat(petResponse, is(samePropertyValuesAs(pet)));
    }

    @Test(priority = 2)
    public void updatePet() {
        pet.setName("New name");
        Pet petResponse = petsController.updatePet(pet);
        assertThat(petResponse, is(samePropertyValuesAs(pet)));
    }

    @Test(priority = 3)
    public void verifyUpdatedPet() {
        Pet petResponse = petsController.findPet(pet);
        assertThat(petResponse, is(samePropertyValuesAs(pet)));
    }

    @Test(priority = 4)
    public void deletePetAndDoCheck() {
        petsController.deletePet(pet);
        petsController.verifyPetDeleted(pet);
    }
}
