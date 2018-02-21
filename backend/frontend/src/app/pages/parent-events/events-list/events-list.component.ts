import { Component, OnInit, Input } from '@angular/core';
import { Event } from '../../../interfaces/event.interface';

@Component({
  selector: 'app-events-list',
  templateUrl: './events-list.component.html',
  styleUrls: ['./events-list.component.css']
})
export class EventsListComponent implements OnInit {

  @Input() events: Event[];
  activePage: number = 1;
  eventsPerPage: number = 2;
  pagesList: number[] = [];
  visibleEvents: Event[];
  constructor() { }


  ngOnInit() {
    for(var i = 0; i<=this.events.length/this.eventsPerPage; i++){
      this.pagesList.push(i+1);
    }
    this.changePage(this.activePage);
  }

  changePage(current: number): void{
    if (current==0) current = 1;
    else if (current==this.pagesList.length+1) current = this.pagesList.length;
    this.activePage = current;
    this.visibleEvents = this.events.slice((current-1)*this.eventsPerPage, current*this.eventsPerPage);
  }

}