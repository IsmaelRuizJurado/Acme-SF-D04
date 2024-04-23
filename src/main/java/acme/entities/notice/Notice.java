
package acme.entities.notice;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Notice extends AbstractEntity {

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
	@Length(min = 1, max = 75)
	private String				author;

	@NotNull
	@NotBlank
	@Length(min = 1, max = 100)
	private String				message;

	@URL
	private String				link;

	@Email
	private String				email;

	@NotNull
	@Transient
	private boolean				confirmation;

}
