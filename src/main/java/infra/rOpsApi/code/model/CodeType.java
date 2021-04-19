package infra.rOpsApi.code.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "code_type_master")
public class CodeType {
    @Id
    int code_type_id;
    String code_type_name;
    String code_type_name_eng;
}