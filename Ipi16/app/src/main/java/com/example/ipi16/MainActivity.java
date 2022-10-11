package com.example.ipi16;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.name);
//        DateTimeFormatter dtf = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        }
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.systemDefault());
//        }
//        SimpleDateFormat dateTime = new SimpleDateFormat();
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//            String formattedDate = dateTime.format(dtf);
//            String string = formattedDate;
//            String[] parts = string.split(",", 2);
//            String part1 = parts[0]; // time00：00：00
//            String part2 = parts[1]; // 日月年2022/08/23
//            String Time[] = part1.split(":");
//            textView.setText(part1+part2);
//        }else{
//            SimpleDateFormat SDFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//            SDFormat.format(new Date());
//            String formattedDate = dateTime.format(SDFormat.format(new Date()));
//            String string = formattedDate;
//            String[] parts = string.split(",", 2);
//            String part1 = parts[0]; // time00：00：00
//            String part2 = parts[1]; // 日月年2022/08/23
//            String Time[] = part1.split(":");
//            textView.setText(part1+part2);
//        }

//        DateFormat inputFormat = null;
//        DateFormat outputFormat = new SimpleDateFormat("MM/yyyy", Locale.JAPAN);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//             inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.JAPAN);
//        }
//        String inputText = "2012-11-17T00:00:00.000-05:00";
//        Date date = new Date(inputFormat.parse(inputText));
//        try {
//            date =  inputFormat.parse(inputText);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String outputText = outputFormat.format(date);
//        String dat="2022/09/24 21:51";
//        String MonthYear = null;
////        SimpleDateFormat date=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        SimpleDateFormat date=new SimpleDateFormat("mm/yyyy");
////        Date dbDate=new Date(date);
//        MonthYear = date.format(dat);
//        DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH);
//        System.out.println(formatter.format(parser.parse( "2018-07-09")));
//        textView.setText(outputText);
//        DateTimeFormatter dateFormatter = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            dateFormatter = DateTimeFormatter
//                    .ofLocalizedDate(FormatStyle.FULL)
//                    .withLocale(Locale.US);
//        }
//        LocalDate date = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            date = LocalDate.parse("2018-07-09");
//        }
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            String formattedDate = date.format(dateFormatter);
//            textView.setText(formattedDate);
//        }
        LocalDate today = LocalDate.now();
        Calendar cl = Calendar.getInstance();

        //SimpleDateFormatクラスでフォーマットパターンを設定する
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd,HH:mm:ss");
        textView.setText(sdf.format(cl.getTime()));


//        long epochMilli = 1598336543358L;
//        DateTimeFormatter GENERAL_TZ_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm:ss z");
//
//        Instant instant = Instant.ofEpochMilli(epochMilli);
//        ZonedDateTime dateTime = instant.atZone(ZoneId.systemDefault());
//        String value = sdf.format(dateTime);
//        // or:  dateTime.format(GENERAL_TZ_FORMATTER)
////        System.out.println(value);
//        textView.setText(value);
    }
//    private String getCurrentDateTime() {
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            Log.d("TAG", "getCurrentDateTime: greater than O");
//            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("h:m a"));
//        } else{
//            Log.d("TAG", "getCurrentDateTime: less than O");
//            SimpleDateFormat SDFormat = new SimpleDateFormat("h:m a");
////            return SDFormat.format(new Date());
//        }
//    }
}