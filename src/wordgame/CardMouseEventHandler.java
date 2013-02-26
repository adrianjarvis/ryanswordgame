package wordgame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Adge
 */
public class CardMouseEventHandler implements EventHandler<MouseEvent> {

    private final Card card;
    private final GameController controller;

    public CardMouseEventHandler(Card card, GameController controller) {
        this.card = card;
        this.controller = controller;
    }

    @Override
    public void handle(MouseEvent t) {
        if (!card.isWordShowing()) {
            card.flipCardToShowWord(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    controller.checkForMatch(card);
                }
            });
        }
    }
}
