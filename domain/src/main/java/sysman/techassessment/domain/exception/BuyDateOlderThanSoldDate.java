package sysman.techassessment.domain.exception;

public class BuyDateOlderThanSoldDate extends BusinessDomainException {
    public BuyDateOlderThanSoldDate(String message) {
        super(message);
    }
}
