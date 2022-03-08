package br.com.utils;

import javax.swing.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {

	static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public static String patternUS(String data) {
		StringTokenizer st = new StringTokenizer(data, "/");

		String day = st.nextToken();
		String month = st.nextToken();
		String year = st.nextToken();

		return year + "-" + month + "-" + day;
	}

	public static String patternBR(String data) {
		StringTokenizer st = new StringTokenizer(data, "-");

		String year = st.nextToken();
		String month = st.nextToken();
		String day = st.nextToken();

		return day + "/" + month + "/" + year;
	}

	public static Date parseToUS(String dataBR) {
		String dataUs = patternUS(dataBR);
		Date date = new Date();
		try {
			date = format.parse(dataUs);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null,
					"Error converting data!\n" + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return date;
	}

	public static Date parseToBR(String dataUS) {
		String dateBr = patternBR(dataUS);
		Date data = new Date();
		try {
			data = format.parse(dateBr);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null,
					"Error converting data!\n" + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return data;
	}

	public static Integer calculateRange(Date date) {
		Integer daysRemaining = 0;

		Date today = new Date();

		while (date.before(today)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int day = 1;
			calendar.add(Calendar.DAY_OF_MONTH, day);
			date = calendar.getTime();

			daysRemaining++;
		}
		return daysRemaining;
	}

	public static String getDate(Date date) {
		return format.format(date);
	}

	public static String getDate(Date date, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	public static String getDate(String date, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		Date data = new Date();
		try {
			data = format.parse(date);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null,
					"Error converting data!\n" + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return format.format(data);
	}

	public static int calculateBetweenDates(Date dateStart, Date dateEnd) {
		long interval = getInterval(dateStart, dateEnd);
		return (int) (interval / (24 * 3600 * 1000));
	}

	public static long getInterval(Date dateStart, Date dateEnd) {
		if (dateEnd.compareTo(dateStart) < 0) {
			throw new RuntimeException("End date less than start date");
		}
		return (dateEnd.getTime() - dateStart.getTime());
	}

	public static int getIntervalHours(Date dateStart, Date dateEnd) {
		long interval = getInterval(dateStart, dateEnd);
		return (int) (interval / (3600 * 1000));
	}

	public static int getUsefulDayInterval(Date dateStart, Date dateEnd) {
		GregorianCalendar calendarInicial = new GregorianCalendar();
		calendarInicial.setTime(dateStart);

		GregorianCalendar calendarFinal = new GregorianCalendar();
		calendarFinal.setTime(dateEnd);

		return getIntervalDayUses(calendarInicial, calendarFinal);
	}

	public static int getIntervalDayUses(Calendar initialDate, Calendar finalDate) {
		return getIntervalDayUses(initialDate, finalDate, new ArrayList());
	}

	public static int getIntervalDayUses(Calendar dateStart, Calendar dateEnd, Collection holidays) {
		int dayOfWeek, dayOfMonth;
		int result = 0;
		while (dateStart.before(dateEnd) || dateStart.equals(dateEnd)) {
			dayOfWeek = dateStart.get(Calendar.DAY_OF_WEEK);
			dayOfMonth = dateStart.get(Calendar.DAY_OF_MONTH);
			switch (dayOfWeek) {
				case 2: // Segunda
				case 3: // Terça
				case 4: // Quarta
				case 5: // Quinta
				case 6: // Sexta
					if (!holidays.contains(dateStart)) result++;
					break;
				case 1: // Domingo
				case 7: // Sabado
					break;
			}
			dateStart.set(Calendar.DAY_OF_MONTH, dayOfMonth + 1);
		}
		return result;
	}

}


