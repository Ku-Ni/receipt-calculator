package coenie.technical_assignment.receipt_calculator.service;

@FunctionalInterface
/**
 * Functional interface which can handled checked exceptions
 * 
 * @author Coenie
 *
 * @param <T>
 * @param <R>
 * @param <EX>
 */
public interface CheckedFunction<T, R, EX extends Exception> {
	R apply(T element) throws EX;
}
