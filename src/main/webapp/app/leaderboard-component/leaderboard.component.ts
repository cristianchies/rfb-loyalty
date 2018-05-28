import {Component, OnInit} from '@angular/core';
import {RfbEventAttendanceService} from '../entities/rfb-event-attendance';
import {ResponseWrapper} from '../shared';
import {RfbLocation, RfbLocationService} from '../entities/rfb-location';
import {JhiAlertService} from 'ng-jhipster';
import {RfbLeaderForLocation} from './rfb-leader-for.location';

@Component({
    selector: 'jhi-leaderboard-component',
    templateUrl: './leaderboard.component.html',
    styles: []
})
export class LeaderboardComponent implements OnInit {

    actualLocationId: number;
    actualLocation: RfbLocation;
    locationList: RfbLocation[];
    leaderList: RfbLeaderForLocation[];

    constructor(
        private locationService: RfbLocationService,
        private evAttdnceService: RfbEventAttendanceService,
        private alertService: JhiAlertService
    ) {
    }

    ngOnInit() {
        this.loadAllLocations();
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
                console.log('location' + location);
                this.actualLocation = location;
                this.getRfbLeaderForLocation();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    getRfbLeaderForLocation() {
        this.locationService.getRfbLeaderForLocation(this.actualLocationId).subscribe(
            (res: ResponseWrapper) =>
                this.leaderList = res.json,
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
