package vn.edu.hust.studentman

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
  private lateinit var studentAdapter: StudentAdapter
  private val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004"),
    StudentModel("Đỗ Minh Đức", "SV005"),
    StudentModel("Vũ Thị Hoa", "SV006"),
    StudentModel("Hoàng Văn Hải", "SV007"),
    StudentModel("Bùi Thị Hạnh", "SV008"),
    StudentModel("Đinh Văn Hùng", "SV009"),
    StudentModel("Nguyễn Thị Linh", "SV010"),
    StudentModel("Phạm Văn Long", "SV011"),
    StudentModel("Trần Thị Mai", "SV012"),
    StudentModel("Lê Thị Ngọc", "SV013"),
    StudentModel("Vũ Văn Nam", "SV014"),
    StudentModel("Hoàng Thị Phương", "SV015"),
    StudentModel("Đỗ Văn Quân", "SV016"),
    StudentModel("Nguyễn Thị Thu", "SV017"),
    StudentModel("Trần Văn Tài", "SV018"),
    StudentModel("Phạm Thị Tuyết", "SV019"),
    StudentModel("Lê Văn Vũ", "SV020")
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    studentAdapter = StudentAdapter(students, ::editStudent, ::deleteStudent)

    findViewById<RecyclerView>(R.id.recycler_view_students).apply {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      showAddStudentDialog()
    }
  }

  private fun showAddStudentDialog() {
    val dialogView = layoutInflater.inflate(R.layout.dialog_student, null)
    val studentNameInput = dialogView.findViewById<TextInputEditText>(R.id.edit_student_name)
    val studentIdInput = dialogView.findViewById<TextInputEditText>(R.id.edit_student_id)

    AlertDialog.Builder(this)
      .setTitle("Add New Student")
      .setView(dialogView)
      .setPositiveButton("Add") { _, _ ->
        val name = studentNameInput.text.toString()
        val id = studentIdInput.text.toString()
        if (name.isNotEmpty() && id.isNotEmpty()) {
          val newStudent = StudentModel(name, id)
          students.add(newStudent)
          studentAdapter.notifyItemInserted(students.size - 1)
        }
      }
      .setNegativeButton("Cancel", null)
      .show()
  }

  private fun editStudent(position: Int) {
    val student = students[position]
    val dialogView = layoutInflater.inflate(R.layout.dialog_student, null)
    val studentNameInput = dialogView.findViewById<TextInputEditText>(R.id.edit_student_name)
    val studentIdInput = dialogView.findViewById<TextInputEditText>(R.id.edit_student_id)

    studentNameInput.setText(student.studentName)
    studentIdInput.setText(student.studentId)

    AlertDialog.Builder(this)
      .setTitle("Edit Student")
      .setView(dialogView)
      .setPositiveButton("Update") { _, _ ->
        val name = studentNameInput.text.toString()
        val id = studentIdInput.text.toString()
        if (name.isNotEmpty() && id.isNotEmpty()) {
          students[position] = StudentModel(name, id)
          studentAdapter.notifyItemChanged(position)
        }
      }
      .setNegativeButton("Cancel", null)
      .show()
  }

  private fun deleteStudent(position: Int) {
    val deletedStudent = students[position]
    students.removeAt(position)
    studentAdapter.notifyItemRemoved(position)

    Snackbar.make(findViewById(R.id.recycler_view_students), "Student deleted", Snackbar.LENGTH_LONG)
      .setAction("Undo") {
        students.add(position, deletedStudent)
        studentAdapter.notifyItemInserted(position)
      }.show()
  }
}
