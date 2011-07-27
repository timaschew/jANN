package de.unikassel.ann.io;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.exception.NullInputException;
import org.supercsv.exception.SuperCSVException;
import org.supercsv.util.CSVContext;

/**
 * Converts decimal seperator comma to point, if needed
 * @author anton
 *
 */
public class ParseDoubleUni extends CellProcessorAdaptor implements StringCellProcessor {

	  public ParseDoubleUni() {
		super();
	  }

	  public ParseDoubleUni(final CellProcessor next) {
		super(next);
	  }

	  /**
	   * @see org.supercsv.cellprocessor.CellProcessorAdaptor#execute(java.lang.Object, org.supercsv.util.CSVContext)
	   */
	  @Override
	  public Object execute(final Object value, final CSVContext context) {
		if (value == null) {
		  throw new NullInputException("Input cannot be null on line " + context.lineNumber + " at column " + context.columnNumber, context,
			  this);
		}

		final Double result;
		if (value instanceof Double) {
		  result = (Double) value;
		} else if (value instanceof String) {
		  try {
			String convertedValue = ((String) value).replace(",", ".");
			result = new Double(convertedValue);
		  } catch (final NumberFormatException e) {
			throw new SuperCSVException("Parser error", context, this, e);
		  }
		} else {
		  throw new SuperCSVException("Can't convert \"" + value + "\" to double. Input is not of type Double nor type String, but of type "
			  + value.getClass().getName(), context, this);
		}

		return next.execute(result, context);
	  }

	}
