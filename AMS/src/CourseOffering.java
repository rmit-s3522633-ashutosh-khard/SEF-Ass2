import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseOffering {
	private Lecture lecture;
	private int expectedStNo;
	private Course course;
	private int year;
	private int semester;
	private List<TutorApplicant> applicants = new ArrayList<TutorApplicant>();
	private List<Tutor> tutors = new ArrayList<Tutor>();
	private List<Student> students = new ArrayList<Student>();
	private Map<String,Tutorial> tutorials = new HashMap<String,Tutorial>();

	public CourseOffering(int expectedStNo, int year, int sem) {
		this.expectedStNo = expectedStNo;
		String key = "" + year + ":" + sem;
		this.year = year;
		this.semester = sem;
		lecture = null;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Course getCourse() {
		return course;
	}

	public void assignLecture(int lectureDay, double lectureStartHr,
			double lectureDur, Venue venue) throws ClashException,
			PreExistException {
		if (lecture != null)
			throw new PreExistException("Lecuture already exist");
		lecture = new Lecture(lectureDay, lectureStartHr, lectureDur, venue);
	}

	public Lecture getLecture() {
		return lecture;
	}

	public List<TutorApplicant> getApplicants() {
		return applicants;
	}

	public void setApplicants(List<TutorApplicant> applicants) {
		this.applicants = applicants;
	}

	public Map<String, Tutorial> getTutorials() {
		return tutorials;
	}

	public void setTutorials(Map<String, Tutorial> tutorials) {
		this.tutorials = tutorials;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Tutor> getTutors() {
		return tutors;
	}

	public void setTutors(List<Tutor> tutors) {
		this.tutors = tutors;
	}

	public int getExpectedStNo() {
		return expectedStNo;
	}

	public void setExpectedStNo(int expectedStNo) {
		this.expectedStNo = expectedStNo;
	}

	public String toString() {
		String s = "";
		if (course != null) {
			s = "Id = " + course.getId();
			s += "\nName = " + course.getName();
		}
		s += "\nYear = : " + year + " Semester : " + semester;
		s += "\nExpected student number " + expectedStNo;
		if (lecture != null)
			s += "\n" + lecture.toString();
		return s;
	}
}