package infra.rOpsApi.code.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "code_master")
@IdClass(Code_PK.class)
public class Code {
    @Id
    int code_type_id;
    @Id
    int code_id;
    String code_name;
    String use_yn;

    @ManyToOne(targetEntity = CodeType.class)
    @JoinColumn(name = "code_type_id", insertable = false, updatable = false)
    private CodeType codeType;
}