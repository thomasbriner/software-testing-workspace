package ch.testing.pricing.service.workflow;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WeekendDiscountValidator {

	private Integer weekendNumber;
	private static final Log LOG = LogFactory
			.getLog(WeekendDiscountValidator.class);

	public WeekendDiscountValidator() {

	}

	public void initializeWithWeekendNumber(int weekendNumber) {
		this.weekendNumber = new Integer(weekendNumber);
	}

	/**
	 * Checks whether a date is within the nth weekend (Saturday 00:00 to Sunday
	 * 23:59) of the month. The number n has to be given to the instance before
	 * using the initializeWithWeekendNumber Method.
	 * 
	 * @param now
	 * @return
	 * @throws ValidatorNotYetInitializedException
	 */
	public boolean isAuthorizedForDiscount(Date now)
			throws ValidatorNotYetInitializedException {

		// make sure a weekend number has been set already
		if (this.weekendNumber == null) {
			throw new ValidatorNotYetInitializedException();
		// and check that the weekend number is a positive value
		} else if (this.weekendNumber < 0) {
			return false;
		} else {
			// set a calender to the given date
			Calendar nowCal = Calendar.getInstance();
			nowCal.setTime(now);

			int dayOfWeek = nowCal.get(Calendar.DAY_OF_WEEK);

			// depending on the day of the week of the given date
			if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
				// if it is saturday or sunday, go on
				Calendar countSaturdaysInMonths = Calendar.getInstance();
				countSaturdaysInMonths.setTime(now);
				int nofSaturdaysUpToNow = 0;

				// now count the weekends (i.e. the saturdays in this month)
				// until month is over or the number of weekends is reached
				for (int day = 1; day <= nowCal.get(Calendar.DAY_OF_MONTH)
						&& nofSaturdaysUpToNow < this.weekendNumber; day++) {
					countSaturdaysInMonths.set(Calendar.DAY_OF_MONTH, day);

					// if it is a saturday: increment counter
					if (countSaturdaysInMonths.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
						nofSaturdaysUpToNow++;
					}
				}

				// if number of weekends is correct
				if (nofSaturdaysUpToNow == this.weekendNumber) {
					// and the given date is either the saturday or the sunday
					// of this weekend

					// is it the saturday of the correct weekend
					if (countSaturdaysInMonths.get(Calendar.DAY_OF_MONTH) == nowCal
							.get(Calendar.DAY_OF_MONTH)) {
						return true;
					}
					
					// is it the sunday of the correct weekend
					if (countSaturdaysInMonths.get(Calendar.DAY_OF_MONTH) + 1 <= nowCal
									.get(Calendar.DAY_OF_MONTH)) {
						return true;


					}
				} else {
					// otherwise we have run out of this month
					if (nofSaturdaysUpToNow < this.weekendNumber) {
						LOG.info("Not enough Saturdays for this number of weekends("
								+ this.weekendNumber + ")");
					}
				}
			}
			return false;
			
		}

	}
}
