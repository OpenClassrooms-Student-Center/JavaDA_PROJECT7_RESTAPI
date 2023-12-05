package com.nnk.springboot.unit.domain;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.domain.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Trade.class)
public class TradeTests extends TestVariables {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
    }

    @Test
    public void contextLoads() {
    }

    @Nested
    public class ValidationTests {

        @Test
        public void validationTest() {
            Set<ConstraintViolation<Trade>> result = validator.validate(trade);
            assertTrue(result.isEmpty());
        }

        @Nested
        public class AccountTests {
            @Test
            public void validationTestIfAccountIsBlank() {
                trade.setAccount("");
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("account", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeAccountNotBlank, constraintViolation.getMessage());
            }

            @Test
            public void validationTestIfAccountSize() {
                trade.setAccount(longString31);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("account", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeAccountSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class TypeTests {
            @Test
            public void validationTestIfTypeIsBlank() {
                trade.setType("");
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("type", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeTypeNotBlank, constraintViolation.getMessage());
            }

            @Test
            public void validationTestIfTypeSize() {
                trade.setType(longString31);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("type", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeTypeSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class BuyQuantityTests {
            @Test
            public void validationTestIfBuyQuantityNegative() {
                trade.setBuyQuantity(-5.0);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("buyQuantity", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeBuyQuantityPositiveOrZero, constraintViolation.getMessage());
            }
        }

        @Nested
        public class SellQuantityTests {
            @Test
            public void validationTestIfSellQuantityNegative() {
                trade.setSellQuantity(-5.0);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("sellQuantity", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeSellQuantityPositiveOrZero, constraintViolation.getMessage());
            }
        }

        @Nested
        public class BuyPriceTests {
            @Test
            public void validationTestIfBuyPriceNegative() {
                trade.setBuyPrice(-5.0);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("buyPrice", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeBuyPricePositiveOrZero, constraintViolation.getMessage());
            }
        }

        @Nested
        public class SellPriceTests {
            @Test
            public void validationTestIfSellPriceNegative() {
                trade.setSellPrice(-5.0);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("sellPrice", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeSellPricePositiveOrZero, constraintViolation.getMessage());
            }
        }

        @Nested
        public class SecurityTests {
            @Test
            public void validationTestIfSecuritySize() {
                trade.setSecurity(longString126);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("security", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeSecuritySize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class StatusTests {
            @Test
            public void validationTestIfStatusSize() {
                trade.setStatus(longString11);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("status", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeStatusSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class TraderTests {
            @Test
            public void validationTestIfTraderSize() {
                trade.setTrader(longString126);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("trader", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeTraderSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class BenchmarkTests {
            @Test
            public void validationTestIfBenchmarkSize() {
                trade.setBenchmark(longString126);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("benchmark", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeBenchmarkSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class BookTests {
            @Test
            public void validationTestIfBookSize() {
                trade.setBook(longString126);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("book", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeBookSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class CreationNameTests {
            @Test
            public void validationTestIfCreationNameSize() {
                trade.setCreationName(longString126);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("creationName", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeCreationNameSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class RevisionNameTests {
            @Test
            public void validationTestIfRevisionNameSize() {
                trade.setRevisionName(longString126);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("revisionName", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeRevisionNameSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class DealNameTests {
            @Test
            public void validationTestIfDealNameSize() {
                trade.setDealName(longString126);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("dealName", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeDealNameSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class DealTypeTests {
            @Test
            public void validationTestIfDealTypeSize() {
                trade.setDealType(longString126);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("dealType", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeDealTypeSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class SourceListIdTests {
            @Test
            public void validationTestIfSourceListIdSize() {
                trade.setSourceListId(longString126);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("sourceListId", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeSourceListIdSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class SideTests {
            @Test
            public void validationTestIfSideSize() {
                trade.setSide(longString126);
                Set<ConstraintViolation<Trade>> result = validator.validate(trade);
                assertEquals(1, result.size());
                ConstraintViolation<Trade> constraintViolation = (ConstraintViolation<Trade>) result.toArray()[0];
                assertEquals("side", constraintViolation.getPropertyPath().toString());
                assertEquals(tradeSideSize, constraintViolation.getMessage());
            }
        }
    }
}