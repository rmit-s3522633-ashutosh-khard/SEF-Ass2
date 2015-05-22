public class CheckAvailabilityUtil {

	public static boolean overlap(double start1, double end1, double start2,
			double end2) {
		if (inBetween(start1, start2, end2) || inBetween(end1, start2, end2))
			return true;
		else
			return false;
	}

	private static boolean inBetween(double x, double start, double end) {
		if (x > start && x < end)
			return true;
		else
			return false;
	}
}
