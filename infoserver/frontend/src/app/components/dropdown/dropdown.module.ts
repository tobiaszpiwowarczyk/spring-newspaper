import { CommonModule } from '@angular/common';
import { DropdownOptionComponent } from './dropdown-option/dropdown-option.component';
import { DropdownComponent } from './dropdown/dropdown.component';
import { NgModule } from '@angular/core';

@NgModule({
  exports: [
    DropdownComponent,
    DropdownOptionComponent
  ],
  imports: [
    CommonModule
  ],
  declarations: [
    DropdownComponent,
    DropdownOptionComponent
  ]
})
export class DropdownModule {}