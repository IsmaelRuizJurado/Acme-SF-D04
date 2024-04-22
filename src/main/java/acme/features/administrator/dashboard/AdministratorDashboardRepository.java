
package acme.features.administrator.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.Administrator;
import acme.client.repositories.AbstractRepository;
import acme.entities.claim.Claim;
import acme.entities.notice.Notice;
import acme.entities.risk.Risk;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select m from Administrator m where m.userAccount.id = :id")
	Administrator findOneAdministratorByUserAccountId(int id);

	@Query("select count(a) from Administrator a")
	Integer numAdministrators();

	@Query("select count(a) from Client a")
	Integer numClients();

	@Query("select count(a) from Manager a")
	Integer numManagers();

	@Query("select count(a) from Consumer a")
	Integer numConsumer();

	@Query("select count(a) from Developer a")
	Integer numDeveloper();

	@Query("select count(a) from Provider a")
	Integer numProvider();

	@Query("select count(a) from Sponsor a")
	Integer numSponsor();

	@Query("select objective from Objective objective where objective.critical = true")
	Integer criticalObjectives();

	@Query("select count(o) from Objective o")
	Integer numObjectives();

	@Query("select n from Notice n")
	Collection<Notice> Notices();

	@Query("select c from Claim c")
	Collection<Claim> Claims();

	@Query("select r from Risk r")
	Collection<Risk> risks();

}
