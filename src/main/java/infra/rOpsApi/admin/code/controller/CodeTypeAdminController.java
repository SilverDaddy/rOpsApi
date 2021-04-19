package infra.rOpsApi.admin.code.controller;

import infra.rOpsApi.code.model.CodeType;
import infra.rOpsApi.code.repository.CodeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/codeTypes")
public class CodeTypeAdminController {
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

    @PostMapping
    public CodeType insertCodeType(@RequestBody CodeType param) {
        return codeTypeRepository.save(new CodeType(param.getCode_type_id(), param.getCode_type_name(), param.getCode_type_name_eng()));
    }

    @PatchMapping("/{code_type_id}")
    public CodeType updateCodeType(@PathVariable("code_type_id") int code_type_id, @RequestBody CodeType param) {
        CodeType codeType = codeTypeRepository.findById(code_type_id).get();
        codeType.setCode_type_name(param.getCode_type_name());
        codeType.setCode_type_name_eng(param.getCode_type_name_eng());
        return codeTypeRepository.save(codeType);
    }

    @DeleteMapping("/{code_type_id}")
    public void deleteCodeType(@PathVariable("code_type_id") int code_type_id) {
        codeTypeRepository.deleteById(code_type_id);
    }
}