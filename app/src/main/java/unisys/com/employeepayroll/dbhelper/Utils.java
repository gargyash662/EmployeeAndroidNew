package unisys.com.employeepayroll.dbhelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String getDateTimeWithoutTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static final String intern = "Intern" ;
    public static final String Fulltime = "Full Time" ;
    public static final String PartTime_Com = "Part Time Commsion Based" ;
    public static final String PartTime_Fixed = "Part Time Fixed Amount" ;
}
