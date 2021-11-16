package de.lukaz.necromancyplus.enums;

public enum Module {

    DISPLAY_DROPPED_SOULS, VIEW_MANA_COST_IN_MENU, CONFIRM_REMOVE, ADDITIONAL_SOUL_INFO;

    public int state;
    public void setState(int state) {
        this.state = state;
    }
    public int getState() {
        return state;
    }
}
