package infra.rOpsApi.code.controller;

import infra.rOpsApi.code.model.CodeType;
import infra.rOpsApi.code.repository.CodeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/codeTypes")
public class CodeTypeController {
    @Autowired
    private CodeTypeRepository codeTypeRepository;

    @GetMapping
    public List<CodeType> getCodeTypes() {
        return codeTypeRepository.findAll();
    }

    @GetMapping("/{code_type_id}")
    public CodeType getCodeTypeById(@PathVariable("code_type_id") int code_type_id) {
        return codeTypeRepository.findById(code_type_id).get();
    }
}