/* Start up code for SEF Student Management System 2015 semester 1.
 * This code should be used for your initial class diagram.
 * You are free to adapt or completely change the code and design for the
 * extended class diagram and implementation as long as your design allows 
 * for all the requirements and test cases.
 *  
 */

import java.util.*;

public class App {
	HashMap<String, Course> courses = new HashMap<String, Course>();
	HashMap<String, Venue> venues = new HashMap<String, Venue>();
	HashMap<String, Lecturer> lecturers = new HashMap<String, Lecturer>();
	HashMap<String, Student> students = new HashMap<String, Student>();
	Scanner scan = new Scanner(System.in);
	private int year = 2015;
	private int semester = 1;

	public static void main(String[] args) {
		App a = new App();
		String options[] = { "Add Offering", "Add Lecture", "Add Lecturer",
				"Lecturer TimeTable", "Venue TimeTable", "Tutor Application","Add tutorial","Assign Tutor","Enroll Student"
				,"View Menu" };
		Menu m = new Menu("Main Menu", options);
		int resp;
		do {
			if ((resp = m.getResponse()) == 0)
				break;
			switch (resp) {
			case 1:
				a.handleCreateOffering();
				break;
			case 2:
				a.handleAddLecture();
				break;
			case 3:
				a.handleAssignLecturer();
				break;
			case 4:
				a.printLecturerTimetable();
				break;
			case 5:
				a.printVenueTimetable();
				break;
			case 6:
				a.handleTutorApplication();
				break;
			case 7:
				a.addTutorial();
				break;
			case 8:
				a.assignTutor();
				break;
			case 9:
				a.enrollStudent();
				break;
			case 10:
				String options2[] = { "View Courses", "View Lecturers",
						"View Venues" ,"View Tutorials"};
				Menu sub = new Menu("View Submenu", options2);
				int n = 0;
				while ((n = sub.getResponse()) != 0)
					a.view(n);
				break;
			}
		} while (true);
	}

	private void enrollStudent() {
		System.out.print("Enter Student ID : ");
		String studentId = scan.nextLine();
		Student student = students.get(studentId);
		
		if(student == null){
			System.out.println("Invalid StudentId!");
		}else{
			displayStudentEnrollmentSubMenu(student);
		}
	}

	private void displayStudentEnrollmentSubMenu(Student student){
		String options[] = { "Enroll In a Course","Register Lecture","Register Tutorial" };
		Menu m = new Menu("Student Enrollment Menu", options);
		int resp;
		CourseOffering courseOffering = null;
		do {
			if ((resp = m.getResponse()) == 0)
				break;
			switch (resp) {
			case 1:
				//Enroll In a course
				System.out.print("Enter Course ID : ");
				String courseID = scan.nextLine();
				Course course = getCourse(courseID);

				CourseOffering co;
				if (course == null) {
					System.out.println("Course ID is invalid");
					hold();
				} else {
					co = course.getOffering(year, semester);
					if (co == null) {
						System.out.println("This Course is not offered in this semester.");
						hold();
						return;
					} else {
						try {
							student.enroll(co);
						} catch (CourseFilledException e) {
							System.out.println(e.getMessage());
						}catch (AlreadyEnrolledException e) {
							System.out.println(e.getMessage());
						}		
					}
				}	
				break;
			case 2://  For registering a lecture
				courseOffering = student.getCourseOffering();
				if(courseOffering == null){
					System.out.println("Please enroll into a course first!");
					return;
				}else{
					if(null != student.getLecture())
						System.out.println("It seems that you are already enrolled into this Course Lecture.");
					else{
						if(null != courseOffering.getLecture()){
							student.setLecture(courseOffering.getLecture());
							System.out.println(student.getStudentId() +" is successfully enrolled into Lecture for "+
									courseOffering.getCourse().getId());	
						}else
							System.out.println(courseOffering.getCourse().getId() + " has not got any Lecture assigned to it.");
						
					}
				}
				break;
			case 3://  For registering a tutorial
				courseOffering = student.getCourseOffering();
				if(courseOffering == null){
					System.out.println("Please enroll into a course first!");
					return;
				}else{
					System.out.print("Enter Tutorial ID : ");
					String tutorialId = scan.nextLine();
					Tutorial tutorial = courseOffering.getTutorials().get(tutorialId);
					if (tutorial == null) {
						System.out.println("Tutorial ID is invalid");
						hold();
					} else {
						if(null != student.getTutorial())
							System.out.println("It seems that you are already enrolled into a Tutorial for " + 
									courseOffering.getCourse().getId()+". Please cancel the enrollment befor enrolling"
											+ " into a different Tutorial.");
						else{
							student.setTutorial(tutorial);
							System.out.println(student.getStudentId() +" is successfully enrolled into Tutorial "+tutorialId);
						}
					}
				}
				break;
			default: System.out.println("Invalid Input!");
				break;
			}
		}while(true);
		
	}
	
	private void assignTutor() {
		
		System.out.print("Enter Course ID : ");
		String courseID = scan.nextLine();
		Course course = getCourse(courseID);

		CourseOffering co;
		if (course == null) {
			System.out.println("Course ID is invalid");
			hold();
		} else {
			co = course.getOffering(year, semester);
			if (co == null) {
				System.out.println("This Course is not offered in this semester.");
				hold();
				return;
			} else {
				System.out.println("Enter the tutorialId :");
				String tutorialID = scan.nextLine();
				Tutorial tutorial = co.getTutorials().get(tutorialID);
				if(tutorial ==  null){
					System.out.println("No Tutorial found with tutorialId : "+tutorialID);
					hold();
					return;
				}else{
					List<Tutor> tutors = co.getTutors();
					boolean matched = false;
					if (tutors != null && tutors.size() > 0){
						for (Tutor tutor : tutors) {
							if(tutor.getDay().equals(tutorial.getDay())){
								if(CheckAvailabilityUtil.overlap(Double.parseDouble(tutor.getStartTime()),
										Double.parseDouble(tutor.getEndTime()), Double.parseDouble(tutorial.getStartTime()),
										Double.parseDouble(tutorial.getEndTime()))){
									tutorial.setTutor(tutor);
									System.out.println("TutorId : "+tutor.getENo()+" is assigned to the tutorial Id : "+tutorialID);
									matched = true;
									break;
								}
							}
						}
						if(!matched)
							System.out.println("No Tutors available for the Timings of the Tuetorial with id : "+tutorialID);
					}else{
						System.out.println("It seems there are no Tutors yet.");
						hold();
						return;
					}
						
				}
			}
		}
	}

	private void addTutorial() {

		System.out.print("Enter  Course ID : ");
		String courseID = scan.nextLine();
		Course course = getCourse(courseID);

		CourseOffering co;
		if (course == null) {
			System.out.println("Course ID is invalid");
			hold();
		} else {
			co = course.getOffering(year, semester);
			if (co == null) {
				System.out
						.println("This Course is not offered in this semester.");
				hold();
				return;
			} else {
				Tutorial tutorial = null;
				if(co.getTutorials() != null)
					tutorial = new Tutorial("TutID:" + (co.getTutorials().size()+1));
				else
					tutorial = new Tutorial("TutID:" + (0));
				System.out.print("Enter tutorial day : ");
				String day = scan.nextLine();
				tutorial.setDay(day);
				System.out.print("Enter start time : ");
				String startTime = scan.nextLine();
				tutorial.setStartTime(startTime);
				System.out.print("Enter end time : ");
				String endTime = scan.nextLine();
				tutorial.setEndTime(endTime);
				Map<String, Tutorial> tutorials = co.getTutorials();
				if(null == tutorials)
					tutorials = new HashMap<String, Tutorial>();
				tutorials.put(tutorial.getTutorialId(), tutorial);
				co.setTutorials(tutorials);
			}
		}
	}

	private void handleTutorApplication() {
		System.out.print("Enter  Course ID : ");
		String courseID = scan.nextLine();
		Course course = getCourse(courseID);

		CourseOffering co;
		if (course == null) {
			System.out.println("Course ID is invalid");
			hold();
		} else {
			co = course.getOffering(year, semester);

			if (co == null) {
				System.out
						.println("This Course is not offered in this semester.");
				hold();
				return;
			} else {
				TutorApplicant tutorApplicant = null;
				if(co.getApplicants() != null)
					tutorApplicant = new TutorApplicant("TempID:" + (co.getApplicants().size()+1));
				else
					tutorApplicant = new TutorApplicant("TempID:" + (1));
				System.out.print("Enter Available day : ");
				String day = scan.nextLine();
				tutorApplicant.setDay(day);
				System.out.print("Enter start time : ");
				String startTime = scan.nextLine();
				tutorApplicant.setStartTime(startTime);
				System.out.print("Enter end time : ");
				String endTime = scan.nextLine();
				tutorApplicant.setEndTime(endTime);
				List<TutorApplicant> applicants = co.getApplicants();
				if(null == applicants)
					applicants = new ArrayList<TutorApplicant>();
				applicants.add(tutorApplicant);
				co.setApplicants(applicants);
			}
		}
	}

	public App() {
		initializeCourses();
		initializeVenues();
		initializeLecturers();
		initializeStudents();
	}

	public void view(int n) {
		if (n == 1)
			displayMap(courses);
		else if (n == 2)
			displayMap(lecturers);
		else if (n == 3)
			displayMap(venues);
		else 
			displayTutorials();
	}

	private void displayTutorials() {
		System.out.print("Enter  Course ID : ");
		String courseID = scan.nextLine();
		Course course = getCourse(courseID);

		CourseOffering co;

		if (course == null) {
			System.out.println("Course ID is invalid");
			hold();
		} else {
			co = course.getOffering(year, semester);
			if (co == null) {
				System.out.println("This Course is not offered in this semester.");
				return;
			} else {
				displayMap(co.getTutorials());
			}
		}	
	}

	public void displayMap(Map m) {
		Set keySet = m.keySet();
		Iterator iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			System.out.println(key + ":" + m.get(key));
		}
		hold();
	}

	public void handleCreateOffering() {
		System.out.print("Enter  Course ID : ");
		String courseID = scan.nextLine();
		Course course = getCourse(courseID);

		CourseOffering co;
		if (course == null) {
			System.out.println("Course ID is invalid");
			hold();
		} else {
			System.out.print("Enter Expected Number : ");
			int expectedNum = scan.nextInt();
			scan.nextLine();
			try {
				createOffering(course, expectedNum, year, semester);
			} catch (PreExistException pe) {
				System.out.println(pe);
				hold();
			}
		}
	}

	public CourseOffering createOffering(Course course, int expectedNum,
			int year, int semester) throws PreExistException {
		return course.createOffering(expectedNum, year, semester);
	}

	private void hold() {
		System.out.print("Press enter to continue");
		scan.nextLine();
	}

	public void handleAddLecture() {
		System.out.print("Enter  Course ID : ");
		String courseID = scan.nextLine();
		CourseOffering courseOffering = getCourseOffering(courseID, year,
				semester);
		if (courseOffering == null) {
			System.out.println("No course offering yet");
			hold();
			return;
		}

		System.out.print("Enter  Venue Location : ");
		String location = scan.nextLine();
		Venue venue = getVenue(location);
		if (venue == null) {
			System.out.println("No such venue");
			hold();
			return;
		}

		System.out.print("Enter  Day of Lecture : ");
		int day = scan.nextInt();

		System.out.print("Enter  Start Hour : ");
		double startHour = scan.nextInt();

		System.out.print("Enter  Duration : ");
		double duration = scan.nextInt();
		scan.nextLine();
		try {
			courseOffering.assignLecture(day, startHour, duration, venue);
			System.out.println(courseOffering);
		} catch (ClashException ce) {
			System.out.println(ce);
		} catch (PreExistException pe) {
			System.out.println(pe);
		}

	}

	public void assignLecture(CourseOffering co, int day, double startHour,
			double duration, Venue venue) throws ClashException,
			PreExistException {
		co.assignLecture(day, startHour, duration, venue);
	}

	public void handleAssignLecturer() {
		System.out.print("Enter  Course ID : ");
		String courseID = scan.nextLine();
		CourseOffering courseOffering = getCourseOffering(courseID, year,
				semester);
		if (courseOffering == null) {
			System.out.println("No course offering yet");
			hold();
			return;
		}
		Lecture lecture = courseOffering.getLecture();
		if (lecture == null) {
			System.out
					.println("No lecture assigned to this course offering yet ");
			hold();
			return;
		}
		System.out.print("Enter  Lecturer ID : ");
		String lecID = scan.nextLine();
		Lecturer lecturer = getLecturer(lecID);
		if (lecturer == null) {
			System.out.println("No lecturer with such ID ");
			hold();
			return;
		}
		try {
			assignLecturer(lecture, lecturer);
		} catch (ClashException ce) {
			System.out.println(ce);
		} catch (PreExistException pe) {
			System.out.println(pe);
		}
	}

	public void assignLecturer(Lecture lecture, Lecturer lecturer)
			throws ClashException, PreExistException {
		lecturer.assign(lecture);
	}

	public void printLecturerTimetable() {
		System.out.print("Enter  Lecturer ID : ");
		String lecturerID = scan.nextLine();
		Lecturer lecturer = getLecturer(lecturerID);
		if (lecturer == null) {
			System.out.println("No lecturer with this ID");
			return;
		}
		ArrayList<Lecture> lectures = getLectures(lecturer);
		if (lectures == null)
			return;
		for (int i = 0; i < lectures.size(); i++)
			System.out.println(lectures.get(i));
	}

	public void printVenueTimetable() {
		System.out.print("Enter  Venue Location : ");
		String location = scan.nextLine();
		Venue venue = getVenue(location);
		if (venue == null) {
			System.out.println("No Venue at this location");
			return;
		}
		ArrayList<Lesson> lessons = getLessons(venue);
		if (lessons == null)
			return;
		for (int i = 0; i < lessons.size(); i++)
			System.out.println(lessons.get(i));
	}

	public void initializeCourses() {
		Course course1 = new Course("P101", "Programming 1",
				"Teach Basic Programming");
		Course course2 = new Course("P102", "Programming 2",
				"Teach Intermediate Programming");
		Course course3 = new Course("S101", "Software Engineering",
				"Teach UML and Modelling");
		Course course4 = new Course("WP1", "Web Programming",
				"Teach Web Technologies");
		Course course5 = new Course("UI1", "User Interface",
				"Teach UI Principles");
		Course course6 = new Course("Math", "Discret Maths",
				"Teach Maths needed for CS");
		Course course7 = new Course("Net1", "Networkins",
				"Teach networking principles");

		course3.addPrereq(course1);
		course2.addPrereq(course1);
		course7.addPrereq(course2);
		course7.addPrereq(course6);

		courses.put("P101", course1);
		courses.put("P102", course2);
		courses.put("S101", course3);
		courses.put("WP1", course4);
		courses.put("UI1", course5);
		courses.put("Math", course6);
		courses.put("Net1", course7);

	}

	public void initializeVenues() {
		venues.put("12.10.02", new Venue("12.10.02", 120, "Lecture"));
		venues.put("12.10.03", new Venue("12.10.03", 200, "Lecture"));
		venues.put("10.10.22", new Venue("10.10.22", 36, "TuteLab"));
		venues.put("10.10.23", new Venue("10.10.23", 36, "TuteLab"));
	}

	public void initializeLecturers() {
		lecturers.put("e44556", new Lecturer("e44556", "Tim O'Connor",
				"Lecturer", "14.13.12"));
		lecturers.put("e44321", new Lecturer("e44321", "Richard Cooper",
				"Professor", "14.13.12"));
		lecturers.put("e54321", new Lecturer("e54321", "Jane Smith",
				"Lecturer", "11.9.10"));
	}

	public void initializeStudents(){
		students.put("s3525075", new Student("s3525075"));
		students.put("s3522633", new Student("s3522633"));
		students.put("s3524248", new Student("s3524248"));
		students.put("s3515746", new Student("s3515746"));
	}
	
	public ArrayList<Lecture> getLectures(Lecturer lecturer) {
		return lecturer.getLectures();
	}

	public ArrayList<Lesson> getLessons(Venue venue) {
		return venue.getLessons();
	}

	public Lecturer getLecturer(String eNo) {
		return lecturers.get(eNo);
	}

	public Venue getVenue(String location) {
		return venues.get(location);
	}

	public Course getCourse(String ID) {
		return courses.get(ID);
	}

	public Lecture getLecture(CourseOffering offering) {
		return offering.getLecture();
	}

	public CourseOffering getCourseOffering(String ID, int year, int sem) {
		Course c = courses.get(ID);
		if (c == null)
			return null;
		return c.getOffering(year, semester);
	}
}