package action.com;

import org.apache.log4j.Logger;

/**
 * Created by geyao on 2017/2/19.
 */
public class LogInAction {

    public static Logger logger = Logger.getLogger(LogInAction.class.getName());

    public String execute() throws Exception {
        System.out.println(1);

        logger.error("This is info message.");
        System.out.println(2);
        return "success";
    }
}
