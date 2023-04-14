package com.nnk.springboot.controllersTest;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
//import org.junit.Test;
import jakarta.validation.Valid;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")

public class BidListControllerTest {
    @Mock
    BidListService bidListService;

    private BidListController bidListController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        bidListController = new BidListController(bidListService);
    }

    @Test
    public void homeDisplayBidListsPageTest(){
        //arrange
        Model model = new ConcurrentModel();
        List<BidList> listOfBidLists = new ArrayList<>();
        when(bidListService.findAll()).thenReturn(listOfBidLists);
        //act
        String view = bidListController.homeDisplayBidListsPage(model);

        //assert
        assertEquals("bidList/list",view );

    }
    @Test
    public void displayAddBidFormTest(){
        BidList bidList = new BidList();
        //act
       String page =  bidListController.displayAddBidForm(bidList);
       assertEquals("bidList/add",page );


    }
    @Test
    public void validateBidListTest(){
        //arrange
        BidList bidList = new BidList("accountTest", "typeTest", 25.5);
        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        //act
        String page = bidListController.validateBidList(bidList, result, model);
        //
        assertEquals("redirect:/bidList/list", page);
    }

    @Test
    public void validateBidListWithErrorsOnBidListTest() throws Exception {
        //arrange
        BidList bidList = new BidList("", "", 25.5);
        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        when(bidListService.validateNewBidList(bidList)).thenThrow(new Exception());
        //act
        String page = bidListController.validateBidList(bidList, result, model);
        //
        assertEquals("bidList/add", page);
        verify(bidListService, times(1)).validateNewBidList(bidList);
    }

    @Test
    public void displayUpdateFormTest() throws Exception {
        Model model = new ConcurrentModel();
        Integer id = 2;

        when(bidListService.getBidListById(id)).thenReturn(new BidList("var1", "var2", 12.2));
        //act
        String page = bidListController.displayUpdateForm(id, model);
        //assert
        assertEquals("bidList/update", page);


    }
    @Test
    public void displayUpdateFormWithErrorsTest() throws Exception {
        Model model = new ConcurrentModel();
        Integer id = 2;

        when(bidListService.getBidListById(id)).thenThrow(new Exception());
        //act
        String page = bidListController.displayUpdateForm(id, model);
        //assert
        assertEquals("bidList/list", page);


    }
    @Test
    public void updateBidTest() throws Exception {
        //arrange
        BidList bidList = new BidList("accountTest", "typeTest", 25.5);
        BidList updatedBidList = new BidList("updatedAccount", "updatedType", 20.0);
        //BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        when(bidListService.updateBidList(1, updatedBidList)).thenReturn(bidList);
        //when(bidListService.updateBidList(1, updatedBidList)).thenReturn(bidList);
        //act

        String page = bidListController.updateBid(1, bidList, result, model);
        //
        assertEquals("redirect:/bidList/list", page);

    }
    @Test
    public void updateBidWithBindingResultErrorsTest() throws Exception {
        //arrange
        BidList bidList = new BidList("accountTest", "typeTest", 25.5);
        BidList updatedBidList = new BidList("", "", 20.0);
        //BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);
        when(bidListService.updateBidList(1, updatedBidList)).thenReturn(bidList);
        //when(bidListService.updateBidList(1, updatedBidList)).thenReturn(bidList);
        //act

        String page = bidListController.updateBid(1, bidList, result, model);
        //
        assertEquals("bidList/update", page);

    }
    @Test
    public void updateBidWithErrorsTest() throws Exception {
        BidList bidList = new BidList("accountTest", "typeTest", 25.5);
        BidList updatedBidList = new BidList("updatedAccount", "updatedType", 20.0);
        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        when(bidListService.updateBidList(1, updatedBidList)).thenThrow(new Exception());
        //act
        String page = bidListController.updateBid(1, updatedBidList, result, model);
        //
        assertEquals("bidList/list", page);

    }
    @Test
    public void deleteBidTest() throws Exception {
        Model model = new ConcurrentModel();
        doNothing().when(bidListService).deleteBidList(1);
        //act
        String page = bidListController.deleteBid(1, model);
        //assert
        verify(bidListService, times(1)).deleteBidList(1);
        assertEquals("redirect:/bidList/list", page);
    }
    @Test
    public void deleteBidWithErrorsTest() throws Exception {
        Model model = new ConcurrentModel();
        doNothing().when(bidListService).deleteBidList(1);
        //act
        String page = bidListController.deleteBid(1, model);
        //assert
        verify(bidListService, times(1)).deleteBidList(1);
        assertEquals("redirect:/bidList/list", page);
    }

}
