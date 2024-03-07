
package acme.entities.risk;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Risk extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@NotBlank
	@NotNull
	@Pattern(regexp = "R-[0-9]{3}")
	private String				reference;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				identificationDate;

	@NotNull
	@Min(1)
	private Double				impact;

	@NotNull
	@Range(min = 0, max = 1)
	private Double				probability;

	@NotNull
	@NotBlank
	@Length(min = 1, max = 100)
	private String				description;

	@URL
	private String				link;

	// Derived attributes -----------------------------------------------------


	@NotNull
	@Transient
	public Double value() {
		double value;
		value = this.probability * this.impact;
		return value;
	}

	// Relationships ----------------------------------------------------------

}
