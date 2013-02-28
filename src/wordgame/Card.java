package wordgame;

import java.net.URL;
import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.FillTransition;
import javafx.animation.FillTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Adge
 */
public class Card extends StackPane {

    private String word;
    private final Text label;
    private final Rectangle rectangle;
    private AudioClip audioClip;
    private boolean flipped;
    private Color cardBackColour = Color.NAVY;
    private Color cardFrontColour = Color.ORANGE;

    public Card(String word, double width, double height) {
        this.word = word;
        label = new Text(word);
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
    
    public void playMatch() {
        FillTransition fillTransition = FillTransitionBuilder.create()
                .duration(Duration.seconds(0.25))
                .shape(rectangle)
                .fromValue(cardFrontColour)
                .toValue(Color.LIGHTYELLOW)
                .cycleCount(2)
                .autoReverse(true)
                .build();
        fillTransition.play();
    }
    
    public void flipCardToShowWord(EventHandler<ActionEvent> finishedHandler) {
        flipped = true;
        audioClip.play();
        FillTransition fillTransition = FillTransitionBuilder.create()
                .duration(Duration.seconds(0.5))
                .shape(rectangle)
                .fromValue(cardBackColour)
                .toValue(cardFrontColour)
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
                .fromValue(cardFrontColour)
                .toValue(cardBackColour)
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
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setOffsetX(6.0);
        innerShadow.setOffsetY(3.0);
        innerShadow.setHeight(20.);
        innerShadow.setWidth(75.);
        label.setEffect(innerShadow);
        label.setFill(cardFrontColour);
        label.setFont(Font.font("Ariel", FontWeight.BOLD, 26.));
        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10);
        rectangle.setFill(cardBackColour);
        rectangle.setUserData(word);
        rectangle.setStroke(Color.BLACK);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(3.);
        dropShadow.setOffsetY(3.);
        rectangle.setEffect(dropShadow);
        getChildren().add(rectangle);
        getChildren().add(label);
    }

}
