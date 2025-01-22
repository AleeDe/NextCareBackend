package com.UBITDev.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.UBITDev.model.NGO;
import com.UBITDev.repository.NGORepository;

@Service
public class NGOService {
    

    @Autowired
    private NGORepository ngoRepository;

    public NGO createNGO(NGO ngo) {
        return ngoRepository.save(ngo);
    }

    public NGO getNGOById(ObjectId id) {
        return ngoRepository.findById(id).orElse(null);
    }

    public String deleteNGO(ObjectId id) {
        ngoRepository.deleteById(id);
        return "NGO deleted";
    }

    public NGO updateNGO(ObjectId id, NGO ngo) {
        NGO existingNGO = ngoRepository.findById(id).orElse(null);
        existingNGO.setName(ngo.getName());
        existingNGO.setEmail(ngo.getEmail());
        existingNGO.setLogoUrl(ngo.getLogoUrl());
        existingNGO.setDescription(ngo.getDescription());
        return ngoRepository.save(existingNGO);
    }

    public List<NGO> getNGOs() {
        return ngoRepository.findAll();
    }
}
