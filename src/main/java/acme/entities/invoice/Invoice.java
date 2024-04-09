
package acme.entities.invoice;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.sponsorship.Sponsorship;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invoice extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^IN-[0-9]{4}-[0-9]{4}$")
	protected String			code;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				registrationTime;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				dueDate;

	@Valid
	@NotNull
	protected Money				quantity;

	@Valid
	@NotNull
	protected Double			tax;

	@URL
	protected String			link;

	protected boolean			draftMode;


	@Transient
	public Money totalAmount() {
		double total;
		total = this.quantity.getAmount() + this.tax / 100 * this.quantity.getAmount();
		Money totalAmount = new Money();
		totalAmount.setAmount(total);
		totalAmount.setCurrency(this.quantity.getCurrency());
		return totalAmount;
	}


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Sponsorship sponsorship;

}
