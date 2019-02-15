package com.gus.translations.service;

import com.gus.translations.model.Dictionary;
import com.gus.translations.repository.DictionaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    public DictionaryService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    public Iterable<Dictionary> list() {
        return dictionaryRepository.findAll();
    }

    public Dictionary save(Dictionary dictionary) {
        return dictionaryRepository.save(dictionary);
    }

    public void save(List<Dictionary> dictionaries) {
        dictionaryRepository.save(dictionaries);
    }

}
