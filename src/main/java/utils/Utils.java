package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.restassured.RestAssured;

public class Utils {

	//Get a date with difference of days. Used to get a future or past date
	public static String getDateWithDaysDifference(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		return formatDate(cal.getTime());
	}
	
	public static String formatDate(Date date) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(date);
	}
	
	public static int getIdAccountByName(String name) {
		return RestAssured.get("/contas?nome="+name).then().extract().path("id[0]");
	}
	
	public static int getIdTransactionByDesc(String desc) {
		return RestAssured.get("/transacoes?descricao"+desc).then().extract().path("id[0]");
	}
	
}
