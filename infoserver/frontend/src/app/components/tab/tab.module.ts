import { TabContentItemComponent } from './tab-content-item/tab-content-item.component';
import { TabContentComponent } from './tab-content/tab-content.component';
import { TabHeaderItemComponent } from './tab-header-item/tab-header-item.component';
import { TabHeaderComponent } from './tab-header/tab-header.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    TabHeaderComponent,
    TabHeaderItemComponent,
    TabContentComponent,
    TabContentItemComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    TabHeaderComponent,
    TabHeaderItemComponent,
    TabContentComponent,
    TabContentItemComponent
  ]
})
export class TabModule {}