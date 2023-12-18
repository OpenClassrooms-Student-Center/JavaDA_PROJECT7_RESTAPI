package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BidListController.class)
public class BidListControllerTests extends TestVariables {

    @Autowired
    BidListController bidListController;

    @MockBean
    private BidListService bidListService;

    @MockBean
    private BindingResult bindingResult;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        bidList.setId(1);

        when(bidListService.findAll()).thenReturn(bidListList);
        when(bidListService.findById(any(Integer.class))).thenReturn(bidListOptional);
        when(bidListService.findById(any(Integer.class))).thenReturn(bidListOptional);
        when(bindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    public void ContextLoads() {}

    @Nested
    public class HomeTests
    {
        @Test
        public void homeTest () {
            assertEquals("bidList/list", bidListController.home(model));
            verify(bidListService, Mockito.times(1)).findAll();
        }
    }

    @Nested
    public class AddBidListFormTests
    {
        @Test
        public void addBidListFormTest () {
            assertEquals("bidList/add", bidListController.addBidListForm(bidList));
        }
        @Test
        public void addBidListFormTestIfEmpty () {
            assertEquals("bidList/add", bidListController.addBidListForm(new BidList()));
        }
        @Test
        public void addBidListFormTestIfNull () {
            assertEquals("bidList/add", bidListController.addBidListForm(null));
        }
    }

    @Nested
    public class ValidateTests
    {
        @Test
        public void validateTest () {
            assertEquals("redirect:/bidList/list", bidListController.validate(bidList, bindingResult, model));
            verify(bidListService, Mockito.times(1)).save(any(BidList.class));
        }
        @Test
        public void validateTestIfInvalidBidList () {
            when(bindingResult.hasErrors()).thenReturn(true);
            assertEquals("bidList/add", bidListController.validate(bidList, bindingResult, model));
            verify(bidListService, Mockito.times(0)).save(any(BidList.class));
        }
    }

    @Nested
    public class ShowUpdateFormTests
    {
        @Test
        public void showUpdateFormTest () {
            assertEquals("bidList/update", bidListController.showUpdateForm(bidList.getId(), model));
            verify(bidListService, Mockito.times(1)).findById(any(Integer.class));
        }
        @Test
        public void showUpdateFormTestIfBidListNotFound () {
            when(bidListService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class, () -> bidListController.showUpdateForm(bidList.getId(), model));
            verify(bidListService, Mockito.times(1)).findById(any(Integer.class));
        }
    }

    @Nested
    public class UpdateBidListTests
    {
        @Test
        public void updateBidListTest () {
            assertEquals("redirect:/bidList/list", bidListController.updateBidList(bidList.getId(), bidList, bindingResult, model));
            verify(bidListService, Mockito.times(1)).findById(any(Integer.class));
            verify(bidListService, Mockito.times(1)).save(any(BidList.class));
        }
        @Test
        public void updateBidListTestIfInvalidBidList () {
            when(bindingResult.hasErrors()).thenReturn(true);
            assertEquals("redirect:/bidList/update/{id}", bidListController.updateBidList(bidList.getId(), bidList, bindingResult, model));
            verify(bidListService, Mockito.times(0)).findById(any(Integer.class));
            verify(bidListService, Mockito.times(0)).save(any(BidList.class));
        }
        @Test
        public void updateBidListTestIfBidListNotInDB () {
            when(bidListService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class, () -> bidListController.updateBidList(bidList.getId(), bidList, bindingResult, model));
            verify(bidListService, Mockito.times(1)).findById(any(Integer.class));
            verify(bidListService, Mockito.times(0)).save(any(BidList.class));
        }
    }

    @Nested
    public class DeleteBidListTests
    {
        @Test
        public void deleteBidListTest () {
            assertEquals("redirect:/bidList/list", bidListController.deleteBidList(bidList.getId(), model));
            verify(bidListService, Mockito.times(1)).deleteById(any(Integer.class));
        }
    }
}
