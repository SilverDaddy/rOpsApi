package infra.rOpsApi.code.repository;

import infra.rOpsApi.code.model.Code;
import infra.rOpsApi.code.model.Code_PK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<Code, Code_PK> {
    @Query("SELECT cd FROM Code cd JOIN FETCH cd.codeType WHERE cd.code_type_id = ?1")
    List<Code> findCodesByTypeId(int code_type_id);

    @Query(
            value ="SELECT count(*) FROM code_master WHERE code_type_id = ?1",
            nativeQuery = true)
    Integer getCountByTypeId(int code_type_id);

    @Query(
            value ="INSERT INTO rops.code_master " +
                    "(code_type_id, code_id, code_name, use_yn) " +
                    "VALUES(?1, ?2, ?3, 'y');",
            nativeQuery = true)
    Code saveCode(int code_type_id, int code_id, String code_name);
}

