
public class Tutorial {

	private String tutorialId;
	private String day;
	private String startTime;
	private String endTime;
	private Tutor tutor;
	
	public Tutorial(String tutorialId) {
		this.tutorialId = tutorialId;
	}
	
	public String getTutorialId() {
		return tutorialId;
	}
	public void setTutorialId(String tutorialId) {
		this.tutorialId = tutorialId;
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
	public Tutor getTutor() {
		return tutor;
	}
	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	@Override
	public String toString() {
		return "Tutorial [tutorialId=" + tutorialId + ", day=" + day
				+ ", startTime=" + startTime + ", endTime=" + endTime +"]";
	}
}
