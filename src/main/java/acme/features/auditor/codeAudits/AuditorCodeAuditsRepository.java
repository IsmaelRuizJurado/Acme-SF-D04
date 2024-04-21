
package acme.features.auditor.codeAudits;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit_records.AuditRecords;
import acme.entities.code_audits.CodeAudits;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditsRepository extends AbstractRepository {

	@Query("SELECT c FROM CodeAudits c WHERE c.auditor.userAccount.id = :id")
	Collection<CodeAudits> findCodeAuditsByAuditorId(int id);

	@Query("SELECT c FROM CodeAudits c WHERE c.id =:id")
	CodeAudits findCodeAuditsById(int id);

	@Query("SELECT a FROM AuditRecords a WHERE a.codeAudits = :codeAudits ")
	Collection<AuditRecords> findAuditRecordsByCodeAudits(CodeAudits codeAudits);

	@Query("SELECT a FROM Auditor a WHERE a.id = :id")
	Auditor findOneAuditorById(int id);

	@Query("SELECT c FROM CodeAudits c WHERE c.code = :code")
	CodeAudits findCodeAuditsByCode(String code);

}
