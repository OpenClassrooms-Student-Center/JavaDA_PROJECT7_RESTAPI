package com.nnk.springboot.controllers.apiRest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.IRuleNameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RuleNameApiRestController {


    /**
     * SLF4J Logger instance.
     */
    private static final Logger logger = LogManager.getLogger("RuleNameApiRestController");


    private IRuleNameService ruleNameService;

    public RuleNameApiRestController(IRuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }


    /**
     * @return
     */
    @GetMapping("/ruleNames/api")
    public ResponseEntity<List<RuleName>> showRestRuleNames() {
        logger.info("@RequestMapping(\"/ruleNames/api\")");
        return new ResponseEntity<>(ruleNameService.findAll(), HttpStatus.OK);
    }

    /**
     * @param id
     * @return bid httpStatus.Ok
     * @throws DataNotFoundException
     */
    @GetMapping("/ruleName/api/{id}")
    public ResponseEntity<Optional<RuleName>> showRestRuleNameById(@PathVariable int id) throws DataNotFoundException {
        logger.info("@RequestMapping(\"/ruleName/api/{id}\")");
        Optional<RuleName> ruleName = ruleNameService.findById(id);
        return new ResponseEntity<>(ruleNameService.findById(id), HttpStatus.OK);
    }

    /**
     * @param ruleName
     * @return add ruleName
     */
    @PostMapping("/ruleName/api")
    public RuleName addRestRuleName(@RequestBody RuleName ruleName) {
        logger.info("@PostMapping(\"/ruleName/api\")");
        ruleNameService.save(ruleName);
        return ruleName;
    }


    /**
     * @param ruleName
     * @return update ruleName
     */
    @PutMapping("/ruleName/api")
    public RuleName uploadRestRuleName(@RequestBody RuleName ruleName) {
        logger.info("@PutMapping(\"/ruleName/api/{}\")  Id " + ruleName + " as modified", ruleName.getId());

        return ruleNameService.update(ruleName);
    }

    /**
     * @param ruleNameId
     * @return
     * @throws DataNotFoundException
     */
    @DeleteMapping("/ruleName/api/{ruleNameId}")
    public String deleteRestRuleName(@PathVariable int ruleNameId) throws DataNotFoundException {
        logger.info("@DeleteMapping(\"/bidList/api/{bidListId}\")");

        ruleNameService.delete(ruleNameId);
        return "delete bid by id: " + ruleNameId + " success";
    }
}
