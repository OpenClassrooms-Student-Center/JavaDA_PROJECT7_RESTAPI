package com.nnk.springboot.controllersTest;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
public class TradeControllerTest {
    @Mock
    TradeService tradeService;

    private TradeController tradeController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        tradeController = new TradeController(tradeService);
    }

    @Test
    public void homeDisplayTradesPageTest(){
        //arrange
        Model model = new ConcurrentModel();
        List<Trade> listOfTrades = new ArrayList<>();
        when(tradeService.findAll()).thenReturn(listOfTrades);
        //act
        String view = tradeController.home(model);

        //assert
        assertEquals("trade/list",view );

    }
    @Test
    public void displayAddBidFormTest(){
        Trade trade = new Trade();
        //act
        String page =  tradeController.displayAddUserForm(trade);
        assertEquals("trade/add",page );


    }
    @Test
    public void validateTradeTest() throws Exception {
        //arrange
        Trade trade = new Trade("firstAccount", "secondType");
        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        //act
        String page = tradeController.validate(trade, result, model);
        //
        assertEquals("redirect:/trade/list", page);
    }

    @Test
    public void validateTradeWithErrorsOnTradeTest() throws Exception {
        //arrange
        Trade trade = new Trade("", "");
        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        when(tradeService.validateNewTrade(trade)).thenThrow(new Exception());
        //act
        String page = tradeController.validate(trade, result, model);
        //
        assertEquals("trade/add", page);
        verify(tradeService, times(1)).validateNewTrade(trade);
    }

    @Test
    public void displayUpdateFormTest() throws Exception {
        Model model = new ConcurrentModel();
        Integer id = 2;

        when(tradeService.getTradeById(id)).thenReturn(new Trade("var1", "var2"));
        //act
        String page = tradeController.displayUpdateForm(id, model);
        //assert
        assertEquals("trade/update", page);


    }
    @Test
    public void displayUpdateFormWithErrorsTest() throws Exception {
        Model model = new ConcurrentModel();
        Integer id = 2;

        when(tradeService.getTradeById(id)).thenThrow(new Exception());
        //act
        String page = tradeController.displayUpdateForm(id, model);
        //assert
        assertEquals("trade/list", page);


    }
    @Test
    public void updateBidTest() throws Exception {
        //arrange
        Trade trade = new Trade("accountTest", "typeTest");
        Trade updatedTrade = new Trade("updatedAccount", "updatedType");
        //BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        when(tradeService.updateTrade(1, updatedTrade)).thenReturn(trade);
        //when(tradeService.updateTrade(1, updatedTrade)).thenReturn(trade);
        //act

        String page = tradeController.updateTrade(1, trade, result, model);
        //
        assertEquals("redirect:/trade/list", page);

    }
    @Test
    public void updateBidWithBindingResultErrorsTest() throws Exception {
        //arrange
        Trade trade = new Trade("accountTest", "typeTest");
        Trade updatedTrade = new Trade("", "");
        //BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);
        when(tradeService.updateTrade(1, updatedTrade)).thenReturn(trade);
        //when(tradeService.updateTrade(1, updatedTrade)).thenReturn(trade);
        //act

        String page = tradeController.updateTrade(1, updatedTrade, result, model);
        //
        assertEquals("trade/update", page);

    }
    @Test
    public void updateTradeWithErrorsTest() throws Exception {
        Trade trade = new Trade("accountTest", "typeTest");
        Trade updatedTrade = new Trade("updatedAccount", "updatedType");
        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        when(tradeService.updateTrade(1, updatedTrade)).thenThrow(new Exception());
        //act
        String page = tradeController.updateTrade(1, updatedTrade, result, model);
        //
        assertEquals("trade/list", page);

    }
    @Test
    public void deleteBidTest() throws Exception {
        Model model = new ConcurrentModel();
        doNothing().when(tradeService).deleteTrade(1);
        //act
        String page = tradeController.deleteTrade(1, model);
        //assert
        verify(tradeService, times(1)).deleteTrade(1);
        assertEquals("redirect:/trade/list", page);
    }
    @Test
    public void deleteBidWithErrorsTest() throws Exception {
        Model model = new ConcurrentModel();
        doNothing().when(tradeService).deleteTrade(1);
        //act
        String page = tradeController.deleteTrade(1, model);
        //assert
        verify(tradeService, times(1)).deleteTrade(1);
        assertEquals("redirect:/trade/list", page);
    }

}
