package wordgame;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.media.AudioClip;

/**
 *
 * @author Adge
 */
public class GameController {

    private Card firstCard;
    List<String> wordList;
    List<AudioClip> matchClips;

    public GameController(List<String> wordList) {
        firstCard = null;
        this.wordList = wordList;
        initSounds();
    }
    
    public void checkForMatch(Card card) {
        if (firstCard == null) {
            firstCard = card;
        } else {
            if (firstCard.isAMatchFor(card)) {
                registerMatch(card.getWord());
                playMatchAudio();
                firstCard.playMatch();
                card.playMatch();
                if (isGameFinished()) {
                    endGame();
                }
            } else {
                flipOverCardPair(card);
            }
            firstCard = null;
        }
    }

    private void registerMatch(String word) {
        wordList.remove(word);
    }
    
    private boolean isGameFinished() {
        return wordList.isEmpty();
    }
    
    private void playMatchAudio() {
        Collections.shuffle(matchClips);
        matchClips.get(0).play();
    }

    private void endGame() {
        
        System.out.println("Completed!");
    }

    private void flipOverCardPair(Card card) {
        firstCard.flipCard(null);
        card.flipCard(null);
    }
    
    private void initSounds() {
        matchClips = new ArrayList<>();
        for(String filename : new String [] {"its-a-match", "well-done-ryan", "yee-haa-little-buddy", "you-rock"}) {
            URL resource = AudioClip.class.getResource("/" + filename + ".wav");
            AudioClip audioClip = new AudioClip(resource.toString());
            matchClips.add(audioClip);
        }

    }
}
