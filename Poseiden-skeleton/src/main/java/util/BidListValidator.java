//package util;
//
//import java.util.Set;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validator;
//import javax.validation.executable.ExecutableValidator;
//import javax.validation.metadata.BeanDescriptor;
//
//import org.apache.commons.validator.routines.DoubleValidator;
//import org.springframework.validation.Errors;
//
//import com.nnk.springboot.domain.BidList;
//
//public class BidListValidator implements Validator {
//
//    public boolean supports(Class<?> aClass) {
//        return BidList.class.equals(aClass);
//    }
//
//    public void validate(Object o, Errors errors) {
//        BidList bidList = (BidList) o;
//        DoubleValidator doubleValidator = new DoubleValidator();
//        if (bidList.getAccount() == null || bidList.getAccount().isEmpty()) {
//            errors.rejectValue("account", "account.empty",
//                    "Account is mandatory");
//        }
//
//        if (bidList.getType() == null || bidList.getType().isEmpty()) {
//            errors.rejectValue("type", "type.empty", "Type is mandatory");
//        }
//
//        if (bidList.getBidQuantity() == null
//                || !doubleValidator.minValue(bidList.getBidQuantity(), 0)) {
//            errors.rejectValue("bidQuantity", "wrongValue",
//                    "BidQuantity must be positive");
//        }
//    }
//
//    @Override
//    public <T> Set<ConstraintViolation<T>> validate(T object,
//            Class<?>... groups) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public <T> Set<ConstraintViolation<T>> validateProperty(T object,
//            String propertyName, Class<?>... groups) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,
//            String propertyName, Object value, Class<?>... groups) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public <T> T unwrap(Class<T> type) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public ExecutableValidator forExecutables() {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//}
