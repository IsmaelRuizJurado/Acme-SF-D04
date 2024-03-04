
package acme.entities.objective;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;

public class Objective extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				instantiationMoment;

	@NotBlank
	@Length(min = 1, max = 75)
	private String				title;

	@NotBlank
	@Length(min = 1, max = 100)
	private String				description;

	@NotNull
	private prioType			priority;

	@NotNull
	private Boolean				critical;

	@Min(0)
	private Integer				duration;
	
	@URL
	private String				link;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;
}
