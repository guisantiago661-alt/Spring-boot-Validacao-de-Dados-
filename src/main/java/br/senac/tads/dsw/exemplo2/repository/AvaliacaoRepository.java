package br.senac.tads.dsw.exemplo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.tads.dsw.exemplo2.model.Avaliacao;
import br.senac.tads.dsw.exemplo2.repository.AvaliacaoRepository;
import jakarta.validation.Valid;

public interface AvaliacaoRepository extends JpaRepository<AvaliacaoRepository, Long> {

    void setNota(Integer nota);

    void setComentario(String comentario);

    Avaliacao save(Avaliacao avaliacao);
}