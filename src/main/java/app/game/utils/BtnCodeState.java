package app.game.utils;

/**
 * This enum specifies wheether a code button is enabled or disabled
 */
public enum BtnCodeState {
    /**
     * Code button is enabled
     */
    ENABLED(true),
    /**
     * Code button is disabled
     */
    DISABLED(false);

    private boolean value;

    BtnCodeState(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }
}
