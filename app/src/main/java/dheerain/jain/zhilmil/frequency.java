package dheerain.jain.zhilmil;

/**
 * Created by Dheerain on 30-07-2017.
 */

public class frequency {
    int freq;


    boolean clicked;
    int milisec;

    public frequency(int freq, int milisec) {
        this.freq = freq;
        clicked=false;
        this.milisec = milisec;
    }

    public int getFreq() {
        return freq;
    }

    public int getMilisec() {
        return milisec;
    }
    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

}
