package threads;
import functions.Function;
import functions.basic.*;

public class Task {
    public Function f;
    public double leftX;
    public double rightX;
    public double dx;
    public int countOfTasks;
    public Task (int count) {
        this.countOfTasks=count;
    }
    public Task(){
        f=new Log();
        leftX=0;
        rightX=0;
        dx=1;
        countOfTasks=1;
    }

}
