import java.util.ArrayList;
import java.util.List;

public class Zoo {

    public List<Cage<? extends Animal>> cages = new ArrayList<>();

    public void addCage(Cage<? extends Animal> cage) {
        cages.add(cage);
        System.out.println("-> " + cage.getClass().getSimpleName() + " додано до зоопарку.");
    }

    public int getCountOfAnimals() {
        int totalCount = 0;
        for (Cage<? extends Animal> cage : cages) {
            totalCount += cage.getOccupiedPlaces();
        }
        return totalCount;
    }
}