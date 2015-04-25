package web.components.table.generated;

import com.vaadin.ui.TextField;

/**
 * Description: The Search Text component which extends {@link TextField} with some <br>
 * extra information such as the propertyId of the web.components.table.generated containerDataSource and search properties. <br>
 * <br>
 * Filename: SearchText.java <br>
 */
public class SearchText extends TextField {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5748490642586998793L;

	/** The property id. */
	private String propertyId;
	
	/** The ignore case. */
	private boolean ignoreCase = true;
	
	/** The is exact match. */
	private boolean isExactMatch = false;
	
	/** The only match prefix. */
	private boolean onlyMatchPrefix = false;
	
	/**
	 * Gets the property id.
	 * 
	 * @return The property id
	 */
	public final String getPropertyId() {
		return propertyId;
	}
	
	/**
	 * Sets the property id.
	 * 
	 * @param pPropertyId
	 *            The new property id
	 */
	public final void setPropertyId(final String pPropertyId) {
		this.propertyId = pPropertyId;
	}
	
	/**
	 * Checks if is ignore case.
	 * 
	 * @return true, if is ignore case
	 */
	public final boolean isIgnoreCase() {
		return ignoreCase;
	}
	
	/**
	 * Sets the ignore case.
	 * 
	 * @param pIgnoreCase
	 *            The new ignore case
	 */
	public final void setIgnoreCase(final boolean pIgnoreCase) {
		this.ignoreCase = pIgnoreCase;
	}
	
	/**
	 * Checks if is only match prefix.
	 * 
	 * @return true, if is only match prefix
	 */
	public final boolean isOnlyMatchPrefix() {
		return onlyMatchPrefix;
	}
	
	/**
	 * Sets the only match prefix.
	 * 
	 * @param pOnlyMatchPrefix
	 *            The new only match prefix
	 */
	public final void setOnlyMatchPrefix(final boolean pOnlyMatchPrefix) {
		this.onlyMatchPrefix = pOnlyMatchPrefix;
	}
	
	/**
	 * Checks if is exact match.
	 * 
	 * @return true, if is exact match
	 */
	public final boolean isExactMatch() {
		return isExactMatch;
	}
	
	/**
	 * Sets the exact match.
	 * 
	 * @param pIsExactMatch
	 *            The new exact match
	 */
	public final void setExactMatch(final boolean pIsExactMatch) {
		this.isExactMatch = pIsExactMatch;
	}
	
}
