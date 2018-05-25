import {Component, OnInit} from '@angular/core';
import {RfbEventAttendanceService} from '../entities/rfb-event-attendance';
import {Principal, ResponseWrapper, UserService} from '../shared';
import {RfbLocation, RfbLocationService} from '../entities/rfb-location';
import {JhiAlertService} from 'ng-jhipster';
import {log} from "util";
import {forEach} from "@angular/router/src/utils/collection";

@Component({
    selector: 'jhi-leaderboard-component',
    templateUrl: './leaderboard-component.component.html',
    styles: []
})
export class LeaderboardComponentComponent implements OnInit {

    actualLocationId: number;
    actualLocation: RfbLocation;
    locationList: RfbLocation[];

    constructor(
        // private principal: Principal,
        // private account: Account,
        // private userService: UserService,
        private locationService: RfbLocationService,
        private evAttdnceService: RfbEventAttendanceService,
        private alertService: JhiAlertService
    ) {
    }

    ngOnInit() {
        this.loadAllLocations();
        // this.principal.identity().then((account) => {
        //     this.account = account;
        //     this.userService.find(this.principal.identity()).subscribe((user) => {
        //         this.user = user;
        //         console.log(this.user[0]);
        //     });
        // });
    }

    loadAllLocations() {
        this.locationService.query({
            sort: ['locationName', 'ASC']
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    onSelect() {
        this.locationService.find(this.actualLocationId).subscribe(
            (location: RfbLocation) => {
                this.actualLocation = location;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    private onSuccess(data, headers) {
        this.locationList = data;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}
