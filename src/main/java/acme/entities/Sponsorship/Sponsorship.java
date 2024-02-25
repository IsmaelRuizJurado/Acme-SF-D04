
package acme.entities.Sponsorship;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.roles.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$")
	protected String			code;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				moment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endPeriod;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				startPeriod;

	@Valid
	@NotNull
	protected Money				amount;

	@NotNull
	protected SponsorshipType	sponsorshipType;

	@Email
	protected String			email;

	@URL
	protected String			link;

	protected boolean			draftMode;

	@NotNull
	@Valid
	@ManyToOne(optional = true)
	protected Project			project;

	@NotNull
	@Valid
	@ManyToOne(optional = true)
	protected Sponsor			sponsor;

}
