package keqing.pollution.api.unification;

import gregtech.api.unification.Element;

import java.util.HashMap;
import java.util.Map;

import static gregtech.api.unification.Elements.add;

public class Elements {

    private static final Map<String, Element> elements = new HashMap<>();

    private Elements() {
    }
    public static final Element Ai = add(5, 5, -1, null, "Air", "Ai", false);
    public static final Element Fi = add(6, 6, -1, null, "Fire", "Fi", false);
    public static final Element Wa = add(7, 7, -1, null, "Water", "Wa", false);
    public static final Element Ea = add(8, 8, -1, null, "Earth", "Ea", false);
    public static final Element En = add(9, 9, -1, null, "Entropy", "En", false);
    public static final Element Ord = add(9, 9, -1, null, "Order", "Ord", false);
}