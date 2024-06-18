package basePackage.controllers;

import basePackage.entities.Pessoa;
import basePackage.models.dto.LoginDTO;
import basePackage.models.enums.VinculoEnum;
import basePackage.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @CrossOrigin
    @PostMapping("/createOrUpdate")
    public ResponseEntity<String> createUpdate(@RequestBody Pessoa pessoa){
        var returnValue = pessoaService.saveCliente(pessoa);
        if (returnValue != null) {
            return ResponseEntity.ok("Usuário criado com sucesso!");
        } else {
            return new ResponseEntity<>("Erro ao criar usuário", HttpStatus.BAD_REQUEST);
        }
    }
    @CrossOrigin
    @PostMapping("/login")
    public Pessoa getCredentials(@RequestBody LoginDTO loginDTO) {
        return pessoaService.validateLogin(loginDTO);
    }

    @CrossOrigin
    @GetMapping("/userByCPF/{cpf}")
    public Pessoa getUserByCPF(@PathVariable("cpf") String cpf) {
        return pessoaService.getPessoaByCPF(cpf);
    }

    @CrossOrigin
    @GetMapping("/userById/{id}")
    public Pessoa getUser(@PathVariable("id") String id) {
        Optional<Pessoa> pessoa = pessoaService.getPessoaById(id);
        return pessoa.orElse(null);
    }

    @CrossOrigin
    @GetMapping("/getAuthorization/{id}")
    public boolean isAuthorized(@PathVariable("id") String id){
        Optional<Pessoa> pessoa = pessoaService.getPessoaById(id);
        if(pessoa.isPresent()){
            VinculoEnum vinculo = pessoa.get().getVinculo();
            if(vinculo == VinculoEnum.MEDICO || vinculo == VinculoEnum.PROFESSOR || vinculo == VinculoEnum.SECRETARIO){
                return true;
            }
        }
        return false;
    }
}
