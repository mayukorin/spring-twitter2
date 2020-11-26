package com.example.demo.validator;


import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import org.springframework.stereotype.Component;



@Component
public class startAndEndDayValidator  {




	public boolean StartAndEndDayCheck(String start,String end) {


		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		try {
			Date date = dateFormat.parse(start);
			Date dateEnd = dateFormat.parse(end);

			return date.before(dateEnd);



		} catch (ParseException e) {

			return false;
		}


	}

}
