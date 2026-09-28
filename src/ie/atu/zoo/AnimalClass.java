package ie.atu.zoo;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * The word "class" here refers to a classification, not a Java or 
 * programming class...! The enum represents the 7 animal classes 
 * that the machine learning models predict, matching the class_type
 * column (1 - 7) in data/zoo.csv.
 *
 * Keeping this as an enum (rather than passing raw long
 * ids around) means the compiler catches typos and an IDE can
 * autocomplete the class names.
 */
public enum AnimalClass {
    MAMMAL			(1, "mammal"),
    BIRD			(2, "bird"),
    REPTILE			(3, "reptile"),
    FISH			(4, "fish"),
    AMPHIBIAN		(5, "amphibian"),
    INSECT			(6, "insect"),
    INVERTEBRATE	(7, "invertebrate");

    private final int id;
    private final String label;

    AnimalClass(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int id() {
        return id;
    }

    public String label() {
        return label;
    }

    /*
     * Built once, not per lookup - a stream over 7 constants is cheap,
     * but there is no reason to repeat the work on every prediction.
     */
    private static final Map<Integer, AnimalClass> BY_ID =
    		Stream.of(values()).collect(Collectors.toMap(AnimalClass::id, c -> c));


    // Looks up the AnimalClass for a predicted label id (1 - 7).
    public static AnimalClass fromId(long id) {
        AnimalClass match = BY_ID.get((int) id);
        if (match == null) {
            throw new IllegalArgumentException("Unknown animal class id: " + id);
        }
        return match;
    }
}