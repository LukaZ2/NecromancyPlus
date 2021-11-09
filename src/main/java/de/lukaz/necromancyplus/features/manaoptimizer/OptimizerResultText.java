package de.lukaz.necromancyplus.features.manaoptimizer;

public class OptimizerResultText {

    public boolean success;
    public String text;
    public String optimizedMana;

    public OptimizerResultText(String text,boolean success, String optimizedMana) {
        this.success = success;
        this.text = text;
        this.optimizedMana = optimizedMana;
    }
}
