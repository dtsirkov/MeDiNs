package table;

import java.io.Serializable;

/**
 * Description: Generates a column for the {@link MyTable}.<br>
 * <br>
 * Filename: MyColumn.java <br>
 */
public class MyColumn implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7577219594806791185L;

	/** The id. */
	private String id;

	/** The name. */
	private String name;

	/** The is searchable. */
	private boolean isSearchable;

	/** The is exact match. */
	private boolean isExactMatch;

	// Not yet implemented completely
	/** The is ignore case. */
	private boolean isIgnoreCase = true;

	/** The is ignore case. */
	private boolean isCollapsed;

	/** The width. */
	private int width;

	private String format;

	public  MyColumn(final String pId) {
		this(pId, true, false, "");
	}

	/**
	 * Instantiates a new MyColumn object.
	 * 
	 * @param pId The id.
	 */
	public  MyColumn(final String pId, final String format) {
		this(pId, true, false, format);
	}

	/**
	 * Instantiates a new MyColumn object.
	 * 
	 * @param pId The id.
	 */
	public  MyColumn(final String pId, boolean pIsSearchable, final String format) {
		this(pId, pIsSearchable, false, format);
	}

	/**
	 * Instantiates a new MyColumn object.
	 * 
	 * @param pId The id.
	 * @param pIsSearchable The is searchable.
	 * @param pIsExactMatch The is exact match.
	 */
	public  MyColumn(final String pId, final boolean pIsSearchable, final boolean pIsExactMatch, final String format) {
		this(pId, "", pIsSearchable, pIsExactMatch, -1, format);
		setCorrectName(pId);
	}

	private void setCorrectName(final String pId) {
		String[] strCollection = pId.split("\\.");
		String correctName = "";
		for (int i = 0; i < strCollection.length; i++) {
			correctName += strCollection[i].substring(0, 1).toUpperCase() + strCollection[i].substring(1);
			if (i != strCollection.length - 1) {
				correctName += " ";
			}
		}
		this.name = correctName;
	}

	/**
	 * Instantiates a new MyColumn object.
	 * 
	 * @param pId The id.
	 * @param pName The name.
	 * @param pIsSearchable The is searchable.
	 * @param pIsExactMatch The is exact match.
	 * @param pIsCollapsed The collapsed.
	 * @param pWidth The width.
	 */
	public  MyColumn(
			final String pId, 
			final boolean pIsSearchable, 
			final boolean pIsExactMatch, 
			final boolean pIsIgnoreCase, 
			final boolean pIsCollapsed, 
			final int pWidth, 
			final String format
			) {
		this(pId, "", pIsSearchable, pIsExactMatch, true, pWidth, format);
		setCorrectName(pId);
	}

	/**
	 * Instantiates a new MyColumn object.
	 * 
	 * @param pId The id.
	 * @param pName The name.
	 */
	public  MyColumn(final String pId, final String pName, final String format) {
		this(pId, pName, true, false, -1, format);
	}

	/**
	 * Instantiates a new MyColumn object.
	 * 
	 * @param pId The id.
	 * @param pName The name.
	 * @param pIsSearchable The is searchable.
	 */
	public  MyColumn(final String pId, final String pName, final boolean pIsSearchable, final String format) {
		this(pId, pName, pIsSearchable, false, -1, format);
	}

	/**
	 * Instantiates a new MyColumn object.
	 * 
	 * @param pId The id.
	 * @param pName The name.
	 * @param pIsSearchable The is searchable.
	 * @param pIsExactMatch The is exact match.
	 */
	public  MyColumn(final String pId, final String pName, final boolean pIsSearchable, final boolean pIsExactMatch, final String format) {
		this(pId, pName, pIsSearchable, pIsExactMatch, -1, format);
	}

	/**
	 * Instantiates a new MyColumn object.
	 * 
	 * @param pId The id.
	 * @param pName The name.
	 * @param pIsSearchable The is searchable.
	 * @param pWidth The width.
	 * @param pIsCollapsed The collapsed.
	 */
	public  MyColumn(final String pId, final String pName, final boolean pIsSearchable, final boolean pIsCollapsed, final int pWidth, final String format) {
		this(pId, pName, pIsSearchable, false, pIsCollapsed, pWidth, format);
	}

	/**
	 * Instantiates a new MyColumn object.
	 * 
	 * @param pId The id.
	 * @param pName The name.
	 * @param pIsSearchable The is searchable.
	 * @param pIsExactMatch The is exact match.
	 * @param pWidth The width.
	 * @param pIsCollapsed The collapsed.
	 */
	public  MyColumn(
			final String pId, 
			final String pName, 
			final boolean pIsSearchable, 
			final boolean pIsExactMatch, 
			final boolean pIsCollapsed, 
			final int pWidth, 
			final String format
			) {
		this(pId, pName, pIsSearchable, pIsExactMatch, true, pIsCollapsed, pWidth, format);
	}

	/**
	 * Instantiates a new MyColumn object.
	 * 
	 * @param pId The id.
	 * @param pName The name.
	 * @param pIsSearchable The is searchable.
	 * @param pIsExactMatch The is exact match.
	 * @param pWidth The width.
	 * @param pIsCollapsed The collapsed.
	 */
	public  MyColumn(   
			final String pId, 
			final String pName, 
			final boolean pIsSearchable, 
			final boolean pIsExactMatch, 
			final boolean pIsIgnoreCase, 
			final boolean pIsCollapsed,
			final int pWidth, 
			final String format
			) {
		this.id = pId;
		this.name = pName;
		this.isSearchable = pIsSearchable;
		this.width = pWidth;
		this.isExactMatch = pIsExactMatch;
		this.format = format;
		this.isCollapsed = pIsCollapsed;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id.
	 */
	public final String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param pId The new id
	 */
	public final void setId(final String pId) {
		this.id = pId;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name.
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param pName The new name.
	 */
	public final void setName(final String pName) {
		this.name = pName;
	}

	/**
	 * Checks if the generated column is searchable.
	 * 
	 * @return true, if is searchable.
	 */
	public final boolean isSearchable() {
		return isSearchable;
	}

	/**
	 * Sets the searchable value for the generating column.
	 * 
	 * @param pIsSearchable The new searchable value for a column.
	 */
	public final void setSearchable(final boolean pIsSearchable) {
		this.isSearchable = pIsSearchable;
	}

	/**
	 * Gets the width.
	 * 
	 * @return the width.
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 * 
	 * @param pWidth The new width.
	 */
	public final void setWidth(final int pWidth) {
		this.width = pWidth;
	}

	/**
	 * Checks if the search for a column is exact match.
	 * 
	 * @return true, if is exact match.
	 */
	public final boolean isExactMatch() {
		return isExactMatch;
	}

	/**
	 * Sets the exact match.
	 * 
	 * @param pIsExactMatch The new exact match
	 */
	public final void setExactMatch(final boolean pIsExactMatch) {
		this.isExactMatch = pIsExactMatch;
	}


	/**
	 * Checks if the search for a column is ignore case.
	 * 
	 * @return true, if is ignore case.
	 */
	public final boolean isIgnoreCase() {
		return isIgnoreCase;
	}


	/**
	 * Sets the ignore case value.
	 * 
	 * @param pIsIgnoreCase The new ignore case value.
	 */
	public final void setIgnoreCase(final boolean pIsIgnoreCase) {
		this.isIgnoreCase = pIsIgnoreCase;
	}

	/**
	 * Checks if the column is collapsed.
	 * 
	 * @return true, if is collapsed.
	 */
	public final boolean isCollapsed() {
		return isCollapsed;
	}

	/**
	 * Sets the collapsed.
	 * 
	 * @param pIsCollapsed The new collapsed
	 */
	public final void setCollapsed(final boolean pIsCollapsed) {
		this.isCollapsed = pIsCollapsed;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}