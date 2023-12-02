package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The ExpenseTrackerModel class implements the expense tracker model.
 * It applies the Observer design pattern.
 * @version 2.0
 * @author Devyani Dhruvi
 * @since 2023-12-01
 */

public class ExpenseTrackerModel {

  //encapsulation - data integrity
  private List<Transaction> transactions;
  private List<Integer> matchedFilterIndices;
  private List<ExpenseTrackerModelListener> listeners = new ArrayList<>();
  
  // This is applying the Observer design pattern.                          
  // Specifically, this is the Observable class. 
  /**
   * This method creates an ExpenseTrackerModel with new list for transactions and filter variables.
   * It applies the Observer design pattern, which is the Observable class.
   */
    
  public ExpenseTrackerModel() {
    transactions = new ArrayList<Transaction>();
    matchedFilterIndices = new ArrayList<Integer>();
  }

  /**
   * This method will add a new transaction to the model.
   *
   * @param t - The Transaction to be added. Cannot be a null value.
   * @throws IllegalArgumentException - if the new transaction added is null value.
   */
  
  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    stateChanged();
  }

  /**
   * This method will remove a transaction from the model.
   *
   * @param t The Transaction to be removed.
   */
  
  public void removeTransaction(Transaction t) {
    transactions.remove(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    stateChanged();
  }

  /**
   * This method is used to retrieve an unmodifiable list of transactions from the model.
   *
   * @return An unmodifiable list of transactions.
   */
  
  public List<Transaction> getTransactions() {
    //encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }
  
  /**
   * This method will set the matched filter indices for the model.
   *
   * @param newMatchedFilterIndices - The new list of matched filter indices. Cannot be a null value.
   * @throws IllegalArgumentException - if the new list is null or contains invalid indices.
   */

  public void setMatchedFilterIndices(List<Integer> newMatchedFilterIndices) {
      // Perform input validation
      if (newMatchedFilterIndices == null) {
	  throw new IllegalArgumentException("The matched filter indices list must be non-null.");
      }
      for (Integer matchedFilterIndex : newMatchedFilterIndices) {
	  if ((matchedFilterIndex < 0) || (matchedFilterIndex > this.transactions.size() - 1)) {
	      throw new IllegalArgumentException("Each matched filter index must be between 0 (inclusive) and the number of transactions (exclusive).");
	  }
      }
      // For encapsulation, copy in the input list 
      this.matchedFilterIndices.clear();
      this.matchedFilterIndices.addAll(newMatchedFilterIndices);
  }

  /**
   * This method will retrieve a copy of the current matched filter indices.
   *
   * @return A copy of the matched filter indices.
   */
  
  public List<Integer> getMatchedFilterIndices() {
      // For encapsulation, copy out the output list
      List<Integer> copyOfMatchedFilterIndices = new ArrayList<Integer>();
      copyOfMatchedFilterIndices.addAll(this.matchedFilterIndices);
      return copyOfMatchedFilterIndices;
  }

  /**
   * This method will register the ExpenseTrackerModelListener for state change events.
   *
   * @param listener - The ExpenseTrackerModelListener to be registered
   * @return a boolean value - if the listener is not null and not registered previously, it will return true. If not, it will return false.
   */   
  public boolean register(ExpenseTrackerModelListener listener) {
      // For the Observable class, this is one of the methods.
      //
      // TODO
	  if (listener != null && !listeners.contains(listener)) {
	      listeners.add(listener);
	      return true;
	  }
	  return false;
  }

  /**
   * This method will retrieve the number of registered listeners.
   *
   * @return The number of registered listeners.
   */
  
  public int numberOfListeners() {
      // For testing, this is one of the methods.
      //
      //TODO
	  if(listeners.size()>0)
		  return listeners.size();
	  else
		  return 0;
  }

  /**
   * This method will check if the given listener is registered previously.
   *
   * @param listener - The ExpenseTrackerModelListener to be checked.
   * @return a boolean value - True if the listener is registered or else false is returned.
   */
  
  public boolean containsListener(ExpenseTrackerModelListener listener) {
      // For testing, this is one of the methods.
      //
      //TODO
	  if(listeners.contains(listener))
		  return true;
	  else
		  return false;
  }

  protected void stateChanged() {
      // For the Observable class, this is one of the methods.
      //
      //TODO
	  for (ExpenseTrackerModelListener listener : listeners) {
		  listener.update(this);
	  }
  }
}
