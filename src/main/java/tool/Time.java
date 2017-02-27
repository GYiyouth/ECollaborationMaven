package tool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by GR on 2017/2/27.
 */
public class Time {

    /**
     * 获取当前时间
     * 格式：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public String getYear(){
        Date date = new Date();
        String year = String.format("%tY", date);
        return year;
    }

    public String getMonth() {
        Date date = new Date();
        String month = String.format("%tm", date);
        return month;
    }

    public String getDay() {
        Date date = new Date();
        String day = String.format("%td", date);
        return day;
    }

    public String getHour() {
        Date date = new Date();
        String hour = String.format("%tH", date);
        return hour;
    }

    public String getMinute() {
        Date date = new Date();
        String minute = String.format("%tM", date);
        return minute;
    }

    public String getDateStr() {

        Time time = new Time();
        return (time.getYear()+time.getMonth()+ time.getDay());

    }

    public Date getDate() {
        return new Date();
    }

    public String getDeadTime() {
        String year = getYear();
        int deadYear = Integer.parseInt(year) + 10;
        return (deadYear + "-" + getMonth() + "-" + getDay() + " " +  getHour() + ":" + getMinute() + ":" + getSecond());
    }

    public String getSecond() {
        Date date = new Date();
        String second = String.format("%tS", date);
        return second;
    }

    public String getTime() {
//		Date date = new Date();
//		String time = String.format("%tY-%tm-%td %tH:%tM:%tS", date,date,date,date,date,date);
//		return time;
        return (getYear() + "-" + getMonth() + "-" + getDay() + " " +  getHour() + ":" + getMinute() + ":" + getSecond());
    }


}
