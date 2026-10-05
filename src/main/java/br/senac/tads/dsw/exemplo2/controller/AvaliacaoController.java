package br.senac.tads.dsw.exemplo2.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import br.senac.tads.dsw.exemplo2.model.Avaliacao;
import br.senac.tads.dsw.exemplo2.repository.AvaliacaoRepository;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private AvaliacaoRepository repository;

    public AvaliacaoController(AvaliacaoRepository repository) {
        this.repository = repository;
    }

    @PostMapping()
    public ResponseEntity<Avaliacao> criarAvaliacao(
            @Valid @RequestBody Avaliacao avaliacao) {

        Avaliacao avaliacaoSalva = repository.save(avaliacao);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(avaliacaoSalva.getId())
                .toUri();

        return ResponseEntity.created(location).body(avaliacaoSalva);
    }

    @GetMapping()
    public List<AvaliacaoRepository> listarTodas() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoRepository> buscarPorId(@PathVariable Long id) {

        Optional<AvaliacaoRepository> avaliacaoBuscada = repository.findById(id);

        if (avaliacaoBuscada.isPresent()) {
            return ResponseEntity.ok(avaliacaoBuscada.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoRepository> atualizarAvaliacao(
            @PathVariable Long id,
            @Valid @RequestBody Avaliacao avaliacaoAtualizada) {

        Optional<AvaliacaoRepository> avaliacaoBuscada = repository.findById(id);

        if (avaliacaoBuscada.isPresent()) {

            AvaliacaoRepository avaliacaoExistente = avaliacaoBuscada.get();

            avaliacaoExistente.setNota(avaliacaoAtualizada.getNota());
            avaliacaoExistente.setComentario(avaliacaoAtualizada.getComentario());

            repository.save(avaliacaoExistente);

            return ResponseEntity.ok(avaliacaoExistente);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarAvaliacao(@PathVariable Long id) {

        Optional<AvaliacaoRepository> avaliacaoBuscada = repository.findById(id);

        if (avaliacaoBuscada.isPresent()) {

            repository.deleteById(id);

            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}