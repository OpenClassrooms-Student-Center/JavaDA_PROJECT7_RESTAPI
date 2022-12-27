package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.ICurvePointService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * contain all business service methods for curvePoint
 */
@Service
public class CurvePointServiceImpl implements ICurvePointService {

    /**
     * SLF4J LOGGER instance.
     */
    private static final Logger logger = LogManager.getLogger("CurvePointServiceImpl");

    private final CurvePointRepository curvePointRepository;

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CurvePoint> findById(Integer id) throws DataNotFoundException {
        logger.debug("find bidById:{}", id);
        return Optional.ofNullable(curvePointRepository.findById(id).orElseThrow(()
                -> {
            logger.error("Invalid bid Id: {} ", id);
            return new DataNotFoundException("No User with id " + id + " found ");
        }));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(CurvePoint curvePoint) {
        logger.debug("save curvePoint:{}", curvePoint.getCurveId());
        curvePointRepository.save(curvePoint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurvePoint update(CurvePoint curvePoint) throws DataNotFoundException {
        logger.debug("update curvePoint:{}", curvePoint.getId());
        return curvePointRepository.save(curvePoint);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer curvePoint) {
        logger.debug("delete curvePoint:{}", curvePoint);
        CurvePoint deletecurvePoint = curvePointRepository.findById(curvePoint).orElseThrow(() -> {
            throw new DataNotFoundException("Id " + curvePoint + " Not Present in Data Base");
        });
//        Optional<BidList> deleteBid = bidListRepository.findById(bid);
//        bidListRepository.deleteById(deleteBid.get().getBidListId());
        curvePointRepository.deleteById(deletecurvePoint.getCurveId());
    }
}
