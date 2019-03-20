package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.LeaveHoliDayService;
import io.github.jhipster.application.domain.LeaveHoliDay;
import io.github.jhipster.application.repository.LeaveHoliDayRepository;
import io.github.jhipster.application.repository.search.LeaveHoliDaySearchRepository;
import io.github.jhipster.application.service.dto.LeaveHoliDayDTO;
import io.github.jhipster.application.service.mapper.LeaveHoliDayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing LeaveHoliDay.
 */
@Service
@Transactional
public class LeaveHoliDayServiceImpl implements LeaveHoliDayService {

    private final Logger log = LoggerFactory.getLogger(LeaveHoliDayServiceImpl.class);

    private final LeaveHoliDayRepository leaveHoliDayRepository;

    private final LeaveHoliDayMapper leaveHoliDayMapper;

    private final LeaveHoliDaySearchRepository leaveHoliDaySearchRepository;

    public LeaveHoliDayServiceImpl(LeaveHoliDayRepository leaveHoliDayRepository, LeaveHoliDayMapper leaveHoliDayMapper, LeaveHoliDaySearchRepository leaveHoliDaySearchRepository) {
        this.leaveHoliDayRepository = leaveHoliDayRepository;
        this.leaveHoliDayMapper = leaveHoliDayMapper;
        this.leaveHoliDaySearchRepository = leaveHoliDaySearchRepository;
    }

    /**
     * Save a leaveHoliDay.
     *
     * @param leaveHoliDayDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LeaveHoliDayDTO save(LeaveHoliDayDTO leaveHoliDayDTO) {
        log.debug("Request to save LeaveHoliDay : {}", leaveHoliDayDTO);
        LeaveHoliDay leaveHoliDay = leaveHoliDayMapper.toEntity(leaveHoliDayDTO);
        leaveHoliDay = leaveHoliDayRepository.save(leaveHoliDay);
        LeaveHoliDayDTO result = leaveHoliDayMapper.toDto(leaveHoliDay);
        leaveHoliDaySearchRepository.save(leaveHoliDay);
        return result;
    }

    /**
     * Get all the leaveHoliDays.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LeaveHoliDayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveHoliDays");
        return leaveHoliDayRepository.findAll(pageable)
            .map(leaveHoliDayMapper::toDto);
    }


    /**
     * Get one leaveHoliDay by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LeaveHoliDayDTO> findOne(Long id) {
        log.debug("Request to get LeaveHoliDay : {}", id);
        return leaveHoliDayRepository.findById(id)
            .map(leaveHoliDayMapper::toDto);
    }

    /**
     * Delete the leaveHoliDay by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LeaveHoliDay : {}", id);
        leaveHoliDayRepository.deleteById(id);
        leaveHoliDaySearchRepository.deleteById(id);
    }

    /**
     * Search for the leaveHoliDay corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LeaveHoliDayDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LeaveHoliDays for query {}", query);
        return leaveHoliDaySearchRepository.search(queryStringQuery(query), pageable)
            .map(leaveHoliDayMapper::toDto);
    }
}
