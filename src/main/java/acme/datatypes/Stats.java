
package acme.datatypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Stats implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	public Stats() {
	}


	// Attributes -------------------------------------------------------------
	int		count;

	double	average;

	double	deviation;

	double	minimum;

	double	maximum;

}
