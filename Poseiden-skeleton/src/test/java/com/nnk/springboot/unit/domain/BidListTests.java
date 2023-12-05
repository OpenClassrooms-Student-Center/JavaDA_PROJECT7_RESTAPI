package com.nnk.springboot.unit.domain;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.domain.BidList;
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

@SpringBootTest(classes = BidList.class)
public class BidListTests extends TestVariables {

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
            Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
            assertTrue(result.isEmpty());
        }

        @Nested
        public class AccountTests {
            @Test
            public void validationTestIfAccountIsBlank() {
                bidList.setAccount("");
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("account", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListAccountNotBlank, constraintViolation.getMessage());
            }

            @Test
            public void validationTestIfAccountSize() {
                bidList.setAccount(longString31);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("account", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListAccountSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class TypeTests {
            @Test
            public void validationTestIfTypeIsBlank() {
                bidList.setType("");
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("type", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListTypeNotBlank, constraintViolation.getMessage());
            }

            @Test
            public void validationTestIfTypeSize() {
                bidList.setType(longString31);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("type", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListTypeSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class BidQuantityTests {
            @Test
            public void validationTestIfBidQuantityNegative() {
                bidList.setBidQuantity(-5.0);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("bidQuantity", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListBidQuantityPositiveOrZero, constraintViolation.getMessage());
            }
        }

        @Nested
        public class AskQuantityTests {
            @Test
            public void validationTestIfAskQuantityNegative() {
                bidList.setAskQuantity(-5.0);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("askQuantity", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListAskQuantityPositiveOrZero, constraintViolation.getMessage());
            }
        }

        @Nested
        public class BidTests {
            @Test
            public void validationTestIfBidNegative() {
                bidList.setBid(-5.0);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("bid", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListBidPositiveOrZero, constraintViolation.getMessage());
            }
        }

        @Nested
        public class AskTests {
            @Test
            public void validationTestIfAskNegative() {
                bidList.setAsk(-5.0);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("ask", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListAskPositiveOrZero, constraintViolation.getMessage());
            }
        }

        @Nested
        public class BenchmarkTests {
            @Test
            public void validationTestIfBenchmarkSize() {
                bidList.setBenchmark(longString126);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("benchmark", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListBenchmarkSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class CommentaryTests {
            @Test
            public void validationTestIfCommentarySize() {
                bidList.setCommentary(longString126);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("commentary", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListCommentarySize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class SecurityTests {
            @Test
            public void validationTestIfSecuritySize() {
                bidList.setSecurity(longString126);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("security", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListSecuritySize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class StatusTests {
            @Test
            public void validationTestIfStatusSize() {
                bidList.setStatus(longString11);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("status", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListStatusSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class TraderTests {
            @Test
            public void validationTestIfTraderSize() {
                bidList.setTrader(longString126);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("trader", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListTraderSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class BookTests {
            @Test
            public void validationTestIfBookSize() {
                bidList.setBook(longString126);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("book", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListBookSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class CreationNameTests {
            @Test
            public void validationTestIfCreationNameSize() {
                bidList.setCreationName(longString126);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("creationName", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListCreationNameSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class RevisionNameTests {
            @Test
            public void validationTestIfRevisionNameSize() {
                bidList.setRevisionName(longString126);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("revisionName", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListRevisionNameSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class DealNameTests {
            @Test
            public void validationTestIfDealNameSize() {
                bidList.setDealName(longString126);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("dealName", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListDealNameSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class DealTypeTests {
            @Test
            public void validationTestIfDealTypeSize() {
                bidList.setDealType(longString126);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("dealType", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListDealTypeSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class SourceListIdTests {
            @Test
            public void validationTestIfSourceListIdSize() {
                bidList.setSourceListId(longString126);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("sourceListId", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListSourceListIdSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class SideTests {
            @Test
            public void validationTestIfSideSize() {
                bidList.setSide(longString126);
                Set<ConstraintViolation<BidList>> result = validator.validate(bidList);
                assertEquals(1, result.size());
                ConstraintViolation<BidList> constraintViolation = (ConstraintViolation<BidList>) result.toArray()[0];
                assertEquals("side", constraintViolation.getPropertyPath().toString());
                assertEquals(bidListSideSize, constraintViolation.getMessage());
            }
        }
    }
}