package infra.rOpsApi.code.repository;

import infra.rOpsApi.code.model.CodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeTypeRepository extends JpaRepository<CodeType, Integer> {

}