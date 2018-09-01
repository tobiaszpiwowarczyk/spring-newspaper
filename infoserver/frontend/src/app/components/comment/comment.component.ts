import { LoaderComponent } from './../loader/loader.component';
import { Validators } from '@angular/forms';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ArticleComment } from './../../pages/article/ArticleComment';
import { Component, OnInit, Input, ViewChild, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss'],
  providers: []
})
export class CommentComponent implements OnInit {

  editCommentForm: FormGroup;

  @Input() comment: ArticleComment;
  @Input() isLast: boolean = false;
  @Input() isOwner: boolean = false;

  @Output() onDelete: EventEmitter<ArticleComment> = new EventEmitter<ArticleComment>();
  @Output() onSave: EventEmitter<ArticleComment> = new EventEmitter<ArticleComment>();

  @ViewChild("commentLoader") commentLoader: LoaderComponent;

  editable: boolean = false;




  constructor(
    private fb: FormBuilder
  ) {}
  ngOnInit() {
    this.editCommentForm = this.fb.group({
      content: [this.comment.content, Validators.required]
    });
  }




  public edit() {
    this.editable = true;
  }
  
  public save() {
    this.comment.content = this.editCommentForm.controls.content.value;
    this.onSave.emit(this.comment);
    this.editable = false;
  }

  public cancel() {
    this.editCommentForm.controls["content"].setValue(this.comment.content);
    this.editable = false;
  }



  public delete() {
    this.onDelete.emit(this.comment);
  }

}
