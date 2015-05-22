
public class TutorApplicant{
	
	private String tempId;
	private String day;
	private String startTime;
	private String endTime;
	
	
	public TutorApplicant(String tempId) {
		super();
		this.tempId = tempId;
	}
	
	public String getTempId() {
		return tempId;
	}
	public void setTempId(String tempId) {
		this.tempId = tempId;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
