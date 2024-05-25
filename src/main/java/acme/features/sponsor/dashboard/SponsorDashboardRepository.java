
package acme.features.sponsor.dashboard;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.roles.Sponsor;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("select count(us) from Invoice us where us.sponsorship.sponsor = :sponsor and us.draftMode=false and us.tax <= 21 ")
	Optional<Integer> findNumOfInvoicesWithTax21less(Sponsor sponsor);

	@Query("select count(us) from Sponsorship us where us.sponsor = :sponsor and us.draftMode=false and us.link is not null  ")
	Optional<Integer> findNumOfSponsorshipsWithLink(Sponsor sponsor);

	@Query("select p.amount.currency, avg(p.amount.amount) from Sponsorship p where p.sponsor = :sponsor and p.draftMode=false group by p.amount.currency")
	List<Object[]> findAverageSponsorshipCost(Sponsor sponsor);

	@Query("select p.amount.currency, max(p.amount.amount) from Sponsorship p where p.sponsor = :sponsor and p.draftMode=false group by p.amount.currency")
	List<Object[]> findMaxSponsorshipCost(Sponsor sponsor);

	@Query("select p.amount.currency, min(p.amount.amount) from Sponsorship p where p.sponsor = :sponsor and p.draftMode=false group by p.amount.currency")
	List<Object[]> findMinSponsorshipCost(Sponsor sponsor);

	@Query("select p.amount.currency, stddev(p.amount.amount) from Sponsorship p where p.sponsor = :sponsor and p.draftMode=false group by p.amount.currency")
	List<Object[]> findLinearDevSponsorshipCost(Sponsor sponsor);

	@Query("select m from Sponsor m where m.userAccount.id = :id")
	Sponsor findOneSponsorByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select us.quantity.currency, avg(us.quantity.amount) from Invoice us where us.sponsorship.sponsor = :sponsor and us.draftMode=false group by us.quantity.currency")
	List<Object[]> findAverageInvoiceQuantity(Sponsor sponsor);

	@Query("select us.quantity.currency, max(us.quantity.amount) from Invoice us where us.sponsorship.sponsor = :sponsor and us.draftMode=false group by us.quantity.currency")
	List<Object[]> findMaxInvoiceQuantity(Sponsor sponsor);

	@Query("select us.quantity.currency, min(us.quantity.amount) from Invoice us where us.sponsorship.sponsor = :sponsor and us.draftMode=false group by us.quantity.currency")
	List<Object[]> findMinInvoiceQuantity(Sponsor sponsor);

	@Query("select us.quantity.currency, stddev(us.quantity.amount) from Invoice us where us.sponsorship.sponsor = :sponsor and us.draftMode=false group by us.quantity.currency")
	List<Object[]> findLinearDevInvoiceQuantity(Sponsor sponsor);

}
