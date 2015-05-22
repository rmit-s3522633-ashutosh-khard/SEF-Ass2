
public class Student {

	private String studentId;
	private CourseOffering courseOffering;
	private Lecture lecture;
	private Tutorial tutorial;

	public Student(String studentId) {
		super();
		this.studentId = studentId;
	}
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public CourseOffering getCourseOffering() {
		return courseOffering;
	}
	public void setCourseOffering(CourseOffering courseOffering) {
		this.courseOffering = courseOffering;
	}
	public Lecture getLecture() {
		return lecture;
	}
	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}
	public Tutorial getTutorial() {
		return tutorial;
	}
	public void setTutorial(Tutorial tutorial) {
		this.tutorial = tutorial;
	}
	
	public void enroll(CourseOffering co) throws CourseFilledException, AlreadyEnrolledException{
		if(co.getStudents().size()>=co.getExpectedStNo())
			throw new CourseFilledException("Course is filled.");
		for (Student student : co.getStudents()) {
			if(student.getStudentId().equals(this.studentId))
				throw new AlreadyEnrolledException("You are already enrolled into "+co.getCourse().getId());	
		}
		this.setCourseOffering(co);
		co.getStudents().add(this);
	}
	
}
