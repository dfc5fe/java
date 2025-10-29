import java.util.ArrayList;
import java.util.List;

abstract class Cage<T extends Animal> {
    private int maxCapacity;
    private List<T> animals;

    public Cage(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.animals = new ArrayList<>();
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getOccupiedPlaces() {
        return animals.size();
    }

    public void addAnimal(T animal) {
        if (animals.size() >= maxCapacity) {
            throw new EnclosureFullException("Enclosure is full. Cannot add " + animal);
        }
        animals.add(animal);
        System.out.println(animal + " був розміщений у " + this.getClass().getSimpleName());
    }

    public void removeAnimal(T animal) {
        if (!animals.remove(animal)) {
            throw new AnimalNotFoundException(animal + " не знайдено у цьому вольєрі.");
        }
        System.out.println(animal + " був вилучений з " + this.getClass().getSimpleName());
    }

    public List<T> getAnimals() {
        return animals;
    }
}

abstract class MammalCage<T extends Mammal> extends Cage<T> {
    public MammalCage(int maxCapacity) { super(maxCapacity); }
}

abstract class BirdCage<T extends Bird> extends Cage<T> {
    public BirdCage(int maxCapacity) { super(maxCapacity); }
}

class LionCage extends MammalCage<Lion> {
    public LionCage(int maxCapacity) { super(maxCapacity); }
}

class UngulateCage extends MammalCage<Ungulate> {
    public UngulateCage(int maxCapacity) { super(maxCapacity); }
}

class GeneralBirdCage extends BirdCage<Bird> {
    public GeneralBirdCage(int maxCapacity) { super(maxCapacity); }
}