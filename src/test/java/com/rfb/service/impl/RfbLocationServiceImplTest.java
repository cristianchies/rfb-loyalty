package com.rfb.service.impl;

import com.rfb.domain.RfbEvent;
import com.rfb.domain.RfbEventAttendance;
import com.rfb.domain.RfbLocation;
import com.rfb.domain.User;
import com.rfb.service.dto.location.RfbLeaderForLocationDTO;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
//@ContextConfiguration(classes = { RfbLocationServiceImpl.class })
//@SpringBootTest(classes = RfbloyaltyApp.class)
public class RfbLocationServiceImplTest {

    @Spy
    private RfbLocationServiceImpl service;

    @Test
    public void test_getRfbLeaderForLocation() {


        Long locationId = 1L;

        User firstUser = new User();
        firstUser.setId(1L);
        firstUser.setFirstName("Cristian");
        firstUser.setLastName("Chies");

        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setFirstName("Johny");
        secondUser.setLastName("Jones");

        RfbLocation location = new RfbLocation();
        Set<RfbEvent> events = Sets.newHashSet();
        RfbEvent event = new RfbEvent();
        Set<RfbEventAttendance> eventAttSet = Sets.newHashSet();

        RfbEventAttendance attnd = new RfbEventAttendance();
        attnd.setUser(firstUser);
        eventAttSet.add(attnd);
        attnd = new RfbEventAttendance();
        attnd.setUser(secondUser);
        eventAttSet.add(attnd);
        attnd = new RfbEventAttendance();
        attnd.setUser(secondUser);// adding 2 counts for second user
        eventAttSet.add(attnd);
        event.setRfbEventAttendances(eventAttSet);
        events.add(event);

        location.setRvbEvents(events);

        Mockito.doReturn(location).when(service).getOneLocation(locationId);
        List<RfbLeaderForLocationDTO> actual = service.getRfbLeaderForLocation(locationId);

        assertNotNull(actual);
        assertTrue(actual.size() > 0);
        // as it must be sorted by 'running count' the first position must have the highest running count
        RfbLeaderForLocationDTO actualData = actual.get(0);
        assertEquals(2, actualData.getTotalRuns());
        assertEquals(secondUser.getLastName() + ", " + secondUser.getFirstName(), actualData.getUserName());
    }

}
