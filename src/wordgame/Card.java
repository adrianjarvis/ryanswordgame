package wordgame;

import java.net.URL;
import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.FillTransition;
import javafx.animation.FillTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author Adge
 */
public class Card extends StackPane {

    private String word;
    private final Label label;
    private final Rectangle rectangle;
    private AudioClip audioClip;
    private boolean flipped;

    public Card(String word, double width, double height) {
        this.word = word;
        label = new Label(word);
        rectangle = new Rectangle(0, 0, width, height);
        flipped = false;
        init();
    }

    public boolean isWordShowing() {
        return flipped;
    }

    public String getWord() {
        return word;
    }

    public boolean isAMatchFor(Card other) {
        return word.equals(other.word);
    }
    
    public void flipCard(EventHandler<ActionEvent> finishedHandler) {
        if (isWordShowing()) {
            flipCardToHideWord(finishedHandler);
        } else {
            flipCardToShowWord(finishedHandler);
        }
    }
    
    public void flipCardToShowWord(EventHandler<ActionEvent> finishedHandler) {
        flipped = true;
        audioClip.play();
        FillTransition fillTransition = FillTransitionBuilder.create()
                .duration(Duration.seconds(0.5))
                .shape(rectangle)
                .fromValue(Color.RED)
                .toValue(Color.LAWNGREEN)
                .cycleCount(1)
                .build();
        FadeTransition fadeTransition = FadeTransitionBuilder.create()
                .duration(Duration.seconds(0.5))
                .fromValue(0.).toValue(1.).node(label).build();
        if (finishedHandler != null) {
            fillTransition.setOnFinished(finishedHandler);
        }
        fillTransition.play();
        fadeTransition.play();
    }

    public void flipCardToHideWord(EventHandler<ActionEvent> finishedHandler) {
        flipped = false;
        FillTransition fillTransition = FillTransitionBuilder.create()
                .duration(Duration.seconds(0.5))
                .shape(rectangle)
                .fromValue(Color.LAWNGREEN)
                .toValue(Color.RED)
                .cycleCount(1)
                .build();
        FadeTransition fadeTransition = FadeTransitionBuilder.create()
                .duration(Duration.seconds(0.5))
                .fromValue(1.).toValue(0.).node(label).build();
        if (finishedHandler != null) {
            fillTransition.setOnFinished(finishedHandler);
        }
        fillTransition.play();
        fadeTransition.play();
    }
    
    private void init() {
        URL resource = AudioClip.class.getResource("/" + word.toLowerCase() + ".wav");
        audioClip = new AudioClip(resource.toString());
        label.setOpacity(0.);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(3.);
        dropShadow.setOffsetY(3.);
        label.setEffect(dropShadow);
        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10);
        rectangle.setFill(Color.RED);
        rectangle.setUserData(word);
        getChildren().add(rectangle);
        getChildren().add(label);
    }

}
