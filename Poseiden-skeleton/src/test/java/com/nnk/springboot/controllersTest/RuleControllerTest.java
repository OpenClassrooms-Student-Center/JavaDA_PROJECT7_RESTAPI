package com.nnk.springboot.controllersTest;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
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

public class RuleControllerTest {
    @Mock
    RuleNameService ruleNameService;

    private RuleNameController ruleController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        ruleController = new RuleNameController(ruleNameService);
    }

    @Test
    public void homeDisplayRuleNamesPageTest(){
        //arrange
        Model model = new ConcurrentModel();
        List<RuleName> listOfRuleNames = new ArrayList<>();
        when(ruleNameService.findAll()).thenReturn(listOfRuleNames);
        //act
        String view = ruleController.homeDisplayRuleNamesList(model);

        //assert
        assertEquals("ruleName/list",view );

    }
    @Test
    public void displayAddBidFormTest(){
        RuleName ruleName = new RuleName();
        //act
        String page =  ruleController.displayAddRuleForm(ruleName);
        assertEquals("ruleName/add",page );
    }
    @Test
    public void validateRuleNameTest(){
        //arrange
        RuleName ruleName = new RuleName("first", "second", "third", "fourth", "fifth", "sixth" );
        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        //act
        String page = ruleController.validateRuleName(ruleName, result, model);
        //
        assertEquals("redirect:/ruleName/list", page);
    }

    @Test
    public void validateRuleNameWithErrorsOnRuleNameTest() throws Exception {
        //arrange
        RuleName ruleName = new RuleName("", "", "", "", "", "");
        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        when(ruleNameService.validateNewRuleName(ruleName)).thenThrow(new Exception());
        //act
        String page = ruleController.validateRuleName(ruleName, result, model);
        //
        assertEquals("ruleName/add", page);
        verify(ruleNameService, times(1)).validateNewRuleName(ruleName);
    }
    @Test
    public void validateRuleNameWithBindingErrorsOnRuleNameTest(){
        RuleName ruleName = new RuleName("", "", "", "", "", "");

        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        when(result.hasErrors()).thenReturn(true);
        String page = ruleController.validateRuleName(ruleName, result, model);
        //
        assertEquals("ruleName/add", page);
    }

    @Test
    public void displayUpdateFormTest() throws Exception {
        Model model = new ConcurrentModel();
        Integer id = 2;

        when(ruleNameService.getRuleNameById(id)).thenReturn(new RuleName("var1", "var2", "var3","var4","var5","var6"));
        //act
        String page = ruleController.displayUpdateForm(id, model);
        //assert
        assertEquals("ruleName/update", page);


    }
    @Test
    public void displayUpdateFormWithErrorsTest() throws Exception {
        Model model = new ConcurrentModel();
        Integer id = 2;

        when(ruleNameService.getRuleNameById(id)).thenThrow(new Exception());
        //act
        String page = ruleController.displayUpdateForm(id, model);
        //assert
        assertEquals("ruleName/list", page);


    }
    @Test
    public void updateRuleNameTest() throws Exception {
        //arrange
        RuleName ruleName = new RuleName("var1", "var2", "var3","var4","var5","var6");
        RuleName updatedRuleName = new RuleName("up1", "up2", "up3","up4","up5","up6");
        //BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        when(ruleNameService.updateRuleName(1, updatedRuleName)).thenReturn(ruleName);
        //when(ruleNameService.updateRuleName(1, updatedRuleName)).thenReturn(ruleName);
        //act

        String page = ruleController.updateRuleName(1, updatedRuleName, result, model);
        //
        assertEquals("redirect:/ruleName/list", page);

    }
    @Test
    public void updateBidWithBindingResultErrorsTest() throws Exception {
        //arrange
        RuleName ruleName = new RuleName("var1", "var2", "var3","var4","var5","var6");
        RuleName updatedRuleName = new RuleName("", "", "up3","up4","up5","up6");

        //BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);
        when(ruleNameService.updateRuleName(1, updatedRuleName)).thenReturn(ruleName);
        //when(ruleNameService.updateRuleName(1, updatedRuleName)).thenReturn(ruleName);
        //act

        String page = ruleController.updateRuleName(1, updatedRuleName, result, model);

        //
        assertEquals("redirect:/ruleName/update/1", page);

    }
    @Test
    public void updateBidWithErrorsTest() throws Exception {
        RuleName ruleName = new RuleName("var1", "var2", "var3","var4","var5","var6");
        RuleName updatedRuleName = new RuleName("up1", "up2", "up3","up4","up5","up6");
        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        when(ruleNameService.updateRuleName(1, updatedRuleName)).thenThrow(new Exception());
        //act
        String page = ruleController.updateRuleName(1, updatedRuleName, result, model);

        //
        assertEquals("redirect:/ruleName/update/1", page);

    }
    @Test
    public void deleteBidTest() throws Exception {
        Model model = new ConcurrentModel();
        doNothing().when(ruleNameService).deleteRuleName(1);
        //act
        String page = ruleController.deleteRuleName(1, model);
        //assert
        verify(ruleNameService, times(1)).deleteRuleName(1);
        assertEquals("redirect:/ruleName/list", page);
    }
    @Test
    public void deleteRuleWithErrorsTest() throws Exception {

        Model model = new ConcurrentModel();
        doThrow(new Exception()).when(ruleNameService).deleteRuleName(1);

        //act
        String page = ruleController.deleteRuleName(1, model);
        //assert
        verify(ruleNameService, times(1)).deleteRuleName(1);
        assertEquals("ruleName/list", page);
    }

}
