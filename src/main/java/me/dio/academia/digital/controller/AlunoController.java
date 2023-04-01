package me.dio.academia.digital.controller;

import me.dio.academia.digital.entity.Aluno;
import me.dio.academia.digital.entity.AvaliacaoFisica;
import me.dio.academia.digital.entity.form.AlunoForm;
import me.dio.academia.digital.entity.form.AlunoUpdateForm;
import me.dio.academia.digital.service.impl.AlunoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoServiceImpl service;

    @PostMapping
    public Aluno create(@Valid @RequestBody AlunoForm form) {
        return service.create(form);
    }


    @GetMapping("/avaliacoes/{id}")
    public List<AvaliacaoFisica> getAllAvaliacaoFisicaId(@PathVariable Long id) {
        return service.getAllAvaliacaoFisicaId(id);
    }

    @GetMapping
    public List<Aluno> getAll(@RequestParam(value = "dataDeNascimento", required = false)
                              String dataDeNacimento) {
        return service.getAll(dataDeNacimento);
    }


    //Metodos adicionado conforme pedido ds professora Camila
    @GetMapping("/{id}") //Localizar pelo id
    public ResponseEntity<Object> get(@PathVariable(value = "id") Long id) {
        Optional<Aluno> aluno = service.get(id);
        if (!aluno.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado");

        }

        return ResponseEntity.status(HttpStatus.OK).body(aluno);
    }

    @DeleteMapping("/{id}") //Excluir pelo id
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        Optional<Aluno> aluno = service.get(id);
        if (!aluno.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado");

        }
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Aluno excluido com sucesso!!!");
    }

    @PutMapping("/{id}") //Atualizar pelo id
    @Transactional
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id,
                                         @RequestBody @Valid AlunoUpdateForm alunoUpdateForm) {
        Optional<Aluno> aluno = service.get(id);
        if (!aluno.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, alunoUpdateForm));

    }


}