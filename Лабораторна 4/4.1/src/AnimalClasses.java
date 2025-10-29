abstract class Animal {
    private String name;
    public Animal(String name) { this.name = name; }
    public String getName() { return name; }
    @Override public String toString() { return getClass().getSimpleName() + " " + name; }
}

abstract class Mammal extends Animal { // Ссавець
    public Mammal(String name) { super(name); }
}

abstract class Bird extends Animal { // Птах
    public Bird(String name) { super(name); }
}

class Lion extends Mammal { // Лев
    public Lion(String name) { super(name); }
}

abstract class Ungulate extends Mammal { // Копитні
    public Ungulate(String name) { super(name); }
}

class Zebra extends Ungulate { // Зебра
    public Zebra(String name) { super(name); }
}

class Giraffe extends Ungulate { // Жираф
    public Giraffe(String name) { super(name); }
}

class Eagle extends Bird { // Орел
    public Eagle(String name) { super(name); }
}