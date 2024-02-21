package app.card.api;

import app.player.api.Player;
import java.util.Objects;

/**
 * A functional interface for design actions of static cards.
 * This is a Strategy patterns with lamda expression
 */
@FunctionalInterface
public interface StaticActionStrategy {
    /**
     * @param player who get the action
     * @return pass the event treggered doing the action to unbuyable
     */
    TriggeredEvent myAction(Player player);

    /**
     * enum rappreset if action was successful or not with his own message.
     */
    enum TriggeredEvent {
        /**
         * performed says the action is successfull.
         */
        PERFORMED,
        /**
         * unperformed says the action is not successfull.
         */
        UNPERFORMED;

        private String message;

        /**
         * @param message to show in game to players.
         * @return hiself but with updated message.
         */
        public TriggeredEvent update(final String message) {
            this.message = Objects.requireNonNull(message, "Message can't be null");
            return this;
        }

        /**
         * @return the message of action.
         */
        public String getMessage() {
            return this.message;
        }

        /**
         * Clear message of each two enum.
         */
        public static void clearMessages() {
            for (final TriggeredEvent i : TriggeredEvent.values()) {
                i.update("");
            }
        }
    }
}
