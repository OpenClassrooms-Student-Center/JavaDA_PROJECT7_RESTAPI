package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurvePointRepositoryTest {
    @Mock
    private CurvePointRepository curvePointRepository;

    @BeforeEach
    private void setup() {
        curvePointRepository = Mockito.mock(CurvePointRepository.class);
    }

    @DisplayName(value = "1°) Recherche de tous les CurvePoints")
    @Order(1)
    @Test
    void test_findAll_should_FindAll_CurvePoint() {
        List<CurvePoint> curvePointList = new ArrayList<>();

        CurvePoint curvePoint = new CurvePoint();
        CurvePoint curvePoint2 = new CurvePoint();
        CurvePoint curvePoint3 = new CurvePoint();
        CurvePoint curvePoint4 = new CurvePoint();
        CurvePoint curvePoint5 = new CurvePoint();

        curvePointList.add(curvePoint);
        curvePointList.add(curvePoint2);
        curvePointList.add(curvePoint3);
        curvePointList.add(curvePoint4);
        curvePointList.add(curvePoint5);

        when(curvePointRepository.findAll()).thenReturn(curvePointList);

        List<CurvePoint> result = curvePointRepository.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(curvePointList.size());
    }

    @DisplayName(value = "2°) Recherche de CurvePoint par ID")
    @Order(2)
    @Test
    void test_findById_shoud_findCurvePoint_By_Id() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse("02/09/2023");

        CurvePoint curvePoint = new CurvePoint(1, 1, new Timestamp(date.getTime()),
                1.0, 5.2, new Timestamp(new Date().getTime()));

        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(curvePoint));

        Optional<CurvePoint> curvePointResult = curvePointRepository.findById(curvePoint.getId());

        assertThat(curvePointResult).isNotNull();
        assertThat(curvePointResult).isPresent();
        assertThat(curvePointResult.get().getId()).isEqualTo(curvePoint.getId());
    }

    @DisplayName(value = "3°) Mise à jour d'un CurvePoint Existant")
    @Order(3)
    @Test
    void test_update_should_update_CurvePoint() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse("02/05/2023");

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(new Timestamp(date.getTime()));
        curvePoint.setTerm(10.0);
        curvePoint.setValue(15.50);
        curvePoint.setCreationDate(new Timestamp(new Date().getTime()));

        CurvePoint curvePointUdate = new CurvePoint();
        curvePointUdate.setId(1);
        curvePointUdate.setCurveId(3);
        curvePointUdate.setAsOfDate(new Timestamp(date.getTime()));
        curvePointUdate.setTerm(50.0);
        curvePointUdate.setValue(40.50);
        curvePointUdate.setCreationDate(new Timestamp(new Date().getTime()));

        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint curvePointResult = curvePointRepository.save(curvePoint);

        assertThat(curvePointResult).isNotNull();
        assertThat(curvePointResult.getId()).isEqualTo(curvePointUdate.getId());
        assertThat(curvePointResult.getCurveId()).isEqualTo(curvePointUdate.getCurveId());
        assertThat(curvePointResult.getAsOfDate()).isEqualTo(curvePointUdate.getAsOfDate());
        assertThat(curvePointResult.getTerm()).isEqualTo(curvePointUdate.getTerm());
        assertThat(curvePointResult.getValue()).isEqualTo(curvePointUdate.getValue());
        assertThat(curvePointResult.getCreationDate()).isEqualTo(curvePointUdate.getCreationDate());
    }

    @DisplayName(value = "4°) Suppression de CurvePoint par ID")
    @Order(4)
    @Test
    void test_delete_shoud_deleteCurvePoint_By_Id() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(new Timestamp(new Date().getTime()));
        curvePoint.setTerm(10.0);
        curvePoint.setValue(15.50);
        curvePoint.setCreationDate(new Timestamp(new Date().getTime()));

        curvePointRepository.deleteById(curvePoint.getId());
    }
}
