package app.game.utils;

/**
 * This enum specifies wheether a code button is enabled or disabled.
 */
public enum BtnCodeState {
    /**
     * Code button is enabled.
     */
    ENABLED(true),
    /**
     * Code button is disabled.
     */
    DISABLED(false);

    private final boolean activated;

    BtnCodeState(final boolean value) {
        this.activated = value;
    }

    /**
     * @return state value.
     */

    public boolean isActivated() {
        return this.activated;
    }
}
