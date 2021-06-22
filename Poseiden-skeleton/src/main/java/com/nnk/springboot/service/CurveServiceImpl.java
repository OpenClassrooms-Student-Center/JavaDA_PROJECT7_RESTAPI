package com.nnk.springboot.service;

import com.nnk.springboot.exceptions.NumberFormatException;
import com.nnk.springboot.forms.AddCurvePointForm;
import com.nnk.springboot.interfaces.CurveService;
import com.nnk.springboot.model.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurveServiceImpl implements CurveService {
    
    private final CurvePointRepository curvePointRepository;

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (java.lang.NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    @Override
    public void validateCurvePoint(AddCurvePointForm addCurvePointForm) throws NumberFormatException {


        if (!isNumeric(addCurvePointForm.getCurveId()) || !isNumeric(addCurvePointForm.getTerm()) || !isNumeric(addCurvePointForm.getValue())){
            throw new NumberFormatException("The quantity must be a number.");
        }

        int curveId = Integer.parseInt(addCurvePointForm.getCurveId());
        double term = Double.parseDouble(addCurvePointForm.getTerm());
        double value = Double.parseDouble(addCurvePointForm.getValue());


        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(curveId);
        curvePoint.setTerm(term);
        curvePoint.setValue(value);
        curvePointRepository.save(curvePoint);
    }

    @Override
    public void updateCurvePoint(Integer id, CurvePoint curvePoint) throws NumberFormatException {
        
        String curveId = String.valueOf(curvePoint.getCurveId());
        String term = String.valueOf(curvePoint.getTerm());
        String value = String.valueOf(curvePoint.getValue());

        if (!isNumeric(curveId) || !isNumeric(term) || !isNumeric(value)){
            throw new NumberFormatException("The quantity must be a number.");
        }

        curvePoint.setId(id);
        curvePointRepository.save(curvePoint);
    }

    @Override
    public void deleteCurvePoint(Integer id) {
        CurvePoint curvePoint = curvePointRepository.findCurvePointById(id);
        curvePointRepository.delete(curvePoint);
    }
}
