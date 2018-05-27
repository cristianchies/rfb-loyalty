package com.rfb.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rfb.domain.RfbLocation;
import com.rfb.domain.User;
import com.rfb.repository.RfbLocationRepository;
import com.rfb.service.RfbLocationService;
import com.rfb.service.dto.RfbLocationDTO;
import com.rfb.service.dto.location.RfbLeaderForLocationDTO;
import com.rfb.service.mapper.RfbLocationMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * Service Implementation for managing RfbLocation.
 */
@Service
@Transactional
public class RfbLocationServiceImpl implements RfbLocationService {

    private final Logger log = LoggerFactory.getLogger(RfbLocationServiceImpl.class);

    @Autowired
    private RfbLocationRepository rfbLocationRepository;

    @Autowired
    private RfbLocationMapper rfbLocationMapper;


    /**
     * Save a rfbLocation.
     *
     * @param rfbLocationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RfbLocationDTO save(RfbLocationDTO rfbLocationDTO) {
        log.debug("Request to save RfbLocation : {}", rfbLocationDTO);
        RfbLocation rfbLocation = rfbLocationMapper.toEntity(rfbLocationDTO);
        rfbLocation = rfbLocationRepository.save(rfbLocation);
        return rfbLocationMapper.toDto(rfbLocation);
    }

    /**
     * Get all the rfbLocations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RfbLocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RfbLocations");
        return rfbLocationRepository.findAll(pageable)
            .map(rfbLocationMapper::toDto);
    }

    /**
     * Get one rfbLocation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RfbLocationDTO findOne(Long id) {
        log.debug("Request to get RfbLocation : {}", id);
        RfbLocation rfbLocation = rfbLocationRepository.findOne(id);
        return rfbLocationMapper.toDto(rfbLocation);
    }

    /**
     * Delete the  rfbLocation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RfbLocation : {}", id);
        rfbLocationRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<RfbLeaderForLocationDTO> getRfbLeaderForLocation(Long locationId) {
        if (locationId == null) {
            throw new IllegalArgumentException("location id not informed");
        }
        RfbLocation location = getOneLocation(locationId);
        if (location == null) {
            throw new IllegalArgumentException("location id not informed");
        }

        Map<Long, RfbLeaderForLocationDTO> userInfoMap = Maps.newHashMap();
        location.getRvbEvents().stream()
            .forEach(event -> event.getRfbEventAttendances().stream()
                .forEach(evAtt -> {
                    User user = evAtt.getUser();
                    Long userId = user.getId();
                    RfbLeaderForLocationDTO dto = userInfoMap.get(userId);
                    int totalRuns = 1;
                    if (dto == null) {
                        dto = new RfbLeaderForLocationDTO();
                        String name = user.getFirstName();
                        String lastName = user.getLastName();
                        if (StringUtils.isNoneBlank(lastName)) {
                            name = lastName + ", " + name;
                        }
                        dto.setUserName(name);
                        dto.setTotalRuns(totalRuns);
                    } else {
                        totalRuns += 1;
                    }
                    dto.setTotalRuns(totalRuns);
                    userInfoMap.put(userId, dto);
                }));
        List<RfbLeaderForLocationDTO> values = Lists.newArrayList(userInfoMap.values());
        Collections.sort(values, Comparator.comparingInt(RfbLeaderForLocationDTO::getTotalRuns));
        Collections.reverse(values);
        return values;
    }

    protected RfbLocation getOneLocation(Long locationId) {
        return rfbLocationRepository.findOne(locationId);
    }
}
