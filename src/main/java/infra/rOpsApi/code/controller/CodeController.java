package infra.rOpsApi.code.controller;

import infra.rOpsApi.code.model.Code;
import infra.rOpsApi.code.model.Code_PK;
import infra.rOpsApi.code.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/codes")
public class CodeController {
    @Autowired
    private CodeRepository codeRepository;

    @GetMapping
    public List<Code> getCodes() {
        return codeRepository.findAll();
    }

    @GetMapping("/{code_type_id}")
    public List<Code> getCodesByTypeId(@PathVariable("code_type_id") int code_type_id) {
        return codeRepository.findCodesByTypeId(code_type_id);
    }

    @GetMapping("/{code_type_id}/{code_id}")
    public Code getCodeByTypeId_CodeId(@PathVariable("code_type_id") int code_type_id, @PathVariable("code_id") int code_id) {
        Code_PK code_PK = new Code_PK(code_type_id,code_id);
        return codeRepository.findById(code_PK).get();
    }
}