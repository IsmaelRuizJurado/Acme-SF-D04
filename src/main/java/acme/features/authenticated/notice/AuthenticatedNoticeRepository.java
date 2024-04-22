
package acme.features.authenticated.notice;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.notice.Notice;

@Repository
public interface AuthenticatedNoticeRepository extends AbstractRepository {

	@Query("select o from Notice o")
	Collection<Notice> findAllNoticesNotOlderThan1Month();

	@Query("select o from Notice o where o.id = :id")
	Notice findNoticeById(int id);
}
