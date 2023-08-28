package com.nnk.springboot.service;

import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.Users;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UsersRepository;

import jakarta.validation.Valid;

@Service
@Component
public class ValidInput {

    private boolean pswValidInput;
    private boolean addUser;
    private boolean updateBid;
    private boolean addBid;
    private boolean addCurvePoint;
    private boolean updateCurvePoint;
    private boolean addRatings;
    private boolean updateRatings;
    private boolean addTrade;
    private boolean updateTrade;
    private boolean addRule;
    private boolean updateRule;

    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(ValidInput.class);

    @Autowired
    private LoggerApi loggerApi;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private BidListRepository bidListRepository;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private FormatDate formatDateFormat;

    public ValidInput() {
    }

    public ValidInput(boolean pswValidInput, boolean addUser, boolean updateBid, boolean addBid, boolean addCurvePoint,
            boolean updateCurvePoint, boolean addTrade, boolean updateTrade, boolean addRatings, boolean updateRatings,
            boolean addRule, boolean updateRule, LoggerApi loggerApi, UsersRepository userRepository,
            BidListRepository bidListRepository, CurvePointRepository curvePointRepository,
            TradeRepository tradeRepository, RuleNameRepository ruleNameRepository, RatingRepository ratingRepository,
            FormatDate formatDateFormat) {
        this.pswValidInput = pswValidInput;
        this.addUser = addUser;
        this.updateBid = updateBid;
        this.addBid = addBid;
        this.addCurvePoint = addCurvePoint;
        this.updateCurvePoint = updateCurvePoint;
        this.addTrade = addTrade;
        this.updateTrade = updateTrade;
        this.addRatings = addRatings;
        this.updateRatings = updateRatings;
        this.addRule = addRule;
        this.updateRule = updateRule;
        this.loggerApi = loggerApi;
        this.userRepository = userRepository;
        this.bidListRepository = bidListRepository;
        this.curvePointRepository = curvePointRepository;
        this.tradeRepository = tradeRepository;
        this.ruleNameRepository = ruleNameRepository;
        this.ratingRepository = ratingRepository;
        this.formatDateFormat = formatDateFormat;
    }

    /**
     * @return boolean
     */
    public boolean isPswValidInput() {
        return this.pswValidInput;
    }

    /**
     * @return boolean
     */
    public boolean getPswValidInput() {
        return this.pswValidInput;
    }

    public void setPswValidInput(boolean pswValidInput) {
        this.pswValidInput = pswValidInput;
    }

    public boolean isUpdateBid() {
        return this.updateBid;
    }

    public boolean getUpdateBid() {
        return this.updateBid;
    }

    public void setUpdateBid(boolean updateBid) {
        this.updateBid = updateBid;
    }

    public LoggerApi getLoggerApi() {
        return this.loggerApi;
    }

    public void setLoggerApi(LoggerApi loggerApi) {
        this.loggerApi = loggerApi;
    }

    public UsersRepository getUserRepository() {
        return this.userRepository;
    }

    public void setUserRepository(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    public BidListRepository getBidListRepository() {
        return this.bidListRepository;
    }

    public void setBidListRepository(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    public FormatDate getFormatDateFormat() {
        return this.formatDateFormat;
    }

    public void setFormatDateFormat(FormatDate formatDateFormat) {
        this.formatDateFormat = formatDateFormat;
    }

    public boolean isAddBid() {
        return this.addBid;
    }

    public boolean getAddBid() {
        return this.addBid;
    }

    public void setAddBid(boolean addBid) {
        this.addBid = addBid;
    }

    public boolean isAddUser() {
        return this.addUser;
    }

    public boolean getAddUser() {
        return this.addUser;
    }

    public void setAddUser(boolean addUser) {
        this.addUser = addUser;
    }

    public boolean isAddCurvePoint() {
        return this.addCurvePoint;
    }

    public boolean getAddCurvePoint() {
        return this.addCurvePoint;
    }

    public void setAddCurvePoint(boolean addCurvePoint) {
        this.addCurvePoint = addCurvePoint;
    }

    public boolean isUpdateCurvePoint() {
        return this.updateCurvePoint;
    }

    public boolean getUpdateCurvePoint() {
        return this.updateCurvePoint;
    }

    public void setUpdateCurvePoint(boolean updateCurvePoint) {
        this.updateCurvePoint = updateCurvePoint;
    }

    public CurvePointRepository getCurvePointRepository() {
        return this.curvePointRepository;
    }

    public void setCurvePointRepository(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    public boolean isAddRatings() {
        return this.addRatings;
    }

    public boolean getAddRatings() {
        return this.addRatings;
    }

    public void setAddRatings(boolean addRatings) {
        this.addRatings = addRatings;
    }

    public boolean isUpdateRatings() {
        return this.updateRatings;
    }

    public boolean getUpdateRatings() {
        return this.updateRatings;
    }

    public void setUpdateRatings(boolean updateRatings) {
        this.updateRatings = updateRatings;
    }

    public RuleNameRepository getRuleNameRepository() {
        return this.ruleNameRepository;
    }

    public void setRuleNameRepository(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    public RatingRepository getRatingRepository() {
        return this.ratingRepository;
    }

    public void setRatingRepository(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public boolean isAddTrade() {
        return this.addTrade;
    }

    public boolean getAddTrade() {
        return this.addTrade;
    }

    public void setAddTrade(boolean addTrade) {
        this.addTrade = addTrade;
    }

    public boolean isUpdateTrade() {
        return this.updateTrade;
    }

    public boolean getUpdateTrade() {
        return this.updateTrade;
    }

    public void setUpdateTrade(boolean updateTrade) {
        this.updateTrade = updateTrade;
    }

    public boolean isAddRule() {
        return this.addRule;
    }

    public boolean getAddRule() {
        return this.addRule;
    }

    public void setAddRule(boolean addRule) {
        this.addRule = addRule;
    }

    public boolean isUpdateRule() {
        return this.updateRule;
    }

    public boolean getUpdateRule() {
        return this.updateRule;
    }

    public void setUpdateRule(boolean updateRule) {
        this.updateRule = updateRule;
    }

    public TradeRepository getTradeRepository() {
        return this.tradeRepository;
    }

    public void setTradeRepository(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public void pswValidInputs(@Valid Users user, Integer id) {

        boolean contraintsPswd = false;

        contraintsPswd = verifyContraintsPassword(user);

        if (contraintsPswd) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setId(id);
            userRepository.save(user);

            setPswValidInput(true);

        } else
            setPswValidInput(false);

    }

    public void addUser(@Valid Users user) {

        boolean contraintsPswd = false;

        contraintsPswd = verifyContraintsPassword(user);

        if (contraintsPswd) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);

            setAddUser(true);

        } else
            setAddUser(false);

    }

    private boolean verifyContraintsPassword(@Valid Users user) {

        long longPassword = user.getPassword().length();
        long compteurUpperCase = user.getPassword().chars().filter(Character::isUpperCase).count();
        long compteurChiffreCase = user.getPassword().chars().filter(Character::isDigit).count();
        long compteurLetterCase = user.getPassword().chars().filter(Character::isLetter).count();

        if ((longPassword >= 8) && (compteurUpperCase >= 1) && (compteurChiffreCase >= 1)
                && (compteurChiffreCase + compteurLetterCase < longPassword)) {

            String messageLoggerInfo = loggerApi
                    .loggerStrings("Le mot de passe a au moins 8 caractères ; ",
                            "il comporte au moins 1 majuscule et 1 chiffre",
                            "et il a au moins un symbole (les chiffres + les lettres font moins que la totalité des caractères)");

            LOGGER.info(messageLoggerInfo);
            return true;

        } else {

            String messageLoggerInfo = loggerApi
                    .loggerStrings("Le mot de passe ne respecte pas les consignes",
                            "",
                            "");

            LOGGER.info(messageLoggerInfo);
            return false;

        }
    }

    public void updateBid(@Valid BidList bidList, Integer id, String bidListDateString, String creationDateString,
            String revisionDateString) throws ParseException {

        setDateToFormat(bidList, bidListDateString, creationDateString, revisionDateString);
        bidList.setBidlistId(id);
        bidListRepository.save(bidList);

        setUpdateBid(true);

    }

    public void addBid(@Valid BidList bid, String bidListDateString, String creationDateString,
            String revisionDateString) throws ParseException {

        setDateToFormat(bid, bidListDateString, creationDateString, revisionDateString);
        bidListRepository.save(bid);

        setAddBid(true);

    }

    private void setDateToFormat(@Valid BidList bid, String bidListDateString, String creationDateString,
            String revisionDateString) throws ParseException {

        if (!bidListDateString.equals("")) {
            formatDateFormat.setFromatDateStringToTimestamp(bidListDateString);
            bid.setBidListDate(formatDateFormat.getTimestampFromatDate());
        }
        if (!creationDateString.equals("")) {
            formatDateFormat.setFromatDateStringToTimestamp(creationDateString);
            bid.setCreationDate(formatDateFormat.getTimestampFromatDate());
        }
        if (!revisionDateString.equals("")) {
            formatDateFormat.setFromatDateStringToTimestamp(revisionDateString);
            bid.setRevisionDate(formatDateFormat.getTimestampFromatDate());
        }

    }

    public void addCurvePoint(@Valid CurvePoint curvePoint, String asOfDateString, String creationDateString)
            throws ParseException {

        setDateToFormat(curvePoint, asOfDateString, creationDateString);
        curvePointRepository.save(curvePoint);

        setAddCurvePoint(true);

    }

    public void updateCurvePoint(@Valid CurvePoint curvePoint, Integer id, String asOfDateString,
            String creationDateString) throws ParseException {

        setDateToFormat(curvePoint, asOfDateString, creationDateString);
        curvePoint.setId(id);
        curvePointRepository.save(curvePoint);

        setUpdateCurvePoint(true);

    }

    private void setDateToFormat(@Valid CurvePoint curvePoint, String asOfDateString, String creationDateString)
            throws ParseException {

        if (!asOfDateString.equals("")) {
            formatDateFormat.setFromatDateStringToTimestamp(asOfDateString);
            curvePoint.setAsOfDate(formatDateFormat.getTimestampFromatDate());
        }
        if (!creationDateString.equals("")) {
            formatDateFormat.setFromatDateStringToTimestamp(creationDateString);
            curvePoint.setCreationDate(formatDateFormat.getTimestampFromatDate());
        }
    }

    public void addTrade(@Valid Trade trade, String tradeDateString, String creationDateString,
            String revisionDateString) throws ParseException {

        setDateToFormat(trade, tradeDateString, creationDateString, revisionDateString);
        tradeRepository.save(trade);

        setAddTrade(true);

    }

    public void updateTrade(@Valid Trade trade, Integer id, String tradeDateString, String creationDateString,
            String revisionDateString) throws ParseException {

        setDateToFormat(trade, tradeDateString, creationDateString, revisionDateString);

        trade.setTradeId(id);
        tradeRepository.save(trade);

        setUpdateTrade(true);

    }

    private void setDateToFormat(@Valid Trade trade, String tradeDateString, String creationDateString,
            String revisionDateString) throws ParseException {

        if (!tradeDateString.equals("")) {
            formatDateFormat.setFromatDateStringToTimestamp(tradeDateString);
            trade.setTradeDate(formatDateFormat.getTimestampFromatDate());
        }
        if (!creationDateString.equals("")) {
            formatDateFormat.setFromatDateStringToTimestamp(creationDateString);
            trade.setCreationDate(formatDateFormat.getTimestampFromatDate());
        }
        if (!revisionDateString.equals("")) {
            formatDateFormat.setFromatDateStringToTimestamp(revisionDateString);
            trade.setRevisionDate(formatDateFormat.getTimestampFromatDate());
        }

    }

    public void addRule(@Valid RuleName ruleName) {

        ruleNameRepository.save(ruleName);
        setAddRule(true);
    }

    public void updateRule(@Valid RuleName ruleName, Integer id) {

        ruleName.setId(id);
        ruleNameRepository.save(ruleName);

        setUpdateRule(true);
    }

    public void addRatings(@Valid Rating rating) {

        ratingRepository.save(rating);

        setAddRatings(true);
    }

    public void updateRatings(Integer id, @Valid Rating rating) {

        rating.setId(id);
        ratingRepository.save(rating);

        setUpdateRatings(true);
    }

}