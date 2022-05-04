import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Student } from '../student';
import { StudentService } from '../student.service';

@Component({
  selector: 'app-update-student',
  templateUrl: './update-student.component.html',
  styleUrls: ['./update-student.component.css']
})
export class UpdateStudentComponent implements OnInit {

  id:number = 0;
  student: Student= new Student();

  constructor(private studentService : StudentService,private router:Router,
    private active:ActivatedRoute) { }

  ngOnInit(): void {

    this.id = this.active.snapshot.params['id'];

    this.studentService.getStudentById(this.id).subscribe(data => {
      this.student = data;
    }, error => console.log(error));

  }

  saveStudent(){
    this.studentService.createStudent(this.student).subscribe(data=>{
      console.log(data);
      this.goToStudentList();
    },
    error => console.log(error));
  }
  
  onSubmit(){
    this.studentService.updateStudent(this.id, this.student).subscribe(data=>{
      this.goToStudentList();
    }, error => console.log(error));
  }

  goToStudentList(){
    this.router.navigate(['/students']);
    this.saveStudent();
  }

}
