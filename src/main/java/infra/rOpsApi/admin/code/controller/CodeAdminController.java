package infra.rOpsApi.admin.code.controller;

import infra.rOpsApi.code.model.Code;
import infra.rOpsApi.code.model.CodeType;
import infra.rOpsApi.code.model.Code_PK;
import infra.rOpsApi.code.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/codes")
public class CodeAdminController {
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

    @PostMapping("/{code_type_id}")
    public Code insertCode(@PathVariable("code_type_id") int code_type_id, @RequestBody Code param) {
        int countByTypeId = codeRepository.getCountByTypeId(code_type_id);
        int code_id = countByTypeId+1;
        codeRepository.saveCode(code_type_id, code_id, param.getCode_name());
        Code_PK code_PK = new Code_PK(code_type_id,code_id);

        return codeRepository.findById(code_PK).get();
    }

    @PatchMapping("/{code_type_id}/{code_id}")
    public Code updateCode(@PathVariable("code_type_id") int code_type_id, @PathVariable("code_id") int code_id, @RequestBody Code param) {
        Code_PK code_PK = new Code_PK(code_type_id,code_id);

        Code code = codeRepository.findById(code_PK).get();
        code.setCode_name(param.getCode_name());
        code.setUse_yn(param.getUse_yn());

        return codeRepository.save(code);
    }

    @DeleteMapping("/{code_type_id}/{code_id}")
    public void deleteCode(@PathVariable("code_type_id") int code_type_id, @PathVariable("code_id") int code_id) {
        Code_PK code_PK = new Code_PK(code_type_id,code_id);

        codeRepository.deleteById(code_PK);
    }
}