
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoice.Invoice;
import acme.entities.project.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select p from Sponsorship p where p.sponsor.userAccount.id = :id")
	Collection<Sponsorship> findSponsorshipsBySponsorId(int id);

	@Query("select p from Sponsorship p where p.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("select pus from Invoice pus where pus.sponsorship = :sponsorship")
	Collection<Invoice> findInvoicesBySponsorship(Sponsorship sponsorship);

	@Query("select m from Sponsor m where m.id = :id")
	Sponsor findOneSponsorById(int id);

	@Query("select p from Sponsorship p where p.code = :code")
	Sponsorship findSponsorshipByCode(String code);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllPublishedProjects();

	@Query("select p from Project p where p.draftMode = false and p.id = :id")
	Project findPublishedProjectById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findProjectbyId(int id);
}
