package middle7;

import java.util.concurrent.Callable;

public interface ProcessSub extends Callable<String>{

    public String process();

}
