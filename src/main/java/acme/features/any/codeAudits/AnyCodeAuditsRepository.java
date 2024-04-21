
package acme.features.any.codeAudits;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit_records.AuditRecords;
import acme.entities.code_audits.CodeAudits;

@Repository
public interface AnyCodeAuditsRepository extends AbstractRepository {

	@Query("SELECT c FROM CodeAudits c WHERE c.draftMode = false")
	Collection<CodeAudits> findPublishedCodeAudits();

	@Query("SELECT c FROM CodeAudits c WHERE c.id =:id")
	CodeAudits findCodeAuditById(int id);

	@Query("SELECT a FROM AuditRecords a WHERE a.codeAudits.id =:id")
	List<AuditRecords> findAudiRecordsByCodeAudits(int id);

}
