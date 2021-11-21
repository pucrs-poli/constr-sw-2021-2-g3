package com.construcao.software.users.service;

import com.construcao.software.users.dto.PapelDTO;
import com.construcao.software.users.model.Papel;
import com.construcao.software.users.repository.PapelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PapelService {

    private PapelRepository papelRepository;

    public PapelService(PapelRepository papelRepository) {
        this.papelRepository = papelRepository;
    }

    public List<Papel> recuperarPapeis() {
        return papelRepository.findAll();
    }

    public Optional<Papel> recuperarPapelPorId(String id) {
        return papelRepository.findById(id);
    }

    public Papel salvarPapel(PapelDTO papelDTO) {
        return papelRepository.save(new Papel(papelDTO.getNome()));
    }

    public Papel alterarPapel(String id, PapelDTO papelDTO) {
        var papel = papelRepository.findById(id);

        if (papel.isEmpty()) {
            throw new IllegalArgumentException("Papel não encontrado.");
        }

        var entidade = new Papel(id, papelDTO.getNome());
        return papelRepository.save(entidade);
    }

    public void deletarPapel(String id) {
        var papel = papelRepository.findById(id);

        if (papel.isEmpty()) {
            throw new IllegalArgumentException();
        }

        papelRepository.deleteById(id);
    }

}
