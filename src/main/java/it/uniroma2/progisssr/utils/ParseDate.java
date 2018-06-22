package it.uniroma2.progisssr.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ParseDate {
    public ParseDate() {
    }

    public static GregorianCalendar parseGregorianCalendar(String data){

        String[] splitStr = data.split("[/ :-]");


        int day = Integer.parseInt(splitStr[0]);
        int month = Integer.parseInt(splitStr[1]);
        int year = Integer.parseInt(splitStr[2]);
        int hour = Integer.parseInt(splitStr[3]);
        int minute = Integer.parseInt(splitStr[4]);
        int second = Integer.parseInt(splitStr[5]);

        if(month==0)
            return new GregorianCalendar(year,Calendar.JANUARY,day,hour,minute,second);
        else if(month==1)
            return new GregorianCalendar(year,Calendar.FEBRUARY,day,hour,minute,second);
        else if(month==2)
            return new GregorianCalendar(year,Calendar.MARCH,day,hour,minute,second);
        else if(month==3)
            return new GregorianCalendar(year,Calendar.APRIL,day,hour,minute,second);
        else if(month==4)
            return new GregorianCalendar(year,Calendar.MAY,day,hour,minute,second);
        else if(month==5)
            return new GregorianCalendar(year,Calendar.JUNE,day,hour,minute,second);
        else if(month==6)
            return new GregorianCalendar(year,Calendar.JULY,day,hour,minute,second);
        else if(month==7)
            return new GregorianCalendar(year,Calendar.AUGUST,day,hour,minute,second);
        else if(month==8)
            return new GregorianCalendar(year,Calendar.SEPTEMBER,day,hour,minute,second);
        else if(month==9)
            return new GregorianCalendar(year,Calendar.OCTOBER,day,hour,minute,second);
        else if(month==10)
            return new GregorianCalendar(year,Calendar.NOVEMBER,day,hour,minute,second);
        else
            return new GregorianCalendar(year,Calendar.DECEMBER,day,hour,minute,second);

    }

    public static void main(String[] args){
        GregorianCalendar d = ParseDate.parseGregorianCalendar("07:05:2018 17:50:23");
        GregorianCalendar d2 = ParseDate.parseGregorianCalendar("7/5/2018 16:19:23");


        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar now = Calendar.getInstance();
        System.out.println("tcuvyibu"+ now.get(Calendar.DATE) + now.toString());
        System.out.println("\n------------------------\n"+ d.toString());



        Long i = (now.getTimeInMillis() - d.getTimeInMillis()) / (1000*3600);
        System.out.println("tempo"+ i);
        System.out.println("data inizio"+d.get(Calendar.DATE) + d.get(Calendar.MONTH) +d.get(Calendar.YEAR));
        System.out.println("data inizio"+d2.get(Calendar.DATE) + d2.get(Calendar.MONTH) +d.get(Calendar.YEAR));
        String n = now.get(Calendar.DATE)+ "-"+ now.get(Calendar.MONTH)+ "-"+now.get(Calendar.YEAR);
        System.out.println("nnnnnnnnnnnnn " +n );

        now.add(Calendar.DATE, 9);
        String ng = now.get(Calendar.DATE)+ "-"+ now.get(Calendar.MONTH)+ "-"+now.get(Calendar.YEAR);
        System.out.println("nnnnnnnnnnnnn " +ng );

    }

}
