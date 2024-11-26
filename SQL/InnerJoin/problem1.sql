SELECT student.id, student.student_name FROM student INNER JOIN
class WHERE class.teacher_name = 'Ms. Lovelace' AND class.class_title = student.class_title;