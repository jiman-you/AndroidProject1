package com.hansung.android.androidproject1;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

/**
 * A simple {@link WeekViewFragment} subclass.
 * Use the {@link WeekViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekViewFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    String data_d;
    int data_st;
    int data_year;
    int data_month;
    int data_day;
    int data_hour;
    int data_minute;

    public WeekViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeekViewFragment newInstance(String param1, String param2) {
        WeekViewFragment fragment = new WeekViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //fragment_week_view를 레이아웃 설정.
        View rootView = inflater.inflate(R.layout.fragment_week_view, container, false);
        GridView day_of_the_week = rootView.findViewById(R.id.day_of_the_week_week);
        //요일생성.
        final String[] dayOfTheWeek = new String[]{"일", "월", "화", "수", "목", "금", "토"};
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.day_of_the_week,
                dayOfTheWeek);
        day_of_the_week.setAdapter(dayAdapter);

        //화면 전환을 위해 ViewPager2를 사용함.
        ViewPager2 vpPager = rootView.findViewById(R.id.week_vp2);
        FragmentStateAdapter adapter = new WeekCalendarAdapter(getActivity());
        vpPager.setAdapter(adapter);
        //초기 position값 설정.
        vpPager.setCurrentItem(50);

        //WeekCalendarAdapter에서 데이터 가져옴.
        int year = ((WeekCalendarAdapter) adapter).year;
        int month = ((WeekCalendarAdapter) adapter).month;
        int date = ((WeekCalendarAdapter) adapter).date;
        int dayOfWeek = ((WeekCalendarAdapter) adapter).dayOfWeek;

        //앱바 타이틀 설정.
        ((MonthViewActivity)getActivity()).getSupportActionBar().setTitle(year + "년 " + (month+1) + "월 " );
        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                int y,m,d,fd;
                Calendar cal = Calendar.getInstance();
                cal.set(year,month,date+((position-50)*7)-dayOfWeek);
                y = cal.get(Calendar.YEAR);
                m = cal.get(Calendar.MONTH);
                ((MonthViewActivity) getActivity()).getSupportActionBar().setTitle(y + "년 " + (m+1) + "월 " );
            }
        });
        //플로팅 액션 바
        FloatingActionButton fab = rootView.findViewById(R.id.flbtn2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //값 전달
                data_d = ((MonthViewActivity) getActivity()).mainDate;
                data_year = ((MonthViewActivity) getActivity()).mainYear;
                data_month = ((MonthViewActivity) getActivity()).mainMonth;
                data_day = ((MonthViewActivity) getActivity()).mainDay;
                data_hour = ((MonthViewActivity) getActivity()).mainHour;
                data_minute = ((MonthViewActivity) getActivity()).mainMinute;
                data_st = ((MonthViewActivity) getActivity()).mainStartTime;

                Intent intent = new Intent(getActivity(), NewSchedule.class);
                intent.putExtra("date", data_d);
                intent.putExtra("startTime",data_st);
                intent.putExtra("date", data_d);
                intent.putExtra("year", data_year);
                intent.putExtra("month", data_month);
                intent.putExtra("day", data_day);
                intent.putExtra("hour", data_hour);
                intent.putExtra("minute", data_minute);

                startActivity(intent);
            }
        });
        return rootView;
    }
}