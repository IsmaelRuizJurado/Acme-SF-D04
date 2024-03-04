
package acme.entities.objective;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.helpers.MomentHelper;

public class Objective extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				instantiationMoment;

	@NotNull
	@NotBlank
	@Length(min = 1, max = 75)
	private String				title;

	@NotNull
	@NotBlank
	@Length(min = 1, max = 100)
	private String				description;

	@NotNull
	private PrioType			priority;

	@NotNull
	private boolean				critical;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				startPeriod;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				endPeriod;

	@URL
	private String				link;


	@NotNull
	public int duration() {
		int duration;
		duration = (int) MomentHelper.computeDuration(this.startPeriod, this.endPeriod).getSeconds() / 3600;
		return duration;
	}
}
