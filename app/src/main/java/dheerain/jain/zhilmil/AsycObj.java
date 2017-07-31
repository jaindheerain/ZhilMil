package dheerain.jain.zhilmil;

/**
 * Created by Dheerain on 31-07-2017.
 */

public class AsycObj {

    Communicator communicator;

    void refrence(Communicator communicator)
    {
        this.communicator=communicator;
    }

    void startFunction()
    {
        communicator.doTask();
    }

}
