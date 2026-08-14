public class StudentService {

    private final StudentRepository studentRepository; // Stores the repository that this service will use. Also final because once it receives its repo it should keep using the same repo.
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository; // Saves the repository received from Main.
    }

    public Student addStudent(String name, int age, int studentId, double grade) {
        if (name == null || name.trim().isEmpty()) { // Rejects null, empty text, and spaces only.
            throw new IllegalArgumentException("Student name cannot be empty.");
        }

        if (!name.matches("[a-zA-Z ]+")) { // Rejects numbers and special characters in the name.
            throw new IllegalArgumentException("Student name must contain letters only.");
        }

        if (age < 4 || age > 61) { // Applies the project's accepted age range.
            throw new IllegalArgumentException("Student age must be between 4 and 61.");
        }

        if (studentId <= 0) { // Prevents zero and negative IDs.
            throw new IllegalArgumentException("Student ID must be greater than 0.");
        }

        if (studentRepository.existsById(studentId)) { // Asks the repository whether this ID is already stored.
            throw new IllegalArgumentException("Student ID already exists.");
        }

        if (grade < 0 || grade > 100) { // Applies the valid grade range.
            throw new IllegalArgumentException("Student grade must be between 0 and 100.");
        }

        Student student = new Student(name, age, studentId, grade); // Creates the Student only after every rule passes.
        studentRepository.add(student); // Sends the valid Student to the repository for storage.
        return student; // Returns the newly created Student to the future controller.
    }
}
