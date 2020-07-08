import {Component, OnInit} from '@angular/core';
import {DataService} from "../data/data.service";
import {DataSource} from "@angular/cdk/collections";
import {Observable} from "rxjs";
import {Post} from "../Post";

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {

  constructor(private dataService: DataService) {
  }

  ngOnInit() {
  }

  displayedColumns = ['date_posted', 'title', 'category', 'delete'];
  dataSource = new PostDataSource(this.dataService);
}

export class PostDataSource extends DataSource<any> {
  constructor(private dataService: DataService) {
    super();
  }

  connect(): Observable<Post[]> {
    return this.dataService.getData();
  }

  disconnect(): void {
  }


}
