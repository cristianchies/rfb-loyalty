import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfbloyaltySharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import {LeaderboardComponentComponent} from "../leaderboard-component/leaderboard-component.component";

@NgModule({
    imports: [
        RfbloyaltySharedModule,
        RouterModule.forRoot([ HOME_ROUTE ], { useHash: true })
    ],
    declarations: [
        HomeComponent,
        LeaderboardComponentComponent
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RfbloyaltyHomeModule {}
